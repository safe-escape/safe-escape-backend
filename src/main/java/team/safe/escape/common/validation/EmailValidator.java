package team.safe.escape.common.validation;

import java.util.regex.Pattern;

public class EmailValidator {

    private EmailValidator() {
    }

    public static boolean isInvalidEmail(String email) {
        return !isValidEmail(email);
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        return Pattern.matches(regex, email);
    }
}
