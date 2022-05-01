package com.example.mybank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentProfileBinding;
import com.example.mybank.model.Client;
import com.example.mybank.ui.activitys.HomeActivity;
import com.example.mybank.ui.activitys.MainActivity;


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
        if(viewModel.getMyDB() == null)
            viewModel.setMyDB(requireActivity());
        updateUi();

        if(viewModel.getCurrentClient() == null) {
            Bundle args = getArguments();
            if(args != null) {
                String email = args.getString(HomeActivity.EMAIL_KEY);
                String senha = args.getString(HomeActivity.SENHA_KEY);

                Client currentClient = viewModel.getMyDB().findClientByEmailAndPassword(email, senha);
                if(currentClient != null) {
                    viewModel.setCurrentClient(currentClient);
                    Log.d("Teste", String.valueOf(viewModel.getCurrentClient().getId()));
                }
            }
        }

        bind.informationsCard.setOnClickListener(view1 -> {
            replaceInformationsFragment();
        });


        bind.sairBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
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