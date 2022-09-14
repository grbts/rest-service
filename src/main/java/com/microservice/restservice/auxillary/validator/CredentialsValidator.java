package com.microservice.restservice.auxillary.validator;

import com.microservice.restservice.auxillary.exception.InvalidPasswordException;
import com.microservice.restservice.auxillary.exception.InvalidEmailException;
import com.microservice.restservice.dao.UserRepository;
import com.microservice.restservice.dto.Credentials;
import com.microservice.restservice.dto.User;

public class CredentialsValidator {

    public static boolean isCredentialsCorrect(Credentials credentials, UserRepository userRepository) {
        User user = userRepository.findByEmail(credentials.getEmail());
        if (user == null) {
            throw new InvalidEmailException("Email " + credentials.getEmail() + " does not exist");
        }
        if (credentials.getPassword() == null || credentials.getPassword().equals("")) {
            throw new InvalidPasswordException("Password should not be empty");
        }
        if (credentials.getPassword().length() < 8) {
            throw new InvalidPasswordException("Too short password: " + credentials.getPassword().length()
                    + " symbols. Should be no less 8");
        }
        if (credentials.getPassword().length() > 16) {
            throw new InvalidPasswordException("Too long password: " + credentials.getPassword().length()
                    + " symbols. Should be no longer 16");
        }
        if (!user.getPassword().equals(credentials.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }
        return true;
    }
}
