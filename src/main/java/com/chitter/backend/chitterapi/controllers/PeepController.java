package com.chitter.backend.chitterapi.controllers;

import com.chitter.backend.chitterapi.model.Peep;
import com.chitter.backend.chitterapi.payloads.requests.NewPeepRequest;
import com.chitter.backend.chitterapi.services.PeepServices;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @Validated
public class PeepController {
    private Logger logger = LoggerFactory.getLogger(Peep.class);

    private final PeepServices peepServices;
    @Autowired
    public PeepController(PeepServices peepServices) {
        this.peepServices = peepServices;
    }

    @GetMapping(value="/peeps")
    public List<Peep> getAllPeeps() {
        return peepServices.getAllPeeps();
    }

    @PostMapping(value="/peeps")
    @ResponseStatus(HttpStatus.CREATED)
    public Peep addPeep(@Valid @RequestBody NewPeepRequest newPeepRequest) {
        logger.info("Saving peep");
        return peepServices.addPeep(newPeepRequest);
    }
}
