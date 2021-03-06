package com.example.mybank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mybank.R;
import com.example.mybank.database.MyDatabaseHelper;
import com.example.mybank.databinding.FragmentSecondEnderecoRegisterBinding;
import com.example.mybank.model.Client;
import com.example.mybank.ui.ProgressButton;
import com.example.mybank.ui.activity.HomeActivity;
import com.example.mybank.ui.utils.EditTextError;

public class SecondEnderecoRegisterFragment extends Fragment {

    private FragmentSecondEnderecoRegisterBinding bind;
    private RegisterViewModel viewModel;
    private MyDatabaseHelper myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentSecondEnderecoRegisterBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        updateUi();
        myDB = new MyDatabaseHelper(requireActivity());

        bind.continuarBtn.setOnClickListener(view1 -> {
            bind.continuarBtn.setClickable(false);
            String logradouro, number, districy, city, state;

            logradouro = bind.logradouroEdt.getText().toString().trim();
            number = bind.numberEdt.getText().toString().trim();
            districy = bind.districtEdt.getText().toString().trim();
            city = bind.cityEdt.getText().toString().trim();
            state = bind.stateEdt.getText().toString().trim();

            if(logradouro.isEmpty() || number.isEmpty() || districy.isEmpty() || city.isEmpty() || state.isEmpty() || !bind.termsCheckbox.isChecked()) {
                if(logradouro.isEmpty())
                    EditTextError.setEdtError(bind.logradouroEdt, "Campo obrigat??rio", requireContext());
                else if(number.isEmpty())
                    EditTextError.setEdtError(bind.numberEdt, "Campo obrigat??rio", requireContext());
                else if(districy.isEmpty())
                    EditTextError.setEdtError(bind.districtEdt, "Campo obrigat??rio", requireContext());
                else if(city.isEmpty())
                    EditTextError.setEdtError(bind.cityEdt, "Campo obrigat??rio", requireContext());
                else if(state.isEmpty())
                    EditTextError.setEdtError(bind.stateEdt, "Campo obrigat??rio", requireContext());
                else{
                    Toast.makeText(requireContext(), "Aceite os termos", Toast.LENGTH_SHORT).show();
                }
            } else {
                viewModel.getClient().setAddress(logradouro);
                viewModel.getClient().setNumber(number);
                viewModel.getClient().setDistrict(districy);
                viewModel.getClient().setCity(city);
                viewModel.getClient().setState(state);

                addClient(viewModel.getClient());
                replaceConfirmEmailFragment();
            }

            bind.continuarBtn.setClickable(true);
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceFirstEnderecoRegisterFragment();
        });

    }

    private void replaceFirstEnderecoRegisterFragment() {
        requireActivity().onBackPressed();
    }

    private void replaceConfirmEmailFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new ConfirmEmailFragment()).addToBackStack(null).commit();
    }

    private void updateUi() {
        viewModel.getAddress().observe(requireActivity(), address -> {
            bind.logradouroEdt.setText(address);
        });
        viewModel.getDistrict().observe(requireActivity(), district -> {
            bind.districtEdt.setText(district);
        });
        viewModel.getCity().observe(requireActivity(), city -> {
            bind.cityEdt.setText(city);
        });
        viewModel.getState().observe(requireActivity(), state -> {
            bind.stateEdt.setText(state);
        });
    }

    private boolean addClient(Client client) {
        return myDB.addClient(client);
    }
}