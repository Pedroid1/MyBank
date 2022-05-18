package com.example.mybank.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentHomeBinding;
import com.example.mybank.ui.utils.StringUtils;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding bind = null;
    private HomeViewModel viewModel = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentHomeBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.setValor("");
        viewModel.setChavePix("");
        updateUi();

        bind.pagamentoCard.setOnClickListener(view1 -> {
            replacePixFragment();
        });

        bind.personImg.setOnClickListener(view1 -> {
            replaceProfileFragment();
        });
    }

    private void replacePixFragment() {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new MakePixFragment()).addToBackStack(null).commit();
    }

    private void updateUi() {
        bind.saldoTxt.setText(StringUtils.getCurrencyInstance(viewModel.getCurrentClient().getSaldo()));
        bind.nomeCliente.setText(splitName(viewModel.getCurrentClient().getName()));
    }

    private String splitName(String name) {
        String[] splitString = name.split(" ");
        String firstName = splitString[0];
        String secondName = splitString[1];
        return firstName + " " + secondName;
    }

    private void replaceProfileFragment() {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new ProfileFragment()).addToBackStack(null).commit();
    }
}