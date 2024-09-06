package com.rescuehub.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.RegisterUserRequest;
import com.rescuehub.restful.model.TokenResponse;
import com.rescuehub.restful.model.UserResponse;
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
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

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
    void testRegisterSuccess() throws Exception {

        RegisterUserRequest request = new RegisterUserRequest();
        request.setNik("12345678");
        request.setNamaPengguna("Jonathan Kamagi");
        request.setPassword("rahasia");
        request.setNomorTelepon("12345");
        request.setKecamatan("test");
        request.setLingkungan("test");
        request.setKelurahan("test");

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
           WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });

            assertEquals("User created successfully", response.getData());
        });
    }

    @Test
    void getUserUnauthorized() throws Exception {
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "notfound")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserUnauthorizedTokenNotSend() throws Exception {
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserSuccess() throws Exception {

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
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("12345678", response.getData().getNik());
            assertEquals("Jonathan Kamagi", response.getData().getNamaPengguna());
            assertEquals("12345", response.getData().getNomorTelepon());
            assertEquals("test", response.getData().getKecamatan());
            assertEquals("test", response.getData().getLingkungan());
            assertEquals("test", response.getData().getKelurahan());
        });
    }

    @Test
    void getUserTokenExpired() throws Exception {

        User user = new User();
        user.setNik("12345678");
        user.setName("Jonathan Kamagi");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setTelephoneNumber("12345");
        user.setKecamatan("test");
        user.setLingkungan("test");
        user.setKelurahan("test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() - 1000000L);

        userRepository.save(user);
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

}

