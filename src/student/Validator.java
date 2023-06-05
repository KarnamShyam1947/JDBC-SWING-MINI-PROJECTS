package student;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static final String REGEX_MAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String REGEX_PHONE = "^\\d{10}$";

    public static boolean isValidPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile(REGEX_PHONE);
        Matcher matcher = pattern.matcher(phone);

        if (matcher.find()) {
            return true;
        }

        return false;
    }

    public static boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile(REGEX_MAIL);
        Matcher emailMatcher = emailPattern.matcher(email);

        if(emailMatcher.find()) {
            return true;
        }

        return false;
    }
}
