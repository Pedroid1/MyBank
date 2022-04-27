package com.example.mybank.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mybank.R;

public class HomeActivity extends AppCompatActivity {

    public static final String EMAIL_KEY = "EMAIL";
    public static final String SENHA_KEY = "SENHA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}