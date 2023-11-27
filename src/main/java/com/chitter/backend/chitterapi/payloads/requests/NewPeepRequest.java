package com.chitter.backend.chitterapi.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("NewPeepRequest")

public class NewPeepRequest {
    @JsonProperty("username")
    @NotEmpty(message = "Peep must have a username")
    private String username;

    @JsonProperty("peepContent")
    @NotEmpty(message = "Peep must have content")
    private String peepContent;

    public String getUsername() {
        return username;
    }

    public String getPeepContent() {
        return peepContent;
    }
}
