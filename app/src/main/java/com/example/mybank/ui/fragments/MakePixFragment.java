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
import com.example.mybank.database.MyDatabaseHelper;
import com.example.mybank.databinding.FragmentMakePixBinding;
import com.example.mybank.model.Client;
import com.example.mybank.ui.utils.EditTextError;

public class MakePixFragment extends Fragment {

    private FragmentMakePixBinding bind = null;
    private HomeViewModel viewModel = null;
    private MyDatabaseHelper myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentMakePixBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        myDB = new MyDatabaseHelper(requireActivity());

        observers();

        bind.continueBtn.setOnClickListener(view1 -> {
            if(bind.pixEdt.getText().toString().isEmpty()) {
                EditTextError.setEdtError(bind.pixEdt, "Campo obrigatório", requireContext());
                return;
            }
            if(bind.valorEdt.getText().toString().isEmpty()) {
                EditTextError.setEdtError(bind.valorEdt, "Campo obrigatório", requireContext());
                return;
            }

            Double valor = Double.parseDouble(bind.valorEdt.getText().toString().trim());
            String chavePix = bind.pixEdt.getText().toString().trim();

            if(valor > viewModel.getCurrentClient().getSaldo()){
                EditTextError.setEdtError(bind.valorEdt, "Valor indisponível", requireContext());
                return;
            }

            Client recebedor = myDB.findClientPorChavePix(chavePix);
            if(recebedor != null) {
                viewModel.setChavePix(chavePix);
                viewModel.setValor(String.valueOf(valor));
                viewModel.setRecebedorPix(recebedor);
                viewModel.setValorPix(valor);
                replaceSenhaFragment();
            } else {
                EditTextError.setEdtError(bind.pixEdt, "Chave pix não encontrada", requireContext());
            }

        });

        bind.backImg.setOnClickListener(view1 -> {
            viewModel.setValorPix(null);
            viewModel.setRecebedorPix(null);
            backFragment();
        });
    }

    private void observers() {
        viewModel.getValor().observe(requireActivity(), valor -> {
            bind.valorEdt.setText(valor);
        });
        viewModel.getChavePix().observe(requireActivity(), chavePix -> {
            bind.pixEdt.setText(chavePix);
        });
    }

    private void backFragment() {
        requireActivity().onBackPressed();
    }

    private void replaceSenhaFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new SenhaFragment()).commit();
    }
}