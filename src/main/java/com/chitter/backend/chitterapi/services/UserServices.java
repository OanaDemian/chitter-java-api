package com.chitter.backend.chitterapi.services;
import com.chitter.backend.chitterapi.model.User;
import com.chitter.backend.chitterapi.payloads.exceptions.UnauthorisedException;
import com.chitter.backend.chitterapi.payloads.exceptions.UsernameTakenException;
import com.chitter.backend.chitterapi.payloads.responses.SignInResponse;
import com.chitter.backend.chitterapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        Optional<User> dbUser =  userRepository.findByUsername(user.getUsername());
        if(dbUser.isPresent()) {
            throw new UsernameTakenException();
        } else {
            return userRepository.save(user);
        }
    }

    public SignInResponse getUserAuthDetails(String username, String password) {
        Optional<User> user =  userRepository.findByUsername(username);
        if(user.isPresent()) {
            if (password.equals(user.get().getPassword())) {
                return new SignInResponse(user.get());
            } else {
                throw new UnauthorisedException();
            }
        } else {
            throw new UnauthorisedException();
        }
    }
}
