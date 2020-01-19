package net.io.cortex.model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sebas
 */
class Validation {

    /**
     * @param login
     * @return
     */
    static boolean validateLogin(String login) {
        String pattern = "(?=.*[a-z]).{3,}";
        if (!login.equals("")) {
            System.out.println("login: " + login.matches(pattern));
            return login.matches(pattern);
        }
        return false;
    }

    /**
     *
     * @param password
     * @return
     */
    static boolean validatePassword(String password) {
        if (password != null) {
            String pattern = "((?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{8,20})";

            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(password);
            return m.matches();
        }
        return false;
    }
}
