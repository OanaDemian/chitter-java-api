package com.chitter.backend.chitterapi.helpers;
import com.chitter.backend.chitterapi.model.Peep;
import com.chitter.backend.chitterapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
public class JsonFileReader {

    public static List <Peep> fileToPeepObjectList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List <Peep> peeps= objectMapper.readValue(JsonFileReader.class.getResourceAsStream("/peepsData.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Peep.class)
            );
            return peeps;
        } catch(IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List <User> fileToUserObjectList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List <User> users= objectMapper.readValue(JsonFileReader.class.getResourceAsStream("/usersData.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class)
            );
            return users;
        } catch(IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
