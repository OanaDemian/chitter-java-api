package com.chitter.backend.chitterapi.payloads.responses;

import com.chitter.backend.chitterapi.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("username")
    private String username;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("errorMessage")
    private String errorMessage;
    public SignInResponse(User user) {
        this.email = user.getEmail();
        this.name =user.getName();
        this.userId=user.get_id();
        this.username=user.getUsername();
    }

    public SignInResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
