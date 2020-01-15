package net.io.cortex.model;

class Validation {
    static boolean validateLogin(String login) {
        String pattern = "(?=.*[a-z]).{3,}";
        if (!login.equals("")) {
            return login.matches(pattern);
        }
        return false;
    }

    static boolean validatePassword(String password) {
        if (password != null) {
            String patterns = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
            return password.matches(patterns);
        }
        return false;
    }
}
