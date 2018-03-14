import java.util.function.Consumer;
import java.util.stream.IntStream;

public class CustomStringBuilder implements CharSequence {

     private final StringBuilder sb;

    public CustomStringBuilder() {
        sb = new StringBuilder();
    }

    public CustomStringBuilder append(Object obj) {
        sb.append(obj);
        return this;
    }


    public CustomStringBuilder appendIf(boolean test , Object obj) {

        if(test){
            sb.append(obj);
        }
        return this;
    }

    public int length() {
        return sb.length();
    }
    public char charAt(int index) {
        return sb.charAt(index);
    }
    public CharSequence subSequence(int start, int end) {
        return sb.subSequence(start, end);
    }
    @Override
    public IntStream chars() {
        return sb.chars();
    }
    @Override
    public IntStream codePoints() {
        return sb.codePoints();
    }
    @Override
    public String toString() {
        return sb.toString();
    }
    public CustomStringBuilder printf(String str, Object... obj) {
        sb.append(String.format(str, obj));
        return this;
    }
    public CustomStringBuilder appendFormatLn(String str, Object... obj) {
        return append(String.format(str, obj))
                .carriageReturn();
    }

    public CustomStringBuilder appendFormat(String str, Object... obj) {
        return append(String.format(str, obj));
    }

    public CustomStringBuilder carriageReturn() {
        return carriageReturn(1);
    }

    public CustomStringBuilder carriageReturn(int n) {
        for (int i = 0; i < n; i++) {
            sb.append('\n');
        }
        return this;
    }

    public CustomStringBuilder indentBy(int i) {
        sb.append(indentLevel(i));
        return this;
    }

    public CustomStringBuilder spaceIndentBy(int i) {
        sb.append(spaceIndentLevel(i));
        return this;
    }

    public CustomStringBuilder quote(Object str) {
        sb.append("\"")
                .append(str)
                .append("\"");
        return this;
    }

    private static String indentLevel(int level) {
       return String.format("%0"+level+"d", 0).replaceAll("0","\t");
    }

    private static String spaceIndentLevel(int level) {
       return String.format("%0"+level+"d", 0).replaceAll("0"," ");
    }

    public CustomStringBuilder appendLn(Object s) {
        return append(s).carriageReturn();
    }

    public CustomStringBuilder carryAndIndentby(int i) {
        return carriageReturn().indentBy(i);
    }

    public CustomStringBuilder appendCarriable(String classDoc, int level) {
        if (classDoc != null) {
            appendLn(classDoc.replaceAll("\n", "\n" + indentLevel(level)));
        }
        return this;
    }

    public CustomStringBuilder spaceAppend(Object s) {
        return append(' ').append(s);
    }

    public CustomStringBuilder spaceAppend() {
        return append(' ');
    }

    public CustomStringBuilder appendLn() {
        return appendLn("");
    }

    public CustomStringBuilder appendStartTag(String tagName) {
        return append(String.format("<%s>", tagName));
    }

    public CustomStringBuilder appendStartTagLn(String tagName) {
        return appendLn(String.format("<%s>", tagName));
    }

    public CustomStringBuilder appendEndTag(String tagName) {
        return append(String.format("</%s>", tagName));
    }

    public CustomStringBuilder appendEndTagLn(String tagName) {
        return appendLn(String.format("</%s>", tagName));
    }

    public CustomStringBuilder doIf(boolean condition, Consumer<CustomStringBuilder> function) {
        if (condition) {
            function.accept(this);
        }
        return this;
    }
}
