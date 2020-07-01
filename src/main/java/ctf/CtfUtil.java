package ctf;

public class CtfUtil {
    public static String trimAscii(String src) {
        return src.chars().filter(ch -> Character.isLetterOrDigit(ch)).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
