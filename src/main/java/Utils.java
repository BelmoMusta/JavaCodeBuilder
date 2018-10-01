public class Utils {

    public static String uncapitalize(String input) {
        if (input == null || input.isEmpty())
            return input;
        return Character.toLowerCase(input.charAt(0)) + input.substring(1);
    }
}
