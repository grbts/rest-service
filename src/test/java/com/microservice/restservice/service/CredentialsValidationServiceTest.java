package com.microservice.restservice.service;

import com.google.gson.Gson;
import com.microservice.restservice.auxillary.DataHelper;
import com.microservice.restservice.dao.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CredentialsValidationServiceTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void setupData() {
        DataHelper.setupTestData(userRepository);
    }

    @AfterAll
    public void clearData() {
       // DataHelper.clearTestData(userRepository);
    }

    // Add time
    @Test
    public void whenPasswordIsCorrect_thenReturnsToken() {
        given()
                .body(new RequestBody("test2@email.com", "Ac#1qpwo").toJson())
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/providetoken")
                .then()
                .assertThat()
                .statusCode(200)
                .body("token", equalTo("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSXZhbiIsInN1YiI6Ik15U3ViamVjdCIsImp0aSI6IjEyMjM0NSJ9.Z4p0k9poPa7P7aBNe5D1cept3wwGaKxAD8N8sThvbkw"));
    }

    @Test
    public void whenEmailNotExist_thenInvalidEmailException() {
        check("strange@email.com","Ac#1qpwo", 400, "Email strange@email.com does not exist");
    }

    @Test
    public void whenPasswordTooShort_thenInvalidPasswordException() {
        check("test2@email.com","123", 400, "Too short password: 3 symbols. Should be no less 8");
    }

    @Test
    public void whenPasswordTooLong_thenInvalidPasswordException() {
        check("test2@email.com","12345678123456789", 400, "Too long password: 17 symbols. Should be no longer 16");
    }

    @Test
    public void whenPasswordEmpty_thenInvalidPasswordException() {
        check("test2@email.com","", 400, "Password should not be empty");
    }

    @Test
    public void whenPasswordIncorrect_thenInvalidPasswordException() {
        check("test2@email.com","123456789", 400, "Incorrect password");
    }

    private void check(String email, String password, int statusCode, String errorMessage){
        given()
                .body(new RequestBody(email, password).toJson())
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/providetoken")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body("Error", equalTo(errorMessage));
    }
    //TODO: Delete duplication
    private class RequestBody {
        RequestBody(String email, String password) {
            this.email = email;
            this.password = password;
        }

        String password;
        String email;
        Gson gson = new Gson();

        String toJson() {
            return gson.toJson(this);
        }
    }
}
