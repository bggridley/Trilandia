package util;

public class StringUtils {

    public static boolean isAlphaNumeric(String text) {
        return !text.matches("^.*[^a-zA-Z0-9 ].*$");
    }
}
