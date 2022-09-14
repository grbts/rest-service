package com.microservice.restservice.auxillary;

import com.microservice.restservice.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// // TODO: Delete class
//  Temporary class for manual test purposes

@Component
public class PostStartActions {
//
//    @Autowired
//    UserRepository userRepository;
//
//    public PostStartActions(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void initializeDb(){
////        userRepository.createDb();
////        userRepository.useDb();
//        userRepository.createUserTable();
//        userRepository.createMessageTable();
//    }
}
