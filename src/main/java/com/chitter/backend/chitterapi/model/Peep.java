package com.chitter.backend.chitterapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("peeps")
public class Peep {
    @Id
    private String _id;

    private String userId;

    private String name;

    private String username;

    private String content;

    private String date;

    public Peep(){

    }
    public Peep(User user, String content) {
        this.setUsername(user.getUsername());
        this.setName(user.getName());
        this.setUserId(user.get_id());
        this.setContent(content);
        this.setDate(java.time.LocalDateTime.now().toString());
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
