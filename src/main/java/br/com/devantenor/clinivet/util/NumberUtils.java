package br.com.devantenor.clinivet.util;

public class NumberUtils {
    public static Integer getIntegerOrZero(Integer number) {
        if (number == null) {
            return 0;
        } else {
            return number;
        }
    }
}
