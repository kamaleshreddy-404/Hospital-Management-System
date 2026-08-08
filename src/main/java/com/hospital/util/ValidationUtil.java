package com.hospital.util;

import java.util.regex.Pattern;

/**
 * Common Input Validation Helpers.
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static int parseAge(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr.trim());
            return age >= 0 && age <= 120 ? age : -1;
        } catch (Exception e) {
            return -1;
        }
    }
}
