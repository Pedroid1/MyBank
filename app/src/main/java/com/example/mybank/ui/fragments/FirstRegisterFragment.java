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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybank.R;
import com.example.mybank.database.MyDatabaseHelper;
import com.example.mybank.databinding.FragmentFirstRegisterBinding;
import com.example.mybank.ui.activity.LoginActivity;
import com.example.mybank.ui.utils.CpfCnpjUtils;
import com.example.mybank.ui.utils.StringUtils;
import com.example.mybank.ui.utils.EditTextError;

public class FirstRegisterFragment extends Fragment {

    private FragmentFirstRegisterBinding bind;
    private RegisterViewModel viewModel;
    private MyDatabaseHelper myDB;

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

        myDB = new MyDatabaseHelper(requireActivity());

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
                    EditTextError.setEdtError(bind.cpfEdt, "Cpf inv??lido", requireContext());
                    return;
                }
                if(myDB.checkCpfIsLogged(cpf)) {
                    EditTextError.setEdtError(bind.cpfEdt, "Cpf j?? cadastrado no sistema", requireContext());
                    return;
                }
                if(!StringUtils.isValid(date)) {
                    EditTextError.setEdtError(bind.dataEdt, "Data inv??lida", requireContext());
                    return;
                }
                if(StringUtils.calculoIdade(date) < 18) {
                    EditTextError.setEdtError(bind.dataEdt, "Cadastro indispon??vel para menores de 18 anos", requireContext());
                    return;
                }
                if(!StringUtils.validateEmail(email)) {
                    EditTextError.setEdtError(bind.emailEdt, "Email inv??lido", requireContext());
                    return;
                }
                if(myDB.checkEmailIsLogged(email)) {
                    EditTextError.setEdtError(bind.emailEdt, "Esse email pertence a outra conta!", requireContext());
                    return;
                }
                if(!StringUtils.validatePhone(phone)) {
                    EditTextError.setEdtError(bind.phoneEdt, "N??mero de celular inv??lido", requireContext());
                    return;
                }

                viewModel.setName(name);
                viewModel.setCpf(cpf);
                viewModel.setDate(date);
                viewModel.setEmail(email);
                viewModel.setPhone(phone);
                replaceSecondRegisterFragment();

            } else {
                if (bind.nameEdt.getText().toString().isEmpty())
                    EditTextError.setEdtError(bind.nameEdt, "Campo obrigat??rio", requireContext());
                else if (bind.nameEdt.getText().toString().length() < 10)
                    EditTextError.setEdtError(bind.nameEdt, "Nome inv??lido", requireContext());
                else if (!bind.cpfEdt.isDone())
                    EditTextError.setEdtError(bind.cpfEdt, "Campo obrigat??rio", requireContext());
                else if (!bind.dataEdt.isDone())
                    EditTextError.setEdtError(bind.dataEdt, "Campo obrigat??rio", requireContext());
                else if (bind.emailEdt.getText().toString().isEmpty())
                    EditTextError.setEdtError(bind.emailEdt, "Campo obrigat??rio", requireContext());
                else
                    EditTextError.setEdtError(bind.phoneEdt, "Campo obrigat??rio", requireContext());

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
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new SecondRegisterFragment()).addToBackStack(null).commit();
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