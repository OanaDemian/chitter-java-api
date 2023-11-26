package com.chitter.backend.chitterapi.controllers;

import com.chitter.backend.chitterapi.model.User;
import com.chitter.backend.chitterapi.payloads.requests.SignInRequest;
import com.chitter.backend.chitterapi.payloads.responses.SignInResponse;
import com.chitter.backend.chitterapi.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Validated
public class UserController {
    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }
    @PostMapping(value="/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) {
        return userServices.addUser(user);
    }

    @PostMapping(value="/signin")
    public SignInResponse signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return userServices.getUserAuthDetails(signInRequest.getUsername(), signInRequest.getPassword());
    }
}
