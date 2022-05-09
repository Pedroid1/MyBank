package com.example.mybank.ui.utils;

import android.util.Patterns;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {

    private static Locale ptBr = new Locale("pt", "BR");

    public static boolean validateEmail(String email) {

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        else
            return false;
    }

    public static String getCurrencyInstance(Double valor) {
        Double d = 10.1;

        String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor);
        return valorString;
    }

}
