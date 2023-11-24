package com.chitter.backend.chitterapi.helpers;
import com.chitter.backend.chitterapi.model.Peep;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
public class JsonFileReader {

    public static List <Peep> fileToObjectList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List <Peep> peeps= objectMapper.readValue(JsonFileReader.class.getResourceAsStream("/data.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Peep.class)
            );
            return peeps;
        } catch(IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
