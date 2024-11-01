package com.rescuehub.restful.controller;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.*;
import com.rescuehub.restful.service.CaseService;
import com.rescuehub.restful.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping(
            path = "/api/cases",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PagingResponse<List<AllCasesResponse>> get(User user, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        val response = caseService.get(user, page, size);
        return PagingResponse.<List<AllCasesResponse>>builder()
                .data(response)
                .page(page)
                .size(size)
                .build();
    }

    @PutMapping(
            path = "api/cases/{caseId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updateCase(User user, @RequestBody UpdateCaseStatusRequest request, @PathVariable("caseId") Integer caseId) {
        caseService.updateStatus(user, request, caseId);
        return WebResponse.<String>builder()
                .data("Case updated successfully")
                .build();
    }

}
