package musta.belmo.javacodebuilder.service;

public class Utils {

    public static String unCapitalize(String input) {
        String output = input;
        if (input != null && !input.isEmpty())
            output = new StringBuilder()
                    .append(Character.toLowerCase(input.charAt(0)))
                    .append(input.substring(1)).toString();
        return output;
    }
}
