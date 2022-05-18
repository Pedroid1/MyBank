package com.example.mybank.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybank.R;
import com.example.mybank.database.MyDatabaseHelper;
import com.example.mybank.databinding.FragmentSenhaBinding;
import com.example.mybank.ui.ProgressButton;
import com.example.mybank.ui.utils.EditTextError;

public class SenhaFragment extends Fragment {

    private FragmentSenhaBinding bind = null;
    private HomeViewModel viewModel = null;
    private View finishBtn;
    private MyDatabaseHelper myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentSenhaBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        myDB = new MyDatabaseHelper(requireActivity());

        bind.senhaEdt.requestFocus();

        finishBtn = view.findViewById(R.id.finish_btn);
        ProgressButton progressButton = new ProgressButton(requireContext(),
                "Transferir",
                "Senha incorreta",
                "Transferência realizada",
                finishBtn);

        finishBtn.setOnClickListener(view1 -> {
            finishBtn.setClickable(false);

            String senha = bind.senhaEdt.getText().toString().trim();
            if(senha.isEmpty()) {
                EditTextError.setEdtError(bind.senhaEdt, "Campo obrigatório", requireContext());
                return;
            }
            if(senha.length() < 4) {
                EditTextError.setEdtError(bind.senhaEdt, "Senha incompleta", requireContext());
                return;
            }

            progressButton.buttonActivated();

            new Handler().postDelayed(() -> {

                if(senha.equals(viewModel.getCurrentClient().getSenha())) {
                    Double valorPix = viewModel.getValorPix();
                    viewModel.getCurrentClient().setSaldo(viewModel.getCurrentClient().getSaldo() - valorPix);

                    myDB.updateSaldo(viewModel.getCurrentClient().getId(), viewModel.getCurrentClient().getSaldo());
                    myDB.updateSaldo(viewModel.getRecebedorPix().getId(), viewModel.getRecebedorPix().getSaldo() + valorPix);
                    viewModel.setCurrentClient(viewModel.getCurrentClient());

                    progressButton.buttonFinishedSuccess();
                    new Handler().postDelayed(this::backFragment, 2000);
                } else {
                    progressButton.buttonFinishedFail();
                    new Handler().postDelayed(() -> {
                        progressButton.resetButton();
                        finishBtn.setClickable(true);
                    }, 2000);
                }

            }, 3000);
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceMakePixFragment();
        });
    }

    private void replaceMakePixFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new MakePixFragment()).commit();
    }

    private void backFragment() {
        requireActivity().onBackPressed();
    }
}