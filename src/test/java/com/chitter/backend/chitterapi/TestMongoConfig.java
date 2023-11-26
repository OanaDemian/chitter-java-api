package com.chitter.backend.chitterapi;

import com.chitter.backend.chitterapi.model.Peep;
import com.chitter.backend.chitterapi.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
@Configuration
public class TestMongoConfig {
    private static final String peepsCollectionName ="peeps";
    private static final String usersCollectionName ="users";

    @Bean
    public static MongoTemplate mongoTemplate(){
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/chitter_test"));
    }

    public static void clearPeepsCollection() {
        System.out.println("Deleting existing peeps");
        mongoTemplate().remove(new Query(), peepsCollectionName);
    }

    public static void repopulatePeepsCollection(List<Peep> peeps) {
        System.out.println("Creating peeps");
        System.out.println("Inserting peeps");
        mongoTemplate().insert(peeps, peepsCollectionName);
    }

    public static void clearUsersCollection() {
        System.out.println("Deleting existing users");
        mongoTemplate().remove(new Query(), usersCollectionName);
    }

    public static void repopulateUsersCollection(List<User> users) {
        System.out.println("Creating users");
        System.out.println("Inserting users");
        mongoTemplate().insert(users, usersCollectionName);
    }
}
