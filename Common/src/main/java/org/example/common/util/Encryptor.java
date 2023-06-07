package org.example.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    private static final int HASHED_MESSAGE_RADIX = 16;
    private static final int PRECENDING_ZEROS_FOR_MD2 = 32;

    private Encryptor(){
    }

    public static String encryptThisString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);


            StringBuilder hashText = new StringBuilder(no.toString(HASHED_MESSAGE_RADIX));

            // а тут тупо по алгоритму нужно 32
            while (hashText.length() < PRECENDING_ZEROS_FOR_MD2) {
                hashText.insert(0, "0");
            }

            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
