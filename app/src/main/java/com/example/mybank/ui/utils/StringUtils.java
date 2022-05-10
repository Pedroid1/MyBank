package com.example.mybank.ui.utils;

import android.os.Build;
import android.util.Patterns;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StringUtils {

    private static Locale ptBr = new Locale("pt", "BR");

    public static boolean validateEmail(String email) {

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        else
            return false;
    }

    public static boolean validatePhone(String phone) {

        if (Patterns.PHONE.matcher(phone).matches())
            return true;
        else
            return false;
    }

    public static String getCurrencyInstance(Double valor) {
        Double d = 10.1;

        String valorString = NumberFormat.getCurrencyInstance(ptBr).format(valor);
        return valorString;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isValid(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate d = LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int calculoIdade(String date) {
        int ano, mes, dia;
        String[] dateArray = date.split("/");
        dia = Integer.parseInt(dateArray[0]);
        mes = Integer.parseInt(dateArray[1]);
        ano = Integer.parseInt(dateArray[2]);
        return (int) ChronoUnit.YEARS.between(LocalDate.of(ano, mes, dia), LocalDate.now());
    }
}
