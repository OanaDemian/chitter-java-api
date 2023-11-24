package com.chitter.backend.chitterapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("peeps")
public class Peep {
    @Id
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("userId")
    @NotEmpty(message = "Peep must have a userId")
    private String userId;

    @JsonProperty("name")
    @NotEmpty(message = "Peep must have a name")
    private String name;
    @JsonProperty("username")
    @NotEmpty(message = "Peep must have a username")
    private String username;
    @JsonProperty("peepContent")
    @NotEmpty(message = "Peep must have content")
    private String peepContent;
    @JsonProperty("dateCreated")
    @NotEmpty(message = "Peep must have a date created")
    private String dateCreated;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPeepContent() {
        return peepContent;
    }

    public void setPeepContent(String peepContent) {
        this.peepContent = peepContent;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
