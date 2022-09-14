package com.microservice.restservice.controller;

import com.microservice.restservice.auxillary.JWTSupport;
import com.microservice.restservice.auxillary.validator.CredentialsValidator;
import com.microservice.restservice.dao.UserRepository;
import com.microservice.restservice.dto.Credentials;
import com.microservice.restservice.dto.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(path = "/addnewuser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> addNewUser(@Valid @RequestBody User user) {
        user.setUuid(UUID.randomUUID().toString());
        user.setAccessLevel("USER");
        userRepository.save(user);
        return ResponseEntity.ok("{User added}");
    }

    @RequestMapping(path = "/providetoken", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> provideToken(@Valid @RequestBody Credentials credentials) {

        CredentialsValidator.isCredentialsCorrect(credentials, userRepository);

        String token = new JWTSupport().generateToken(
                userRepository.findByEmail(credentials.getEmail()).getName(), "122345");
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("token", token);

        return ResponseEntity.ok(responseBody);
    }

}
