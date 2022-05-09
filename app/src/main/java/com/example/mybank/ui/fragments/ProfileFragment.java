package com.example.mybank.ui.fragments;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.example.mybank.databinding.FragmentProfileBinding;
import com.example.mybank.model.Client;
import com.example.mybank.ui.activity.HomeActivity;
import com.example.mybank.ui.activity.MainActivity;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding bind;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentProfileBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        updateUi();

        bind.informationsCard.setOnClickListener(view1 -> {
            replaceInformationsFragment();
        });


        bind.sairBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
        });

        bind.deleteConta.setOnClickListener(view1 -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
            dialog.setTitle("Deletar conta");
            dialog.setMessage("Tem certeza que deseja deletar sua conta?");
            dialog.setPositiveButton("Sim", (dialogInterface, i) -> {
                viewModel.getMyDB().deleteClientById(viewModel.getCurrentClient().getId());
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                requireActivity().startActivity(intent);
                requireActivity().finish();
            });
            dialog.setNegativeButton("Não", (dialogInterface, i) -> {
            });
            dialog.show();
        });
    }

    private void replaceInformationsFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new InformationsFragment()).commit();
    }

    private void updateUi() {
        viewModel.getName().observe(requireActivity(), name -> {
            bind.nameTxt.setText(name);
        });
        viewModel.getEmail().observe(requireActivity(), email -> {
            bind.emailTxt.setText(email);
        });
    }
}