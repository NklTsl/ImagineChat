package com.imagine.chattingapp.client.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidation {
    private static final Pattern VALID_PASSWORD_REGEX = 
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",Pattern.CASE_INSENSITIVE);
    
    private static final Pattern VALID_PHONE_REGEX = 
            Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");
    
    private static Matcher matcher;
    
    public static boolean validatePhone(String phone){
        matcher = VALID_PHONE_REGEX.matcher(phone);
        return matcher.find();
    }
    
    public static boolean validatePassword(String password){
        matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }
    //private static match(String )
}