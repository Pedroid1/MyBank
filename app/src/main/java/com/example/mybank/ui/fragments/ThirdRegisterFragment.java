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
import com.example.mybank.databinding.FragmentThirdRegisterBinding;
import com.example.mybank.ui.utils.EditTextError;

public class ThirdRegisterFragment extends Fragment {

    private FragmentThirdRegisterBinding bind = null;
    private RegisterViewModel viewModel = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentThirdRegisterBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        updateUi();

        bind.continueBtn.setOnClickListener(view1 -> {
            String renda, patrimonio;
            renda = bind.rendaEdt.getText().toString().trim();
            patrimonio = bind.patrimonioEdt.getText().toString().trim();

            if (!renda.isEmpty() && !patrimonio.isEmpty()) {
                //Adicionar limites minimos de renda
                //----------------------------------

                viewModel.setRendaMensal(Double.parseDouble(renda));
                viewModel.setPatrimonioLiquido(Double.parseDouble(patrimonio));

                replaceFirstEnderecoRegisterFragment();

            } else {
                if (renda.isEmpty()) {
                    EditTextError.setEdtError(bind.rendaEdt, "Campo obrigatório", requireContext());
                } else if (patrimonio.isEmpty()) {
                    EditTextError.setEdtError(bind.patrimonioEdt, "Campo obrigatório", requireContext());
                } else {

                }
            }
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceSecondRegisterFragment();
        });

    }

    private void updateUi() {
        viewModel.getRendaMensal().observe(requireActivity(), renda -> {
            bind.rendaEdt.setText(String.valueOf(renda));
        });
        viewModel.getPatrimonioLiquido().observe(requireActivity(), patrimonio -> {
            bind.patrimonioEdt.setText(String.valueOf(patrimonio));
        });
    }

    private void replaceSecondRegisterFragment() {
        requireActivity().onBackPressed();
    }

    private void replaceFirstEnderecoRegisterFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new FirstEnderecoRegisterFragment()).addToBackStack(null).commit();
    }
}