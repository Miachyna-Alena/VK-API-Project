package utils;

public class StringModifier {
    public static String getReplacedString(String string) {
        return string.replaceAll(" ", "")
                .replaceAll("\n", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "");
    }
}
