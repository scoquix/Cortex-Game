package net.io.cortex.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTest {
    @Test
    void validateLogin() {
        String toSmall = "aa";
        String withoutLower = "ABBA";
        String proper = "Aaa";
        String nullValue = "";
        assertTrue(Validation.validateLogin(proper));
        assertFalse(Validation.validateLogin(toSmall));
        assertFalse(Validation.validateLogin(withoutLower));
        assertFalse(Validation.validateLogin(nullValue));
    }

    @Test
    void validatePassword() {
        String withoutDigit = "AlaMaKota@";
        String withoutSpecial = "Ala23";
        String withoutLower = "AMAKOTAAA@2";
        String withoutUpper = "alamakotaa@2";
        String withWhiteSpace = "al am akotaa@2";
        String toShort = "alM@2";
        String strongPassword = "AlaMa@2Kota";

        //(?=.*[0-9]) a digit must occur at least once
        //(?=.*[a-z]) a lower case letter must occur at least once
        //(?=.*[A-Z]) an upper case letter must occur at least once
        //(?=.*[@#$%^&+=]) a special character must occur at least once
        // (?=\\S+$) no whitespace allowed in the entire string
        //.{8,} at least 8 characters
        assertFalse(Validation.validatePassword(withoutDigit));
        assertFalse(Validation.validatePassword(withoutSpecial));
        assertFalse(Validation.validatePassword(withoutLower));
        assertFalse(Validation.validatePassword(withoutUpper));
        assertFalse(Validation.validatePassword(withWhiteSpace));
        assertFalse(Validation.validatePassword(toShort));
        assertTrue(Validation.validatePassword(strongPassword));
        assertFalse(Validation.validatePassword(null));
    }
}
