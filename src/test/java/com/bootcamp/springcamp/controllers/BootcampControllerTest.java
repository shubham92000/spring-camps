package com.bootcamp.springcamp.controllers;

import com.bootcamp.springcamp.dtos.bootcamp.CreateBootcampReqDto;
import com.bootcamp.springcamp.security.JwtTokenProvider;
import com.bootcamp.springcamp.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(
        value = BootcampController.class
)
public class BootcampControllerTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BootcampService bootcampService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void notNull(){
        assert context!=null;
        assert mockMvc!=null;
        System.out.println(context.getBeanDefinitionCount());
        for(var c: context.getBeanDefinitionNames()){
            System.out.println(c);
        }
    }

    @Test
    public void createBootcamp() throws Exception {
        CreateBootcampReqDto createBootcampReqDto = new CreateBootcampReqDto(
                "name",
                "description",
                "site",
                "4567643456",
                "df@gmail.com"
        );

        var res = mockMvc.perform(post("/api/v1/bootcamps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBootcampReqDto))
                .header("Authorization", "Bearer 30fxcjkl")
        );

        res.andDo(MockMvcResultHandlers.print());
    }
}
