package com.example.mybank.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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

        bind.continueBtn.setOnClickListener(view1 -> {
            String sSenha1, sSenha2;
            sSenha1 = bind.senha1Edt.getText().toString().trim();
            sSenha2 = bind.senha2Edt.getText().toString().trim();

            if (sSenha1.isEmpty() || sSenha2.isEmpty()) {
                if (sSenha1.isEmpty())
                    EditTextError.setEdtError(bind.senha1Edt, "Campo obrigatório", requireContext());
                if (sSenha2.isEmpty())
                    EditTextError.setEdtError(bind.senha2Edt, "Campo obrigatório", requireContext());
            } else {
                if (!sSenha1.equals(sSenha2)) {
                    EditTextError.setEdtError(bind.senha1Edt, "As senhas não correspondem!", requireContext());
                    EditTextError.setEdtError(bind.senha2Edt, "As senhas não correspondem!", requireContext());
                } else {

                    if(sSenha1.length() < 4) {
                        EditTextError.setEdtError(bind.senha1Edt, "A senha deve conter 4 números", requireContext());
                        EditTextError.setEdtError(bind.senha2Edt, "A senha deve conter 4 números", requireContext());
                    } else {
                        viewModel.setSenha(sSenha1);
                        replaceEnderecoRegisterFragment();
                    }
                }
            }
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceFirstRegisterFragment();
        });
    }



    private void replaceFirstRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new FirstRegisterFragment()).commit();
    }

    private void replaceEnderecoRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new FirstEnderecoRegisterFragment()).commit();
    }
}