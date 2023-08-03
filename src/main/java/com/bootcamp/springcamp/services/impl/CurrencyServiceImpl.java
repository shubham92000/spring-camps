package com.bootcamp.springcamp.services.impl;

import com.bootcamp.springcamp.dtos.currency.CreateCurrencyReqDto;
import com.bootcamp.springcamp.dtos.currency.CurrencyResDto;
import com.bootcamp.springcamp.exception.CampApiException;
import com.bootcamp.springcamp.models.Currency;
import com.bootcamp.springcamp.repos.CurrencyRepo;
import com.bootcamp.springcamp.services.CurrencyService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private CurrencyRepo currencyRepo;
    private ModelMapper mapper;

    public CurrencyServiceImpl(CurrencyRepo currencyRepo, ModelMapper modelMapper) {
        this.currencyRepo = currencyRepo;
        this.mapper = modelMapper;
    }

    @Override
    public List<CurrencyResDto> getAllCurrency() {
        return currencyRepo.findAll()
                .stream().map(currency -> mapper.map(currency, CurrencyResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CurrencyResDto> addCurrency(CreateCurrencyReqDto createCurrencyReqDto) {
        var countryCode = validateCurrency(createCurrencyReqDto);

        var newCurrency = new Currency(countryCode, createCurrencyReqDto.getCountry().toLowerCase());

        newCurrency = currencyRepo.save(newCurrency);

        return currencyRepo.findAll()
                .stream().map(currency -> mapper.map(currency, CurrencyResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyResDto updateCurrency(String id) {
        var currency = currencyRepo.findById(id)
                .orElseThrow(() -> new CampApiException(HttpStatus.NOT_FOUND, String.format("currency with id %s not found", id)));

        // fetch currencyCode
        var currencyCode = fetchCurrencyCodeForCountry(currency.getCountry());
        currency.setCurrencyCode(currencyCode);

        currencyRepo.save(currency);
        return mapper.map(currency, CurrencyResDto.class);
    }

    private String fetchCurrencyCodeForCountry(String country) {
        // third party api
        return country;
    }

    private String validateCurrency(CreateCurrencyReqDto createCurrencyReqDto) {
        if(currencyRepo.findByCountry(createCurrencyReqDto.getCountry()).isPresent()){
            throw new CampApiException(HttpStatus.NOT_FOUND, String.format("currency of country %s is already present", createCurrencyReqDto.getCountry()));
        }
        return fetchCurrencyCodeForCountry(createCurrencyReqDto.getCountry());
    }

    @Override
    public String deleteCurrency(String id) {
        currencyRepo.deleteById(id);
        return id+" deleted";
    }

    @Override
    public Boolean currencyValid(String currencyCode) {
        return currencyRepo.findByCurrencyCode(currencyCode).isPresent();
    }
}
