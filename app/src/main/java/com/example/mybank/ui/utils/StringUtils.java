package com.example.mybank.ui.utils;

import android.util.Patterns;

public class StringUtils {

    public static boolean validateEmail(String email) {

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        else
            return false;
    }
}
