package com.chitter.backend.chitterapi.repositories;

import com.chitter.backend.chitterapi.model.Peep;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeepRepository extends MongoRepository<Peep, String> {

}
