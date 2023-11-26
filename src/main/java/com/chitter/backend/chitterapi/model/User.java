package com.chitter.backend.chitterapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User {

    @Id
    @JsonProperty("_id")
    private String _id;

    @JsonProperty("username")
    @Indexed(unique = true)
    @NotEmpty(message = "Users must have a username")
    private String username;

    @JsonProperty("name")
    @NotEmpty(message = "Users must have a name")
    private String name;

    @JsonProperty("email")
    @NotEmpty(message = "Users must have an email")
    @Indexed(unique = true)
    @Email
    private String email;

    @JsonProperty("password")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    @NotEmpty(message = "Users must have a password")
    private String password;
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

