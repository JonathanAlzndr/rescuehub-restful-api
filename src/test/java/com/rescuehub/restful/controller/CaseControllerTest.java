package com.rescuehub.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CreateCaseRequest;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.repository.CaseRepository;
import com.rescuehub.restful.repository.UserRepository;
import com.rescuehub.restful.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        caseRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setNik("12345678");
        user.setName("Jonathan Kamagi");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setTelephoneNumber("12345");
        user.setKecamatan("test");
        user.setLingkungan("test");
        user.setKelurahan("test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
        userRepository.save(user);
    }


    @Test
    void createCaseSuccess() throws Exception {
        CreateCaseRequest createCaseRequest = new CreateCaseRequest();
        createCaseRequest.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        createCaseRequest.setLongitude("234561.213123");
        createCaseRequest.setLatitude("323432840.3");
        createCaseRequest.setStatus("Baru");

        mockMvc.perform(
                post("/api/cases")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCaseRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("Case created successfully", response.getData());
        });
    }


}