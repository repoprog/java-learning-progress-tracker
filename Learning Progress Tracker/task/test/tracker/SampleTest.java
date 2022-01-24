package tracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {


    @Test
    @DisplayName("Valid first name tester")
    void validFirstNameTest() {
        Verifier verifier = new Verifier();

        String validName1 = "J-Clause";
        String validName2 = "O'Neil";
        String validName3 = "Ad'nan";

        assertTrue(verifier.verifyName(validName1), validName1);
        assertTrue(verifier.verifyName(validName2), validName2);
        assertTrue(verifier.verifyName(validName3), validName3);
    }

    @Test
    @DisplayName("Invalid first name tester")
    void invalidFirstNameTest() {
        Verifier verifier = new Verifier();

        String invalidName1 = "-Jj";
        String invalidName2 = "j.";
        String invalidName3 = "nam-'e";

        assertFalse(verifier.verifyName(invalidName1), invalidName1);
        assertFalse(verifier.verifyName(invalidName2), invalidName2);
        assertFalse(verifier.verifyName(invalidName3), invalidName3);
    }

    @Test
    @DisplayName("Valid last name tester")
    void validLastNameTest() {
        Verifier verifier = new Verifier();

        String validName1 = "van Helsing";
        String validName2 = "Luise Johnson";
        String validName3 = "Doe";

        assertTrue(verifier.verifyLastName(validName1), validName1);
        assertTrue(verifier.verifyLastName(validName2), validName2);
        assertTrue(verifier.verifyLastName(validName3), validName3);
    }

    @Test
    @DisplayName("Invalid last name tester")
    void invalidLastNameTest() {
        Verifier verifier = new Verifier();

        String invalidName1 = "D.";
        String invalidName2 = ".D";
        String invalidName3 = "ey yo sup-'l";

        assertFalse(verifier.verifyLastName(invalidName1), invalidName1);
        assertFalse(verifier.verifyLastName(invalidName2), invalidName2);
        assertFalse(verifier.verifyLastName(invalidName3), invalidName3);
    }

    @Test
    @DisplayName("Valid email tester")
    void validEmailTest() {
        Verifier verifier = new Verifier();

        String validEmail1 = "jdoe@mail.net";
        String validEmail2 = "jane.doe@yahoo.com";
        String validEmail3 = "jc@google.it";

        assertTrue(verifier.verifyEmail(validEmail1), validEmail1);
        assertTrue(verifier.verifyEmail(validEmail2), validEmail2);
        assertTrue(verifier.verifyEmail(validEmail3), validEmail3);
    }

    @Test
    @DisplayName("Invalid email tester")
    void invalidEmailTest() {
        Verifier verifier = new Verifier();

        String invalidEmail1 = "email";
        String invalidEmail2 = "@email";
        String invalidEmail3 = "email@";

        assertFalse(verifier.verifyEmail(invalidEmail1), invalidEmail1);
        assertFalse(verifier.verifyEmail(invalidEmail2), invalidEmail2);
        assertFalse(verifier.verifyEmail(invalidEmail3), invalidEmail3);
    }
}

