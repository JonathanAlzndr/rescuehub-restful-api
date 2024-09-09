package com.rescuehub.restful.controller;

import com.rescuehub.restful.model.RegisterUserRequest;
import com.rescuehub.restful.model.UserResponse;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rescuehub.restful.entity.User;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping(path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("User created successfully").build();
    }

    // Get detail of the current user
    @GetMapping(
            path = "/api/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(User user) {
        UserResponse userResponse = userService.get(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

}
