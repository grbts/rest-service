package com.microservice.restservice.unit;

import com.microservice.restservice.auxillary.DataHelper;
import com.microservice.restservice.auxillary.exception.InvalidPasswordException;
import com.microservice.restservice.auxillary.exception.InvalidEmailException;
import com.microservice.restservice.auxillary.validator.CredentialsValidator;
import com.microservice.restservice.dao.UserRepository;
import com.microservice.restservice.dto.Credentials;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CredentialsValidationTest {

    @Autowired
    private UserRepository userRepository;

    private final String validEmail = "test2@email.com";

    @BeforeAll
    public void setupData() {
        DataHelper.setupTestData(userRepository);
    }

    @AfterAll
    public void clearData() {
        DataHelper.clearTestData(userRepository);
    }

    @Test
    public void whenPasswordIsCorrect_thenReturnsTrue() {
        Assertions.assertTrue(
                CredentialsValidator.isCredentialsCorrect(
                        new Credentials(validEmail, "Ac#1qpwo"),
                        userRepository)
        );
    }

    @Test
    public void whenEmailNotExist_thenInvalidEmailException() {
        check(new Credentials("strange@email.com", "Ac#1qpwo"),
                InvalidEmailException.class,
                "Email strange@email.com does not exist");
    }

    @Test
    public void whenPasswordTooShort_thenInvalidPasswordException() {
        check(new Credentials(validEmail, "123"),
                InvalidPasswordException.class,
                "Too short password: 3 symbols. Should be no less 8");
    }

    @Test
    public void whenPasswordTooLong_thenInvalidPasswordException() {
        check(new Credentials(validEmail, "12345678912345678"),
                InvalidPasswordException.class,
                "Too long password: 17 symbols. Should be no longer 16");
    }

    @Test
    public void whenPasswordEmpty_thenInvalidPasswordException() {
        check(new Credentials(validEmail, ""),
                InvalidPasswordException.class,
                "Password should not be empty");
    }

    @Test
    public void whenPasswordIncorrect_thenInvalidPasswordException() {
        check(new Credentials(validEmail, "123456789"),
                InvalidPasswordException.class,
                "Incorrect password");
    }

    private void check(Credentials credentials, Class c, String expectedMessage) {
        Throwable throwable = assertThrows(
                c,
                () -> CredentialsValidator.isCredentialsCorrect(credentials, userRepository)
        );
        Assertions.assertEquals(expectedMessage, throwable.getMessage());
    }
}
