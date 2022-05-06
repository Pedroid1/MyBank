package com.example.mybank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.mybank.R;
import com.example.mybank.ui.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    public static final String EMAIL_KEY = "EMAIL";
    public static final String SENHA_KEY = "SENHA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent intent = getIntent();
        String email, senha;
        email = intent.getStringExtra(EMAIL_KEY);
        senha = intent.getStringExtra(SENHA_KEY);
        Bundle args = new Bundle();
        args.putString(EMAIL_KEY, email);
        args.putString(SENHA_KEY, senha);


        replaceProfileFragment(args);
    }

    private void replaceProfileFragment(Bundle args) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment.class, args).commit();
    }

}