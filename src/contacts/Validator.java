package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final String NUMBER_PATTERN = "^\\d{10}$";

    public static boolean isValidNumber(String number) {
        Pattern numberPattern = Pattern.compile(NUMBER_PATTERN);
        Matcher numberMatcher = numberPattern.matcher(number);

        if(numberMatcher.find()) 
            return true;

        return false;
    }
}
