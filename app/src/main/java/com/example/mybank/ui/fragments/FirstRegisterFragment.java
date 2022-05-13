package com.example.mybank.ui.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentFirstRegisterBinding;
import com.example.mybank.ui.activity.LoginActivity;
import com.example.mybank.ui.utils.CpfCnpjUtils;
import com.example.mybank.ui.utils.StringUtils;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(RegisterViewModel.class);
        updateUi();

        bind.continueBtn.setOnClickListener(view1 -> {

            if (bind.cpfEdt.isDone()
                    && bind.dataEdt.isDone()
                    && bind.phoneEdt.isDone()
                    && (bind.nameEdt.getText().toString().length() > 10)
                    && !bind.emailEdt.getText().toString().isEmpty()) {

                String name, cpf, date, email, phone;
                name = bind.nameEdt.getText().toString().trim();
                cpf = bind.cpfEdt.getText().toString().trim();
                date = bind.dataEdt.getText().toString().trim();
                email = bind.emailEdt.getText().toString().trim();
                phone = bind.phoneEdt.getText().toString().trim();

                if(!CpfCnpjUtils.isValid(cpf)) {
                    EditTextError.setEdtError(bind.cpfEdt, "Cpf inválido", requireContext());
                    return;
                }
                if(!StringUtils.isValid(date)) {
                    EditTextError.setEdtError(bind.dataEdt, "Data inválida", requireContext());
                    return;
                }
                if(StringUtils.calculoIdade(date) < 18) {
                    EditTextError.setEdtError(bind.dataEdt, "Cadastro indisponível para menores de 18 anos", requireContext());
                    return;
                }
                if(!StringUtils.validateEmail(email)) {
                    EditTextError.setEdtError(bind.emailEdt, "Email inválido", requireContext());
                    return;
                }
                if(!StringUtils.validatePhone(phone)) {
                    EditTextError.setEdtError(bind.phoneEdt, "Número de celular inválido", requireContext());
                    return;
                }

                viewModel.setNome(name);
                viewModel.setCpf(cpf);
                viewModel.setDate(date);
                viewModel.setEmail(email);
                viewModel.setCelular(phone);
                replaceSecondRegisterFragment();

            } else {
                if (bind.nameEdt.getText().toString().isEmpty())
                    EditTextError.setEdtError(bind.nameEdt, "Campo obrigatório", requireContext());
                else if (bind.nameEdt.getText().toString().length() < 10)
                    EditTextError.setEdtError(bind.nameEdt, "Nome inválido", requireContext());
                else if (!bind.cpfEdt.isDone())
                    EditTextError.setEdtError(bind.cpfEdt, "Campo obrigatório", requireContext());
                else if (!bind.dataEdt.isDone())
                    EditTextError.setEdtError(bind.dataEdt, "Campo obrigatório", requireContext());
                else if (bind.emailEdt.getText().toString().isEmpty())
                    EditTextError.setEdtError(bind.emailEdt, "Campo obrigatório", requireContext());
                else
                    EditTextError.setEdtError(bind.phoneEdt, "Campo obrigatório", requireContext());

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
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new SecondRegisterFragment()).addToBackStack(null).commit();
    }

    private void updateUi() {
        viewModel.nomeObserver().observe(requireActivity(), name -> {
            bind.nameEdt.setText(name);
        });

        viewModel.cpfObserver().observe(requireActivity(), cpf -> {
            bind.cpfEdt.setText(cpf);
        });

        viewModel.dateObserver().observe(requireActivity(), date -> {
            bind.dataEdt.setText(date);
        });

        viewModel.emailObserver().observe(requireActivity(), email -> {
            bind.emailEdt.setText(email);
        });

        viewModel.celularObserver().observe(requireActivity(), phone -> {
            bind.phoneEdt.setText(phone);
        });
    }
}