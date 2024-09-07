package com.rescuehub.restful.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rescuehub.restful.entity.Case;
import com.rescuehub.restful.entity.CaseReport;
import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CaseDetailResponse;
import com.rescuehub.restful.model.CreateCaseRequest;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.repository.CaseReportRepository;
import com.rescuehub.restful.repository.CaseRepository;
import com.rescuehub.restful.repository.UserRepository;
import com.rescuehub.restful.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private CaseReportRepository caseReportRepository;

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

        Case cases = new Case();
        cases.setUser(user);
        cases.setStatus("Baru");
        cases.setLatitude("1234.567");
        cases.setLongitude("123.43535");
        cases.setCaseReportList(new ArrayList<>());

        caseRepository.save(cases);
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

    @Test
    void getDetailCaseNotFound() throws Exception {
        mockMvc.perform(
                get("/api/cases/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getDetailCaseSuccess() throws Exception {

        Case cases = new Case();
        cases.setUser(userRepository.findById("12345678").orElse(null));
        cases.setStatus("Baru");
        cases.setLatitude("1234.567");
        cases.setLongitude("123.43535");
        cases.setCaseReportList(new ArrayList<>());
        caseRepository.save(cases);

        Case myCase = caseRepository.findById(44).orElse(null);
        if(myCase != null) {
            ArrayList<CaseReport> caseReportsArrayList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                CaseReport caseReport = new CaseReport();
                caseReport.setImageUrl("imageUrl");
                caseReport.setDescription("description");
                caseReport.setUser(userRepository.findById("12345678").orElse(null));
                caseReport.setACase(cases);

                caseReportsArrayList.add(caseReport);
            }

            myCase.setCaseReportList(caseReportsArrayList);
        }

        mockMvc.perform(
                get("/api/cases/44")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<CaseDetailResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(response.getData().getId(), response.getData().getId());
            assertEquals(cases.getStatus(), response.getData().getStatusKasus());
            assertEquals(cases.getUser().getTelephoneNumber(), response.getData().getNomorTeleponPengguna());
            assertEquals(cases.getLatitude(), response.getData().getLatitude());
        });
    }





}