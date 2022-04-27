package com.example.mybank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentFirstRegisterBinding;
import com.example.mybank.ui.activitys.LoginActivity;

public class FirstRegisterFragment extends Fragment {

    private FragmentFirstRegisterBinding bind;
    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentFirstRegisterBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(RegisterViewModel.class);
        updateUi();

        bind.continueBtn.setOnClickListener(view1 -> {

            String name, cpf, date, email, phone;
            name = bind.nameEdt.getText().toString().trim();
            cpf = bind.dataEdt.getText().toString().trim();
            date = bind.dataEdt.getText().toString().trim();
            email = bind.emailEdt.getText().toString().trim();
            phone = bind.phoneEdt.getText().toString().trim();

            if (name.isEmpty() || cpf.isEmpty() || date.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                if (name.isEmpty())
                    setErrorEdt(bind.nameEdt);
                if (cpf.isEmpty())
                    setErrorEdt(bind.cpfEdt);
                if (date.isEmpty())
                    setErrorEdt(bind.dataEdt);
                if (email.isEmpty())
                    setErrorEdt(bind.emailEdt);
                if (phone.isEmpty())
                    setErrorEdt(bind.phoneEdt);
            } else {
                viewModel.setName(name);
                viewModel.setCpf(cpf);
                viewModel.setDate(date);
                viewModel.setEmail(email);
                viewModel.setPhone(phone);
                replaceSecondRegisterFragment();
            }

        });

        bind.backImg.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        bind.logarTxt.setOnClickListener(view2 -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
        });
    }

    private void replaceSecondRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new SecondRegisterFragment()).commit();
    }

    private void updateUi() {
        viewModel.getName().observe(requireActivity(), name -> {
            bind.nameEdt.setText(name);
        });

        viewModel.getCpf().observe(requireActivity(), cpf -> {
            bind.cpfEdt.setText(cpf);
        });

        viewModel.getDate().observe(requireActivity(), date -> {
            bind.dataEdt.setText(date);
        });

        viewModel.getEmail().observe(requireActivity(), email -> {
            bind.emailEdt.setText(email);
        });

        viewModel.getPhone().observe(requireActivity(), phone -> {
            bind.phoneEdt.setText(phone);
        });
    }

    private void setErrorEdt(EditText edt) {
        edt.setError("Campo obrigatório", ActivityCompat.getDrawable(requireContext(), R.drawable.ic_error));
    }
}