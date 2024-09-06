package com.rescuehub.restful.controller;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.CreateCaseRequest;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.service.CaseService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CaseController {

    @Autowired
    private CaseService caseService;

    @PostMapping(
            path = "/api/cases",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(User user, @RequestBody CreateCaseRequest request) {
        caseService.create(user, request);
        return WebResponse.<String>builder().data("Case created successfully").build();
    }
}
