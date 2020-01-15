package net.io.cortex.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
    Authentication emptyAuth = new Authentication();
    Authentication auth = new Authentication("Abc", "ZAQ!@WSXcd");
    Authentication admin = new Authentication("admin", "ZAQ!@WSXcd");

    @Test
    void validateLogin() {
        assertEquals("admin", admin.getLogin(), "admin check");
        assertEquals("Abc", auth.getLogin(), "auth check");
        assertNull(admin.getLogin(), "null check");
    }

    @Test
    void validatePassword() {
        String patternDigit = "(?=.*[0-9])";
        String patternLowerCase = "(?=.*[a-z])";
        String patternUpperCase = "(?=.*[A-Z])";
        String patternSpecialChar = "(?=.*[@#$%^&+=])";
        String patternNoWhiteSpace = "(?=\\S+$)";
        String allPatterns = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

        String withoutDigit = "AlaMaKota@";
        String withoutSpecial = "AlaMaKotaaa2";
        String withoutLower = "AMAKOTAAA@2";
        String withoutUpper = "alamakotaa@2";
        String withWhiteSpace = "al am akotaa@2";
        String toShort = "alM@2";
        String strongPassword = "AlaMa@2Kota";

        assertNull(null);

        //(?=.*[0-9]) a digit must occur at least once
        assertFalse(withoutDigit.matches(patternDigit));
        assertTrue(withoutSpecial.matches(patternDigit));
        assertTrue(withoutLower.matches(patternDigit));
        assertTrue(withoutUpper.matches(patternDigit));
        assertTrue(withWhiteSpace.matches(patternDigit));
        assertTrue(toShort.matches(patternDigit));
        assertTrue(strongPassword.matches(patternDigit));

        //(?=.*[a-z]) a lower case letter must occur at least once
        assertFalse(withoutLower.matches(patternLowerCase));
        assertTrue(withoutDigit.matches(patternLowerCase));
        assertTrue(withoutSpecial.matches(patternLowerCase));
        assertTrue(withoutUpper.matches(patternLowerCase));
        assertTrue(withWhiteSpace.matches(patternLowerCase));
        assertTrue(toShort.matches(patternLowerCase));
        assertTrue(strongPassword.matches(patternLowerCase));

        //(?=.*[A-Z]) an upper case letter must occur at least once
        assertFalse(withoutUpper.matches(patternUpperCase));
        assertTrue(withoutDigit.matches(patternUpperCase));
        assertTrue(withoutLower.matches(patternUpperCase));
        assertTrue(withoutSpecial.matches(patternUpperCase));
        assertTrue(withWhiteSpace.matches(patternUpperCase));
        assertTrue(toShort.matches(patternUpperCase));
        assertTrue(strongPassword.matches(patternUpperCase));

        //(?=.*[@#$%^&+=]) a special character must occur at least once
        assertFalse(withoutSpecial.matches(patternSpecialChar));
        assertTrue(withoutDigit.matches(patternSpecialChar));
        assertTrue(withoutLower.matches(patternSpecialChar));
        assertTrue(withoutUpper.matches(patternSpecialChar));
        assertTrue(withWhiteSpace.matches(patternSpecialChar));
        assertTrue(toShort.matches(patternSpecialChar));
        assertTrue(strongPassword.matches(patternSpecialChar));

        // (?=\\S+$) no whitespace allowed in the entire string
        assertFalse(withWhiteSpace.matches(patternNoWhiteSpace));
        assertTrue(withoutDigit.matches(patternNoWhiteSpace));
        assertTrue(withoutLower.matches(patternNoWhiteSpace));
        assertTrue(withoutUpper.matches(patternNoWhiteSpace));
        assertTrue(withoutSpecial.matches(patternNoWhiteSpace));
        assertTrue(toShort.matches(patternNoWhiteSpace));
        assertTrue(strongPassword.matches(patternNoWhiteSpace));

        //All patterns
        //.{8,} at least 8 characters
        assertFalse(withWhiteSpace.matches(allPatterns));
        assertFalse(withoutDigit.matches(allPatterns));
        assertFalse(withoutLower.matches(allPatterns));
        assertFalse(withoutUpper.matches(allPatterns));
        assertFalse(withoutSpecial.matches(allPatterns));
        assertFalse(toShort.matches(allPatterns));
        assertTrue(strongPassword.matches(allPatterns));
    }
}
