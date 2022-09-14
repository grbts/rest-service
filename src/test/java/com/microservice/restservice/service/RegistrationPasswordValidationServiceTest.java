package com.microservice.restservice.service;

import com.google.gson.Gson;
import com.microservice.restservice.auxillary.PostRequest;
import com.microservice.restservice.controller.AuthController;
import com.microservice.restservice.dao.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// Example of spring tests

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class RegistrationPasswordValidationServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @Test
    public void whenPostRequestToAddnewuserAndCorrectData8Symbols_then200Response() throws Exception {
        String responseBody = "{User added}";
        postRequest.sendAndCheck(new RequestBody("Ac#1qpwo").toJson(), responseBody, 200);
    }

    @Test
    public void whenPostRequestToAddnewuserAndCorrectData16Symbols_then200Response() throws Exception {
        String responseBody = "{User added}";
        postRequest.sendAndCheck(new RequestBody("Ac#1qpwoAc#1qpwo").toJson(), responseBody, 200);
    }

    @Test
    public void whenPostRequestToAddnewuserAndCorrectData7Symbols_then400Response() throws Exception {
        String responseBody = "{\"password\":\"Password must be 8 or more characters in length.\"}";
        postRequest.sendAndCheck(new RequestBody("Ac#1qpw").toJson(), responseBody, 400);
    }

    @Test
    public void whenPostRequestToAddnewuserAndCorrectData17Symbols_then400Response() throws Exception {
        String responseBody = "{\"password\":\"Password must be no more than 16 characters in length.\"}";
        postRequest.sendAndCheck(new RequestBody("Ac#1qpwoAc#1qpwoA").toJson(), responseBody, 400);
    }

    @Test
    public void whenPostRequestToAddnewuserAndTooSHortPassword_then400Response() throws Exception {
        String responseBody = "{\"password\":\"" +
                "Password must be 8 or more characters in length.," +
                "Password must contain 1 or more uppercase characters.," +
                "Password must contain 1 or more lowercase characters.," +
                "Password must contain 1 or more special characters.," +
                "Password must contain 1 or more digit characters.\"}";
        postRequest.sendAndCheck(new RequestBody(" ").toJson(), responseBody, 400);
    }

    @Test
    public void whenPostRequestToAddnewuserAndPasswordHasNoUpperCase_then400Response() throws Exception {
        String responseBody = "{\"password\":\"Password must contain 1 or more uppercase characters.\"}";
        postRequest.sendAndCheck(new RequestBody("ac#1qpwo").toJson(), responseBody, 400);
    }

    @Test
    public void whenPostRequestToAddnewuserAndPasswordHasNoLowerCase_then400Response() throws Exception {
        String responseBody = "{\"password\":\"Password must contain 1 or more lowercase characters.\"}";
        postRequest.sendAndCheck(new RequestBody("AC#1QPWO").toJson(), responseBody, 400);
    }

    @Test
    public void whenPostRequestToAddnewuserAndPasswordHasNoDigit_then400Response() throws Exception {
        String responseBody = "{\"password\":\"Password must contain 1 or more digit characters.\"}";
        postRequest.sendAndCheck(new RequestBody("AC#lQPWO").toJson(), responseBody, 400);
    }

    @Test
    public void whenPostRequestToAddnewuserAndPasswordContainQWERTYSequence_then400Response() throws Exception {
        String responseBody = "{\"password\":\"Password contains the illegal QWERTY sequence 'qwerty'.\"}";
        postRequest.sendAndCheck(new RequestBody("AC#1qwerty").toJson(), responseBody, 400);
    }

    @Test
    public void whenPostRequestToAddnewuserAndPasswordContainAphabeticalequence_then400Response() throws Exception {
        String responseBody = "{\"password\":\"Password contains the illegal alphabetical sequence 'abcde'.\"}";
        postRequest.sendAndCheck(new RequestBody("AC#1abcde").toJson(), responseBody, 400);
    }

    PostRequest<String, String, Integer> postRequest = (userCredentials, responseBody, status) -> {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/addnewuser")
                                .content(userCredentials)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(status))
                .andExpect(MockMvcResultMatchers.content().contentType(new MediaType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.content().string(responseBody));
    };

    private class RequestBody {
        RequestBody(String password) {
            name = "Ivan";
            email = "test@email.com";
            this.password = password;
        }
        String name;
        String password;
        String email;

        String toJson() {
            return gson.toJson(this);
        }
    }
}
