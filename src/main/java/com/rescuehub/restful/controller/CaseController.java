package com.rescuehub.restful.controller;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CaseDetailResponse;
import com.rescuehub.restful.model.CreateCaseRequest;
import com.rescuehub.restful.model.UserResponse;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.service.CaseService;
import com.rescuehub.restful.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class CaseController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private UserService userService;

    // Post a new case
    @PostMapping(
            path = "/api/cases",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(User user, @RequestBody CreateCaseRequest request) {
        caseService.create(user, request);
        return WebResponse.<String>builder().data("Case created successfully").build();
    }

    // Get Detail Case
    @GetMapping(
            path = "/api/cases/{caseId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CaseDetailResponse> get(User user, @PathVariable("caseId") Integer caseId) {
        val caseDetailResponse = caseService.get(user, caseId);
        return WebResponse.<CaseDetailResponse>builder().data(caseDetailResponse).build();
    }
}
