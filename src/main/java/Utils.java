public class Utils {

    public static String uncapitalize(String input) {
        if (input == null || input.isEmpty())
            return input;
        return new StringBuilder()
                .append(Character.toLowerCase(input.charAt(0)))
                .append(input.substring(1)).toString();
    }
}
