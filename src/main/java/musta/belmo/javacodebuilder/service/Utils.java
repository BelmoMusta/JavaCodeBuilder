package musta.belmo.javacodebuilder.service;

import com.github.javaparser.ast.CompilationUnit;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Utils {
    /**
     * uncapitalize the inout String
     *
     * @param input {@link String}
     * @return String
     */
    public static String unCapitalize(String input) {
        String output = input;
        if (input != null && !input.isEmpty()) {
            output = Character.toLowerCase(input.charAt(0)) +
                    input.substring(1);
        }
        return output;
    }

    /**
     * Writes the compilation unit to the given output
     *
     * @param resultUnit {@link CompilationUnit}
     * @param out        {@link OutputStream}
     */
    public static void writeToOutput(CompilationUnit resultUnit, OutputStream out) {
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.write(resultUnit.toString());
        printWriter.flush();
        printWriter.close();
    }
}
