package com.microservice.restservice.auxillary;

import com.microservice.restservice.dao.UserRepository;
import com.microservice.restservice.dto.User;

public class DataHelper {

    public static void setupTestData(UserRepository userRepository) {
        userRepository.deleteDb();
        userRepository.createDb();
        userRepository.useDb();
        userRepository.createUserTable();
        userRepository.save(new User("uuid", "Ivan", "USER", "Ac#1qpwo", "test2@email.com"));
    }

    public static void clearTestData(UserRepository userRepository) {
        userRepository.deleteDb();
        userRepository.createDb();
    }
}
