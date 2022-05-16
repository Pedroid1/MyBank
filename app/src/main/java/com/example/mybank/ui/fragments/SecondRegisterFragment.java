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
import com.example.mybank.databinding.FragmentSecondRegisterBinding;
import com.example.mybank.ui.utils.EditTextError;

public class SecondRegisterFragment extends Fragment {

    private FragmentSecondRegisterBinding bind;
    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentSecondRegisterBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        updateUi();

        bind.continueBtn.setOnClickListener(view1 -> {
            String sSenha1, sSenha2;
            sSenha1 = bind.senha1Edt.getText().toString().trim();
            sSenha2 = bind.senha2Edt.getText().toString().trim();

            if (sSenha1.isEmpty() || sSenha2.isEmpty()) {
                if (sSenha1.isEmpty())
                    EditTextError.setEdtError(bind.senha1Edt, "Campo obrigatório", requireContext());
                else
                    EditTextError.setEdtError(bind.senha2Edt, "Campo obrigatório", requireContext());
            } else {
                if (sSenha1.length() < 4 || sSenha2.length() < 4 ) {
                    if(sSenha1.length() < 4)
                        EditTextError.setEdtError(bind.senha1Edt, "A senha deve conter 4 números", requireContext());
                    else
                        EditTextError.setEdtError(bind.senha2Edt, "A senha deve conter 4 números", requireContext());
                } else {

                    if(!sSenha1.equals(sSenha2)) {
                        EditTextError.setEdtError(bind.senha2Edt, "As senhas não correspondem!", requireContext());
                    } else {
                        viewModel.setSenha(sSenha1);
                        replaceThirdRegisterFragment();
                    }
                }
            }
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceFirstRegisterFragment();
        });
    }

    private void updateUi() {
        viewModel.getSenha().observe(requireActivity(), senha -> {
            bind.senha1Edt.setText(senha);
            bind.senha2Edt.setText(senha);
        });
    }


    private void replaceFirstRegisterFragment() {
        requireActivity().onBackPressed();
    }

    private void replaceThirdRegisterFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new ThirdRegisterFragment()).addToBackStack(null).commit();
    }
}