package com.example.mybank.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentEditProfileBinding;
import com.example.mybank.ui.utils.CpfCnpjUtils;
import com.example.mybank.ui.utils.EditTextError;
import com.example.mybank.ui.utils.StringUtils;
import com.santalu.maskara.Mask;
import com.santalu.maskara.MaskChangedListener;
import com.santalu.maskara.MaskStyle;

public class EditProfileFragment extends Fragment {

    static final String KEY_EDIT = "KEY";
    static final String NAME_EDIT = "NAME";
    static final String CPF_EDIT = "CPF";
    static final String DATE_EDIT = "DATA";
    static final String EMAIL_EDIT = "EMAIL";
    static final String PHONE_EDIT = "PHONE";

    private FragmentEditProfileBinding bind;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentEditProfileBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        if (viewModel.getOptionEdit() == null) {
            Bundle args = getArguments();
            viewModel.setOptionEdit(args.getString(KEY_EDIT));
        }

        updateUi();

        bind.editBtn.setOnClickListener(view1 -> {
            String edition = bind.editarEdt.getText().toString().trim();

            if (!edition.isEmpty()) {

                if (viewModel.getOptionEdit().equals(NAME_EDIT)) {

                    if (edition.length() < 10)
                        EditTextError.setEdtError(bind.editarEdt, "Nome inválido", requireContext());
                    else
                        viewModel.getCurrentClient().setName(edition);

                } else if (viewModel.getOptionEdit().equals(CPF_EDIT)) {

                    if (bind.editarEdt.isDone()) {
                        if (!CpfCnpjUtils.isValid(edition))
                            EditTextError.setEdtError(bind.editarEdt, "CPF inválido", requireContext());
                        else
                            viewModel.getCurrentClient().setCpf(edition);
                    } else
                        EditTextError.setEdtError(bind.editarEdt, "Complete o campo", requireContext());

                } else if (viewModel.getOptionEdit().equals(DATE_EDIT)) {

                    if(bind.editarEdt.isDone())
                        viewModel.getCurrentClient().setDate(edition);
                    else
                        EditTextError.setEdtError(bind.editarEdt, "Complete o campo", requireContext());

                } else if (viewModel.getOptionEdit().equals(EMAIL_EDIT)) {

                    if (!StringUtils.validateEmail(edition))
                        EditTextError.setEdtError(bind.editarEdt, "Email inválido", requireContext());
                    else
                        viewModel.getCurrentClient().setEmail(edition);

                } else {

                    if (bind.editarEdt.isDone())
                        viewModel.getCurrentClient().setPhone(edition);
                    else
                        EditTextError.setEdtError(bind.editarEdt, "Preencha o campo", requireContext());
                }
            } else {
                EditTextError.setEdtError(bind.editarEdt, "Campo obrigatório", requireContext());
            }

            viewModel.getMyDB().updateClient(viewModel.getCurrentClient());
        });

        bind.backImg.setOnClickListener(view1 -> {
            viewModel.setOptionEdit(null);
            replaceInformationsFragment();
        });
    }

    private void updateUi() {
        if (viewModel.getOptionEdit().equals(NAME_EDIT)) {
            bind.editarTxt.setText("Editar nome");
            bind.editarEdt.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        } else if (viewModel.getOptionEdit().equals(CPF_EDIT)) {

            bind.editarTxt.setText("Editar CPF");
            Mask mask = new Mask(
                    "___.___.___-__",
                    '_',
                    MaskStyle.COMPLETABLE
            );
            bind.editarEdt.addTextChangedListener(new MaskChangedListener(mask));
            bind.editarEdt.setText(viewModel.getCurrentClient().getCpf());

        } else if (viewModel.getOptionEdit().equals(DATE_EDIT)) {

            bind.editarTxt.setText("Editar data");
            Mask mask = new Mask(
                    "__/__/____",
                    '_',
                    MaskStyle.COMPLETABLE
            );
            bind.editarEdt.addTextChangedListener(new MaskChangedListener(mask));
            bind.editarEdt.setText(viewModel.getCurrentClient().getDate());

        } else if (viewModel.getOptionEdit().equals(EMAIL_EDIT)) {

            bind.editarTxt.setText("Editar email");
            bind.editarEdt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            bind.editarEdt.setText(viewModel.getCurrentClient().getEmail());

        } else {

            bind.editarTxt.setText("Editar celular");
            Mask mask = new Mask(
                    "(__) _____-____",
                    '_',
                    MaskStyle.COMPLETABLE
            );
            bind.editarEdt.addTextChangedListener(new MaskChangedListener(mask));
            bind.editarEdt.setText(viewModel.getCurrentClient().getPhone());

        }
    }

    private void replaceInformationsFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new InformationsFragment()).commit();
    }
}