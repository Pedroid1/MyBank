package com.example.mybank.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.mybank.ui.fragments.FirstRegisterFragment;
import com.example.mybank.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        replaceFirstRegisterFragment();

    }

    private void replaceFirstRegisterFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new FirstRegisterFragment()).commit();
    }
}