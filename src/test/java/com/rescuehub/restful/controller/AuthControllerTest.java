package com.rescuehub.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.LoginUserRequest;
import com.rescuehub.restful.model.TokenResponse;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.repository.UserRepository;
import com.rescuehub.restful.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void loginFailedUserNotFound() throws Exception {

        LoginUserRequest request = new LoginUserRequest();
        request.setNik("test");
        request.setPassword("test");


        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void loginFailedWrongPassword() throws Exception {
        User user = new User();
        user.setNik("12345678");
        user.setName("Jonathan Kamagi");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setTelephoneNumber("12345");
        user.setKecamatan("test");
        user.setLingkungan("test");
        user.setKelurahan("test");

        userRepository.save(user);

        LoginUserRequest request = new LoginUserRequest();
        request.setPassword("salah");
        request.setNik("1234568");

        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void loginSuccess() throws Exception {
        User user = new User();
        user.setNik("12345678");
        user.setName("Jonathan Kamagi");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setTelephoneNumber("12345");
        user.setKecamatan("test");
        user.setLingkungan("test");
        user.setKelurahan("test");

        userRepository.save(user);

        LoginUserRequest request = new LoginUserRequest();
        request.setPassword("test");
        request.setNik("12345678");

        mockMvc.perform(
                        post("/api/auth/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<TokenResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getErrors());
                    assertNotNull(response.getData().getNamaPengguna());
                    assertNotNull(response.getData().getToken());
                    assertNotNull(response.getData().getExpiredAt());

                    User userDb = userRepository.findById("12345678").orElse(null);

                    assertNotNull(userDb);
                    assertEquals(userDb.getName(), response.getData().getNamaPengguna());
                    assertEquals(userDb.getToken(), response.getData().getToken());
                    ;
                    assertEquals(userDb.getTokenExpiredAt(), response.getData().getExpiredAt());

                });
    }
}