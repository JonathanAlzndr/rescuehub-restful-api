package com.rescuehub.restful.controller;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CreateCaseReportRequest;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.repository.CaseReportRepository;
import com.rescuehub.restful.service.CaseReportService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaseReportController {

    @Autowired
    private CaseReportService caseReportService;

    @PostMapping(
            path = "/api/cases/{caseId}/response",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(User user,
                                      @RequestBody CreateCaseReportRequest request,
                                      @PathVariable("caseId") Integer caseId) {
        caseReportService.create(user, request, caseId);
        return WebResponse.<String>builder()
                .data("Case Report create successfully")
                .build();
    }
}
