package com.example.mybank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mybank.R;
import com.example.mybank.database.MyDatabaseHelper;
import com.example.mybank.databinding.FragmentSecondRegisterBinding;
import com.example.mybank.model.Client;
import com.example.mybank.ui.LoadingDialog;
import com.example.mybank.ui.ProgressButton;
import com.example.mybank.ui.activitys.HomeActivity;

public class SecondRegisterFragment extends Fragment {

    private FragmentSecondRegisterBinding bind;
    private RegisterViewModel viewModel;
    private View finishBtn;
    MyDatabaseHelper myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentSecondRegisterBinding.inflate(inflater, container, false);
        return bind.getRoot();

        /*
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

    Intent intent = new Intent(Your_Current_Activity.this, Your_Destination_Activity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
     */
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        myDB = new MyDatabaseHelper(requireActivity());

        finishBtn = view.findViewById(R.id.finish_btn);
        ProgressButton progressButton = new ProgressButton(requireContext(),
                "Finalizar",
                "Falha ao cadastrar",
                "Pronto",
                finishBtn);

        finishBtn.setOnClickListener(view1 -> {
            String sSenha1, sSenha2;
            sSenha1 = bind.senha1Edt.getText().toString().trim();
            sSenha2 = bind.senha2Edt.getText().toString().trim();

            if (sSenha1.isEmpty() || sSenha2.isEmpty() || !bind.termsCheckbox.isChecked()) {
                if (sSenha1.isEmpty())
                    setErrorEdt(bind.senha1Edt, "Campo obrigatório");
                if (sSenha2.isEmpty())
                    setErrorEdt(bind.senha2Edt, "Campo obrigatório");
                if (!bind.termsCheckbox.isChecked())
                    bind.termsCheckbox.setError("Para continuar, aceite os termos!", ActivityCompat.getDrawable(requireContext(), R.drawable.ic_error));
            } else {
                if (!sSenha1.equals(sSenha2)) {
                    bind.senha2Edt.setError("As senhas não correspondem!", ActivityCompat.getDrawable(requireContext(), R.drawable.ic_error));
                } else {

                    if(sSenha1.length() < 4) {
                        setErrorEdt(bind.senha1Edt, "A senha deve conter 4 números");
                        setErrorEdt(bind.senha2Edt, "A senha deve conter 4 números");
                    } else {
                        viewModel.setSenha(Integer.parseInt(sSenha1));
                        Client client = viewModel.getClient();

                        progressButton.buttonActivated();
                        finishBtn.setClickable(false);

                        new Handler().postDelayed(() -> {
                            if (registerClient(client)) {
                                progressButton.buttonFinishedSuccess();

                                new Handler().postDelayed(() -> {
                                    Intent intent = new Intent(requireActivity(), HomeActivity.class);
                                    intent.putExtra(HomeActivity.EMAIL_KEY, client.getEmail());
                                    intent.putExtra(HomeActivity.SENHA_KEY, client.getSenha());
                                    requireActivity().startActivity(intent);
                                    requireActivity().finish();
                                }, 2000);

                            } else {
                                progressButton.buttonFinishedFail();

                                new Handler().postDelayed(() -> {
                                    progressButton.resetButton();
                                }, 2000);
                            }
                        }, 3000);
                    }

                }
            }
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceFirstRegisterFragment();
        });
    }

    private boolean registerClient(Client client) {
        return myDB.addClient(client);
    }

    private void replaceFirstRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new FirstRegisterFragment()).commit();
    }

    private void setErrorEdt(EditText edt, String message) {
        edt.setError(message, ActivityCompat.getDrawable(requireContext(), R.drawable.ic_error));
    }
}