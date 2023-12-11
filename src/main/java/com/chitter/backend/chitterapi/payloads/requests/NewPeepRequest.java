package com.chitter.backend.chitterapi.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public class NewPeepRequest {
    @JsonProperty("username")
    @NotEmpty(message = "Peep must have a username")
    private String username;

    @JsonProperty("content")
    @NotEmpty(message = "Peep must have content")
    private String content;

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
