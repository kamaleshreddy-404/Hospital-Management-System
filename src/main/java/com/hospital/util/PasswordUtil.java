package com.hospital.util;

import java.security.MessageDigest;

/**
 * Password Security Utility using SHA-256 Hashing.
 */
public class PasswordUtil {

    public static String hashPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) {
        if (inputPassword == null || storedHash == null) return false;
        return hashPassword(inputPassword).equalsIgnoreCase(storedHash);
    }
}
