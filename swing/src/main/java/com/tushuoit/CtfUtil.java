package com.tushuoit;

public class CtfUtil {
    public static String trimAscii(String src) {
        return src.chars().filter(Character::isLetterOrDigit).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
