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
import android.widget.Toast;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentFirstEnderecoRegisterBinding;
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
        updateUi();

        bind.continuarBtn.setOnClickListener(view1 -> {
            if (bind.cepEdt.isDone()) {
                String cep = bind.cepEdt.getText().toString().trim();
                requestEndereco(cep);
            } else {
                if (bind.cepEdt.getText().toString().isEmpty())
                    EditTextError.setEdtError(bind.cepEdt, "Campo obrigatório", requireContext());
                else
                    EditTextError.setEdtError(bind.cepEdt, "Complete o campo", requireContext());
            }
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceThirdRegisterFragment();
        });
    }

    private void updateUi() {
        viewModel.getCep().observe(requireActivity(), cep -> {
            bind.cepEdt.setText(cep);
        });
    }

    private void replaceThirdRegisterFragment() {
        requireActivity().onBackPressed();
    }

    private void replaceSecondEnderecoRegisterFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new SecondEnderecoRegisterFragment()).addToBackStack(null).commit();
    }

    private void requestEndereco(String cep) {
        RetrofitMethods methods = RetrofitInstance.getRetrofitInstance().create(RetrofitMethods.class);
        Call<Endereco> data = methods.getCep(cep);
        data.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        viewModel.setEndereco(response.body());
                    requireActivity().runOnUiThread(() -> {
                        replaceSecondEnderecoRegisterFragment();
                    });
                } else {
                    EditTextError.setEdtError(bind.cepEdt, "Cep não encontrado", requireContext());
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(requireContext(), "Erro ao buscar o cep", Toast.LENGTH_SHORT).show();
            }
        });
    }
}