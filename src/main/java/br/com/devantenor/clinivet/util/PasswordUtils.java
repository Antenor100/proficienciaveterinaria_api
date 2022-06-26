package br.com.devantenor.clinivet.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

public class PasswordUtils {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String getEightDigitsRandomPassword() {
        char[] chart = {'0','1','2','3','4','5','6','7','8','9'};

        char[] senha = new char[8];

        int chartLenght = chart.length;

        Random rdm = new Random();

        for (int i=0; i<8; i++) senha[i] = chart[rdm.nextInt(chartLenght)];

        return new String(senha);
    }

    public static String encryptPassword(String targetPassword) {
        return bCryptPasswordEncoder.encode(targetPassword);
    }

    public static boolean verifyIfBCryptPasswordMatches(String targetPassword, String encryptedPassword) {
        return bCryptPasswordEncoder.matches(targetPassword, encryptedPassword);
    }
}
