package com.imagine.chattingapp.client.view;

import com.sun.prism.impl.PrismSettings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterValidation extends LoginValidation{
    public static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$");
    Matcher matcher;
    public boolean validateEmail(String email){
       matcher = VALID_EMAIL_REGEX.matcher(email);
        return matcher.find();
    }
    
}
