package pl.blackwater.rpg.util;

public class StringUtil {

    public static boolean isAlphanumeric(String s){
        return s.matches("^[a-zA-Z0-9_]*$");
    }
    public static boolean isAlphanumericBigLetters(String s){
        return s.matches("^[A-Z0-9_]*$");
    }
}
