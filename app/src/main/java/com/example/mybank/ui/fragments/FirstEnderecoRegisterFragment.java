package com.example.mybank.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentFirstEnderecoRegisterBinding;
import com.example.mybank.databinding.FragmentFirstRegisterBinding;
import com.example.mybank.model.Endereco;
import com.example.mybank.network.RetrofitInstance;
import com.example.mybank.network.RetrofitMethods;
import com.example.mybank.ui.utils.EditTextError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstEnderecoRegisterFragment extends Fragment {

    private FragmentFirstEnderecoRegisterBinding bind;
    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentFirstEnderecoRegisterBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        viewModel.getCep().observe(requireActivity(), cep -> {
            bind.cepEdt.setText(cep);
        });

        bind.continuarBtn.setOnClickListener(view1 -> {
            String cep = bind.cepEdt.getText().toString().trim();

            if(cep.isEmpty()) {
                EditTextError.setEdtError(bind.cepEdt, "Campo obrigatório", requireContext());
            } else {
                requestEndereco(cep);
            }
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceSecondRegisterFragment();
        });
    }

    private void replaceSecondRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new SecondRegisterFragment()).commit();
    }

    private void replaceSecondEnderecoRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new SecondEnderecoRegisterFragment()).commit();
    }

    private void requestEndereco(String cep) {
        RetrofitMethods methods = RetrofitInstance.getRetrofitInstance().create(RetrofitMethods.class);
        Call<Endereco> data = methods.getCep(cep);
        data.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if(response.isSuccessful()) {
                    viewModel.setEndereco(response.body());
                    requireActivity().runOnUiThread(() -> {
                        replaceSecondEnderecoRegisterFragment();
                    });
                } else {

                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {

            }
        });
    }
}