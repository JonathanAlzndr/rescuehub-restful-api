package com.rescuehub.restful.controller;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CreateCaseReportRequest;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.service.CaseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CaseReportController {

    @Autowired
    private CaseReportService caseReportService;


    @PostMapping(
            path = "/api/cases/{caseId}/response",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(User user, @PathVariable("caseId")Integer caseId, @RequestParam("description") String description, @RequestParam("file")MultipartFile file) {
        CreateCaseReportRequest request = new CreateCaseReportRequest();
        request.setDescription(description);
        try {
            caseReportService.create(user, request, caseId, file);
            return WebResponse.<String>builder()
                    .data("Case report created successfully")
                    .build();
        } catch (Exception e) {
            return WebResponse.<String>builder()
                    .data("Case report failed")
                    .build();
        }
    }
}
