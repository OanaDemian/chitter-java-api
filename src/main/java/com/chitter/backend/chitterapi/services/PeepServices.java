package com.chitter.backend.chitterapi.services;

import com.chitter.backend.chitterapi.model.Peep;
import com.chitter.backend.chitterapi.model.User;
import com.chitter.backend.chitterapi.payloads.exceptions.UsernameNotFoundException;
import com.chitter.backend.chitterapi.payloads.requests.NewPeepRequest;
import com.chitter.backend.chitterapi.repositories.PeepRepository;
import com.chitter.backend.chitterapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeepServices {
    @Autowired
    private PeepRepository peepRepository;

    @Autowired
    private UserRepository userRepository;
    public List<Peep> getAllPeeps() {
        return peepRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
    }

    public Peep addPeep(NewPeepRequest newPeepRequest) {
        Optional<User> user = userRepository.findByUsername(newPeepRequest.getUsername());
        if (user.isPresent()) {
            Peep peep = new Peep(user.get(), newPeepRequest.getPeepContent());
            return peepRepository.save(peep);
        } else {
            throw new UsernameNotFoundException();
        }
    }
}
