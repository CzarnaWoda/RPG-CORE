package pl.blackwater.rpg.util;

public class Util {

    public static boolean containsIgnoreCase(String[] array, String element) {
        for (String s : array) {
            if (s.equalsIgnoreCase(element)) {
                return true;
            }
        }
        return false;
    }
}
