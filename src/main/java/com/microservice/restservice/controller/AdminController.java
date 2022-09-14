package com.microservice.restservice.controller;

import com.microservice.restservice.dao.UserRepository;
import com.microservice.restservice.dto.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

// // TODO: Delete class
//  Temporary class for manual test purposes
@RestController
@RequestMapping("admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/createdb")
    public void createDb() {
        userRepository.createDb();
    }

    @PostMapping("/usedb")
    public void usedb() {
        userRepository.useDb();
    }

    @PostMapping("/deletedb")
    public void deleteDb() {
        userRepository.deleteDb();
    }

    @PostMapping("/createusertable")
    public void createUserTable() {
        userRepository.createUserTable();
    }

    @PostMapping("/createmessagetable")
    public void createMessageTable() {
        userRepository.createMessageTable();
    }

    @PostMapping("/createadminaccount")
    public void createAdminAccount() {
        User user = new User(); // Get all data from env variable
        user.setName("Ivan");
        user.setUuid(UUID.randomUUID().toString());
        user.setAccessLevel("ADMIN");
        user.setEmail("test@email.com");
        user.setPassword("Ac#1qpwo"); // Get from env variable
        userRepository.save(user);
    }
}
