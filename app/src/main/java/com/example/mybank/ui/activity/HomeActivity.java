package com.example.mybank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mybank.R;
import com.example.mybank.model.Cliente;
import com.example.mybank.network.RetrofitInstance;
import com.example.mybank.network.RetrofitMethods;
import com.example.mybank.ui.fragments.HomeFragment;
import com.example.mybank.ui.fragments.HomeViewModel;
import com.example.mybank.ui.fragments.ProfileFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    public static final String ID_KEY = "ID";

    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if(viewModel.getCurrentClient() == null) {
            Intent getIntent = getIntent();
            Integer id = getIntent.getIntExtra(ID_KEY, -1);
            getClientPorId(id);
        } else {
            replaceHomeFragment();
        }


    }

    private void getClientPorId(Integer id) {
        RetrofitMethods methods = RetrofitInstance.getRetrofitInstance().create(RetrofitMethods.class);
        Call<Cliente> data = methods.buscarPorId(id);

        data.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if(response.isSuccessful()) {
                    viewModel.setCurrentClient(response.body());
                    replaceHomeFragment();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Erro ao buscar cliente", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void replaceHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
    }

}