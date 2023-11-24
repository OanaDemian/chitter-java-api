package com.chitter.backend.chitterapi.controllers;

import com.chitter.backend.chitterapi.model.Peep;
import com.chitter.backend.chitterapi.services.PeepServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class PeepController {
    private final PeepServices peepServices;
    @Autowired
    public PeepController(PeepServices peepServices) {
        this.peepServices = peepServices;
    }

    @GetMapping(value="/peeps")
    public List<Peep> getAllPeeps() {
        return peepServices.getAllPeeps();
    }
}
