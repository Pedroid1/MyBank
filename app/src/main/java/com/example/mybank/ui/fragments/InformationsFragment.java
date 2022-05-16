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
import com.example.mybank.databinding.FragmentInformationsBinding;
import com.example.mybank.ui.utils.StringUtils;

public class InformationsFragment extends Fragment {

    private FragmentInformationsBinding bind;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentInformationsBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        bind.setViewModel(viewModel);

        updateUi();

        listeners();

    }

    private void updateUi() {
        bind.rendaMensalClientTxt.setText(StringUtils.getCurrencyInstance(viewModel.getCurrentClient().getRendaMensal()));
        bind.patrimonioClientTxt.setText(StringUtils.getCurrencyInstance(viewModel.getCurrentClient().getPatrimonioLiquido()));
    }

    private void listeners() {
        bind.backImg.setOnClickListener(view1 -> {
            replaceProfileFragment();
        });

        bind.nameEdit.setOnClickListener(view1 -> {
            replaceEditFragment(EditInformationsFragment.NAME_EDIT);
        });

        bind.cpfEdit.setOnClickListener(view1 -> {
            replaceEditFragment(EditInformationsFragment.CPF_EDIT);
        });

        bind.dataEdit.setOnClickListener(view1 -> {
            replaceEditFragment(EditInformationsFragment.DATE_EDIT);
        });

        bind.emailEdit.setOnClickListener(view1 -> {
            replaceEditFragment(EditInformationsFragment.EMAIL_EDIT);
        });

        bind.phoneEdit.setOnClickListener(view1 -> {
            replaceEditFragment(EditInformationsFragment.PHONE_EDIT);
        });

        bind.rendaEdit.setOnClickListener(view1 -> {
            replaceEditFragment(EditInformationsFragment.RENDA_EDIT);
        });

        bind.patrimonioEdit.setOnClickListener(view1 -> {
            replaceEditFragment(EditInformationsFragment.PATRIMONIO_EDIT);
        });
    }

    private void replaceProfileFragment() {
        requireActivity().onBackPressed();
    }

    private void replaceEditFragment(String edit) {
        Bundle args = new Bundle();
        args.putString(EditInformationsFragment.KEY_EDIT, edit);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, EditInformationsFragment.class, args).addToBackStack(null).commit();
    }
}