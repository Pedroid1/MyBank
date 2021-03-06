package com.example.mybank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.mybank.R;
import com.example.mybank.model.Client;
import com.example.mybank.ui.fragments.HomeFragment;
import com.example.mybank.ui.fragments.HomeViewModel;
import com.example.mybank.ui.fragments.ProfileFragment;


public class HomeActivity extends AppCompatActivity {

    public static final String EMAIL_KEY = "EMAIL";
    public static final String SENHA_KEY = "SENHA";

    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        if (viewModel.getMyDB() == null)
            viewModel.setMyDB(this);

        if (viewModel.getCurrentClient() == null) {
            Intent intent = getIntent();
            String email, senha;
            email = intent.getStringExtra(EMAIL_KEY);
            senha = intent.getStringExtra(SENHA_KEY);

            Client currentClient = viewModel.getMyDB().findClientByEmailAndPassword(email, senha);
            if (currentClient != null) {
                viewModel.setCurrentClient(currentClient);
            }
        }

        replaceHomeFragment();
    }

    private void replaceHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
    }

}