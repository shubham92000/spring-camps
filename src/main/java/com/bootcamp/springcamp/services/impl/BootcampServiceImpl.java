package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.controllers.BootcampController;
import com.bootcamp.springcamp.dtos.bootcamp.*;
import com.bootcamp.springcamp.dtos.geocode.GeocodeResDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Address;
import com.bootcamp.springcamp.models.Bootcamp;
import com.bootcamp.springcamp.models.Photo;
import com.bootcamp.springcamp.models.User;
import com.bootcamp.springcamp.repos.BootcampRepo;
import com.bootcamp.springcamp.repos.UserRepo;
import com.bootcamp.springcamp.services.BootcampService;
import com.bootcamp.springcamp.services.CareerService;
import com.bootcamp.springcamp.services.GeocodeService;
import com.bootcamp.springcamp.utils.Mode;
import com.bootcamp.springcamp.utils.ModeValue;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.compress.utils.FileNameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BootcampServiceImpl implements BootcampService {

    private final Logger log = LoggerFactory.getLogger(BootcampController.class);
    private ModelMapper mapper;
    private BootcampRepo bootcampRepo;
    private CareerService careerService;
    private UserRepo userRepo;
    private MinioClient minioClient;
    private GeocodeService geocodeService;

    private MongoTemplate mongoTemplate;

    private final String geocode_service = "geocode_service_mock";

    @Value("${minio.bootcamp.imageUploadBucket}")
    private String imageUploadBucket;

    public BootcampServiceImpl(ModelMapper mapper, BootcampRepo bootcampRepo, CareerService careerService, UserRepo userRepo, MinioClient minioClient, @Qualifier(geocode_service)  GeocodeService geocodeService, MongoTemplate mongoTemplate) {
        this.mapper = mapper;
        this.bootcampRepo = bootcampRepo;
        this.careerService = careerService;
        this.userRepo = userRepo;
        this.minioClient = minioClient;
        this.geocodeService = geocodeService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<BootcampResDto> getAllBootcamps() {
        var bootcamps = bootcampRepo.findAll()
                .stream().map(bootcamp -> mapper.map(bootcamp, BootcampResDto.class))
                .collect(Collectors.toList());
        return bootcamps;
    }

    @Override
    public BootcampResDto getBootcamp(String id) {
        var bootcamp = bootcampRepo.findById(id)
                .orElseThrow(() -> new CampApiException(HttpStatus.OK, String.format("bootcamp with id %s not found", id)));

        var bootcampRes = mapper.map(bootcamp, BootcampResDto.class);
        return bootcampRes;
    }

    @Override
    public BootcampResDto createBootcamp(CreateBootcampReqDto createBootcampReqDto, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new CampApiException(HttpStatus.UNAUTHORIZED, "user not found"));

        var bootcamp = mapper.map(createBootcampReqDto, Bootcamp.class);
        bootcamp.setUser(user);
        bootcamp.getEmails().add(createBootcampReqDto.getPrimaryEmail());
        bootcamp.getPhones().add(createBootcampReqDto.getPrimaryPhone());

        bootcamp = bootcampRepo.save(bootcamp);
        log.info("saved bootcamp -> " + bootcamp);

        var bootcampRes = mapper.map(bootcamp, BootcampResDto.class);
        return bootcampRes;
    }

    @Override
    public BootcampResDto updateBootcamp(String id, UpdateBootcampReqDto updateBootcampReqDto, Authentication authentication) {
        var bootcamp = bootcampRepo.findById(id)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("bootcamp with id %s not found", id)));

        var bootcampCreationUser = bootcamp.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!bootcampCreationUser.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update bootcamp with %s", id));
        }


        if(updateBootcampReqDto.getMode() != null){
            Mode mode = ModeValue.getMode(updateBootcampReqDto.getMode());
            if(mode == null){
                throw new CampApiException(HttpStatus.BAD_REQUEST, String.format("%s mode is now allowed", updateBootcampReqDto.getMode()));
            }
        }

        if(updateBootcampReqDto.getName() != null){
            if(bootcampRepo.findByName(updateBootcampReqDto.getName()).isPresent()){
                throw new CampApiException(HttpStatus.NOT_ACCEPTABLE, "bootcamp with the name already exists");
            }
            bootcamp.setName(updateBootcampReqDto.getName());
        }

        if(updateBootcampReqDto.getDescription() != null){
            bootcamp.setDescription(updateBootcampReqDto.getDescription());
        }

        if(updateBootcampReqDto.getWebsite() != null){
            bootcamp.setWebsite(updateBootcampReqDto.getWebsite());
        }

        if(updateBootcampReqDto.getAddress() != null){
            Address address = mapper.map(updateBootcampReqDto.getAddress(), Address.class);
            bootcamp.setAddress(address);
        }

        if(updateBootcampReqDto.getCareers() != null && updateBootcampReqDto.getCareers().size() > 0){
            var acceptableCareer = validateCareer(updateBootcampReqDto.getCareers());
            bootcamp.setCareers(acceptableCareer);
        }

        if(updateBootcampReqDto.getHousing() != null){
            bootcamp.setHousing(updateBootcampReqDto.getHousing());
        }

        if(updateBootcampReqDto.getJobAssistance() != null){
            bootcamp.setJobAssistance(updateBootcampReqDto.getJobAssistance());
        }

        if(updateBootcampReqDto.getJobGuarantee() != null){
            bootcamp.setJobGuarantee(updateBootcampReqDto.getJobGuarantee());
        }

        if(updateBootcampReqDto.getAcceptGI() != null){
            bootcamp.setAcceptGI(updateBootcampReqDto.getAcceptGI());
        }

        bootcamp = bootcampRepo.save(bootcamp);
        var response = mapper.map(bootcamp, BootcampResDto.class);
        if(bootcamp.getLocation() != null){
            response.setFormattedAddress(bootcamp.getLocation().getFormattedAddress());
        }
        return response;
    }

    List<String> validateCareer(List<String> requestCareers){
        List<String> availableCareer = careerService.getAllCareers()
                .stream().map(career -> career.name).toList();

        return requestCareers.stream().filter(availableCareer::contains)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteBootcamp(String id) {
        bootcampRepo.deleteById(id);
        return id;
    }

    @Override
    public GetBootcampInRadiusResDto getBootcampInRadius(GetBootcampInRadiusReqDto getBootcampInRadiusReqDto) {
        GeocodeResDto geocodeResDto = geocodeService.getCoordinates(getBootcampInRadiusReqDto.getZipcode());
        double latitude = Double.parseDouble(geocodeResDto.loc.lat);
        double longitude = Double.parseDouble(geocodeResDto.loc.lon);

        double radius = Double.parseDouble(getBootcampInRadiusReqDto.getDistance());

        var bootcamps = mongoTemplate.find(Query.query(Criteria.where("location").withinSphere(new Circle(
            new Point(longitude, latitude), new Distance(radius, Metrics.KILOMETERS)
        ))), Bootcamp.class, "bootcamps");

        return new GetBootcampInRadiusResDto(
                (long) bootcamps.size(),
                bootcamps
        );
    }

    @Override
    public BootcampResDto uploadPhotoForBootcamp(String bootcampId, MultipartFile file, Authentication authentication) {
        var bootcamp = bootcampRepo.findById(bootcampId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not fonud", bootcampId)));

        var bootcampCreationUser = bootcamp.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!bootcampCreationUser.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update bootcamp with %s", bootcampId));
        }

        validateContentType(file);

        // filename = bootcamp_id/uuid
        String uuid = UUID.randomUUID().toString();
        String extension = FileNameUtils.getExtension(file.getOriginalFilename());
        String filename = bootcampId+"/"+uuid+"."+extension;

        try{
            InputStream in = file.getResource().getInputStream();
            var writeResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(imageUploadBucket)
                            .object(filename)
                            .stream(in, in.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            bootcamp.getPhotos().add(
                    new Photo(uuid, writeResponse.etag(),imageUploadBucket+"/"+filename)
            );
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            throw new CampApiException(HttpStatus.INTERNAL_SERVER_ERROR,"image could not be upload");
        }

        bootcamp = bootcampRepo.save(bootcamp);

        return mapper.map(bootcamp, BootcampResDto.class);
    }

    private void validateContentType(MultipartFile file) {
        List<String> validContentType = List.of("image/jpeg", "image/png");
        if(!validContentType.contains(file.getContentType())){
            throw new CampApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "please upload jpeg or png image file");
        }
    }


    @Override
    public BootcampResDto deletePhotoForBootcamp(String bootcampId, String photoId, Authentication authentication) {
        var bootcamp = bootcampRepo.findById(bootcampId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not fonud", bootcampId)));

        var bootcampCreationUser = bootcamp.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!bootcampCreationUser.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update bootcamp with %s", bootcampId));
        }

        bootcamp.getPhotos().removeIf((photo) -> photo.getId().equals(photoId));
        bootcampRepo.save(bootcamp);
        return mapper.map(bootcamp, BootcampResDto.class);
    }

    @Override
    public BootcampResDto addEmail(String bootcampId, String email, Authentication authentication) {
        var bootcamp = bootcampRepo.findById(bootcampId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not fonud", bootcampId)));

        var bootcampCreationUser = bootcamp.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!bootcampCreationUser.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update bootcamp with %s", bootcampId));
        }

        bootcamp.getEmails().add(email);
        bootcamp = bootcampRepo.save(bootcamp);

        return mapper.map(bootcamp, BootcampResDto.class);
    }

    @Override
    public BootcampResDto deleteEmail(String bootcampId, String email, Authentication authentication) {
        var bootcamp = bootcampRepo.findById(bootcampId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not fonud", bootcampId)));

        var bootcampCreationUser = bootcamp.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!bootcampCreationUser.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update bootcamp with %s", bootcampId));
        }

        bootcamp.getEmails().removeIf(e -> e.equals(email));
        bootcamp = bootcampRepo.save(bootcamp);

        return mapper.map(bootcamp, BootcampResDto.class);
    }

    @Override
    public BootcampResDto addPhone(String bootcampId, String phone, Authentication authentication) {
        var bootcamp = bootcampRepo.findById(bootcampId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not fonud", bootcampId)));

        var bootcampCreationUser = bootcamp.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!bootcampCreationUser.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update bootcamp with %s", bootcampId));
        }

        bootcamp.getPhones().add(phone);
        bootcamp = bootcampRepo.save(bootcamp);

        return mapper.map(bootcamp, BootcampResDto.class);
    }

    @Override
    public BootcampResDto deletePhone(String bootcampId, String phone, Authentication authentication) {
        var bootcamp = bootcampRepo.findById(bootcampId)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("bootcamp with id %s not fonud", bootcampId)));

        var bootcampCreationUser = bootcamp.getUser();
        var userInRequest = userRepo.findByEmail(authentication.getName())
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND ,String.format("user with email %s not found", authentication.getName())));

        if(!bootcampCreationUser.getEmail().equals(userInRequest.getEmail())){
            throw new CampApiException(HttpStatus.UNAUTHORIZED, String.format("user is not allowed to update bootcamp with %s", bootcampId));
        }

        bootcamp.getPhones().removeIf(p -> p.equals(phone));
        bootcamp = bootcampRepo.save(bootcamp);

        return mapper.map(bootcamp, BootcampResDto.class);
    }
}
