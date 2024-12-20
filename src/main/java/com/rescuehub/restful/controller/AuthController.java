package com.rescuehub.restful.controller;

import com.rescuehub.restful.entity.User;
import com.rescuehub.restful.model.LoginUserRequest;
import com.rescuehub.restful.model.TokenResponse;
import com.rescuehub.restful.model.WebResponse;
import com.rescuehub.restful.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    // Login
    @PostMapping(
            path = "/api/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    // Logout
    @DeleteMapping(
            path = "/api/auth/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user) {
        authService.logout(user);
        return WebResponse.<String>builder().data("Logout Successfully").build();
    }
}
