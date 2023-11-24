package com.chitter.backend.chitterapi.services;

import com.chitter.backend.chitterapi.model.Peep;
import com.chitter.backend.chitterapi.repositories.PeepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeepServices {
    @Autowired
    private PeepRepository peepRepository;
    public List<Peep> getAllPeeps() {
        return peepRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
    }
}
