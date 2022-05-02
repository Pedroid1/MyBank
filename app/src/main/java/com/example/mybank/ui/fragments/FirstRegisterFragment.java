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

                if (StringUtils.validateEmail(email)) {
                    if (!myDB.checkEmail(email)) {
                        if (CpfCnpjUtils.isValid(cpf)) {
                            if (!myDB.checkCpf(cpf)) {
                                viewModel.setName(name);
                                viewModel.setCpf(cpf);
                                viewModel.setDate(date);
                                viewModel.setEmail(email);
                                viewModel.setPhone(phone);
                                replaceSecondRegisterFragment();
                            } else {
                                EditTextError.setEdtError(bind.cpfEdt, "Esse Cpf pertence a outra conta", requireContext());
                            }
                        } else
                            EditTextError.setEdtError(bind.cpfEdt, "Cpf inválido", requireContext());
                    } else
                        EditTextError.setEdtError(bind.emailEdt, "Esse email pertence a outra conta!", requireContext());
                } else
                    EditTextError.setEdtError(bind.emailEdt, "Email inválido", requireContext());


            } else {
                if (bind.nameEdt.getText().toString().isEmpty())
                    EditTextError.setEdtError(bind.nameEdt, "Campo obrigatório", requireContext());
                else if (bind.nameEdt.getText().toString().length() < 10)
                    EditTextError.setEdtError(bind.nameEdt, "Nome inválido", requireContext());
                else if (!bind.cpfEdt.isDone())
                    EditTextError.setEdtError(bind.cpfEdt, "Complete o campo", requireContext());
                else if (!bind.dataEdt.isDone())
                    EditTextError.setEdtError(bind.dataEdt, "Complete o campo", requireContext());
                else if (bind.emailEdt.getText().toString().isEmpty())
                    EditTextError.setEdtError(bind.emailEdt, "Campo obrigatório", requireContext());
                else
                    EditTextError.setEdtError(bind.phoneEdt, "Complete o campo", requireContext());

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