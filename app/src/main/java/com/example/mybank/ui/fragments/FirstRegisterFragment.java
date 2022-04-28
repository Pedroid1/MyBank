package com.example.mybank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentFirstRegisterBinding;
import com.example.mybank.ui.activitys.LoginActivity;
import com.example.mybank.ui.utils.EditTextError;

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
            cpf = bind.cpfEdt.getText().toString().trim();
            date = bind.dataEdt.getText().toString().trim();
            email = bind.emailEdt.getText().toString().trim();
            phone = bind.phoneEdt.getText().toString().trim();

            if (name.isEmpty() || cpf.isEmpty() || date.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                if (name.isEmpty())
                    EditTextError.setEdtError(bind.nameEdt, "Campo obrigatório", requireContext());
                if (cpf.isEmpty())
                    EditTextError.setEdtError(bind.cpfEdt, "Campo obrigatório", requireContext());
                if (date.isEmpty())
                    EditTextError.setEdtError(bind.dataEdt, "Campo obrigatório", requireContext());
                if (email.isEmpty())
                    EditTextError.setEdtError(bind.emailEdt, "Campo obrigatório", requireContext());
                if (phone.isEmpty())
                    EditTextError.setEdtError(bind.phoneEdt, "Campo obrigatório", requireContext());
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
}