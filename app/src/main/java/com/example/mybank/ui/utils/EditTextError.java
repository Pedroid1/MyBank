package com.example.mybank.ui.utils;

import android.content.Context;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;

import com.example.mybank.R;

public class EditTextError {

    public static void setEdtError(EditText edt, String messageError, Context context) {
        edt.setError(messageError, ActivityCompat.getDrawable(context, R.drawable.ic_error));
        edt.requestFocus();
    }
}
