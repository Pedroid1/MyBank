package com.example.mybank.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentEditInformationsBinding;
import com.example.mybank.ui.LoadingDialog;
import com.example.mybank.ui.utils.CpfCnpjUtils;
import com.example.mybank.ui.utils.EditTextError;
import com.example.mybank.ui.utils.StringUtils;
import com.santalu.maskara.Mask;
import com.santalu.maskara.MaskChangedListener;
import com.santalu.maskara.MaskStyle;


public class EditInformationsFragment extends Fragment {

    static final String KEY_EDIT = "KEY";
    static final String NAME_EDIT = "NAME";
    static final String CPF_EDIT = "CPF";
    static final String DATE_EDIT = "DATA";
    static final String EMAIL_EDIT = "EMAIL";
    static final String PHONE_EDIT = "PHONE";
    static final String PATRIMONIO_EDIT = "PATRIMONIO";
    static final String RENDA_EDIT = "RENDA";

    private HomeViewModel viewModel;
    private FragmentEditInformationsBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentEditInformationsBinding.inflate(inflater, container, false);
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
            String edition;
            if (bind.editarInformationEdt.getVisibility() == View.VISIBLE)
                edition = bind.editarInformationEdt.getText().toString().trim();
            else
                edition = bind.editarInformationEdtMask.getText().toString().trim();

            if (!edition.isEmpty()) {

                if (viewModel.getOptionEdit().equals(NAME_EDIT)) {

                    if (edition.length() < 10)
                        EditTextError.setEdtError(bind.editarInformationEdt, "Nome inválido", requireContext());
                    else {
                        viewModel.getCurrentClient().setNome(edition);
                        updateClient();
                    }

                } else if (viewModel.getOptionEdit().equals(CPF_EDIT)) {

                    if (bind.editarInformationEdtMask.isDone()) {
                        if (!CpfCnpjUtils.isValid(edition))
                            EditTextError.setEdtError(bind.editarInformationEdtMask, "CPF inválido", requireContext());
                        else {
                            // Fazer verificação para saber se o CPF já está cadastrado em outra conta
                            viewModel.getCurrentClient().setCpf(edition);
                            updateClient();
                        }

                    } else
                        EditTextError.setEdtError(bind.editarInformationEdtMask, "Complete o campo", requireContext());

                } else if (viewModel.getOptionEdit().equals(DATE_EDIT)) {

                    if (bind.editarInformationEdtMask.isDone()) {
                        viewModel.getCurrentClient().setDataNascimento(edition);
                        updateClient();
                    } else
                        EditTextError.setEdtError(bind.editarInformationEdtMask, "Complete o campo", requireContext());

                } else if (viewModel.getOptionEdit().equals(EMAIL_EDIT)) {

                    if (!StringUtils.validateEmail(edition))
                        EditTextError.setEdtError(bind.editarInformationEdt, "Email inválido", requireContext());
                    else {
                        //Verificar se o EMAIl já está cadastrado em outra conta
                        viewModel.getCurrentClient().setEmail(edition);
                        updateClient();
                    }

                } else if (viewModel.getOptionEdit().equals(RENDA_EDIT)) {

                    viewModel.getCurrentClient().setRenda(Double.parseDouble(edition));
                    updateClient();

                } else if (viewModel.getOptionEdit().equals(PATRIMONIO_EDIT)) {

                    viewModel.getCurrentClient().setPatrimonio(Double.parseDouble(edition));
                    updateClient();

                } else {

                    if (bind.editarInformationEdtMask.isDone()) {
                        viewModel.getCurrentClient().setCelular(edition);
                        updateClient();
                    } else
                        EditTextError.setEdtError(bind.editarInformationEdtMask, "Preencha o campo", requireContext());
                }
            } else {
                if (bind.editarInformationEdt.getVisibility() == View.VISIBLE)
                    EditTextError.setEdtError(bind.editarInformationEdt, "Campo obrigatório", requireContext());
                else
                    EditTextError.setEdtError(bind.editarInformationEdtMask, "Campo obrigatório", requireContext());
            }

        });

        bind.backImg.setOnClickListener(view1 -> {
            viewModel.setOptionEdit(null);
            backFragment();
        });

    }

    private void updateUi() {
        if (viewModel.getOptionEdit().equals(NAME_EDIT) || viewModel.getOptionEdit().equals(EMAIL_EDIT) || viewModel.getOptionEdit().equals(RENDA_EDIT) || viewModel.getOptionEdit().equals(PATRIMONIO_EDIT)) {
            bind.editarInformationEdt.setVisibility(View.VISIBLE);
        } else {
            bind.editarInformationEdtMask.setVisibility(View.VISIBLE);
        }

        if (viewModel.getOptionEdit().equals(NAME_EDIT)) {
            bind.editarTxt.setText("Editar nome");
            bind.editarInformationEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            bind.editarInformationEdt.setText(viewModel.getCurrentClient().getNome());

        } else if (viewModel.getOptionEdit().equals(CPF_EDIT)) {

            bind.editarTxt.setText("Editar CPF");
            Mask mask = new Mask(
                    "___.___.___-__",
                    '_',
                    MaskStyle.COMPLETABLE
            );
            bind.editarInformationEdtMask.addTextChangedListener(new MaskChangedListener(mask));
            bind.editarInformationEdtMask.setText(viewModel.getCurrentClient().getCpf());

        } else if (viewModel.getOptionEdit().equals(DATE_EDIT)) {

            bind.editarTxt.setText("Editar data de nascimento");
            Mask mask = new Mask(
                    "__/__/____",
                    '_',
                    MaskStyle.COMPLETABLE
            );
            bind.editarInformationEdtMask.addTextChangedListener(new MaskChangedListener(mask));
            bind.editarInformationEdtMask.setText(viewModel.getCurrentClient().getDataNascimento());

        } else if (viewModel.getOptionEdit().equals(EMAIL_EDIT)) {

            bind.editarTxt.setText("Editar email");
            bind.editarInformationEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            bind.editarInformationEdt.setText(viewModel.getCurrentClient().getEmail());

        } else if (viewModel.getOptionEdit().equals(RENDA_EDIT)) {

            bind.editarTxt.setText("Editar renda mensal");
            bind.editarInformationEdt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            bind.editarInformationEdt.setText(String.valueOf(viewModel.getCurrentClient().getRenda()));

        } else if (viewModel.getOptionEdit().equals(PATRIMONIO_EDIT)) {

            bind.editarTxt.setText("Editar patrimônio líquido");
            bind.editarInformationEdt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            bind.editarInformationEdt.setText(String.valueOf(viewModel.getCurrentClient().getPatrimonio()));

        } else {

            bind.editarTxt.setText("Editar celular");
            Mask mask = new Mask(
                    "(__) _____-____",
                    '_',
                    MaskStyle.COMPLETABLE
            );
            bind.editarInformationEdtMask.addTextChangedListener(new MaskChangedListener(mask));
            bind.editarInformationEdtMask.setText(viewModel.getCurrentClient().getCelular());

        }
    }

    private void backFragment() {
        requireActivity().onBackPressed();
    }

    private void updateClient() {
        //Fazer update aqui
        //viewModel.getMyDB().updateClient(viewModel.getCurrentClient());
        viewModel.setCurrentClient(viewModel.getCurrentClient());
        viewModel.setOptionEdit(null);

        LoadingDialog loadingDialog = new LoadingDialog(requireActivity());
        loadingDialog.startLoadDialog();

        new Handler().postDelayed(() -> {
            loadingDialog.dismissDialog();
            backFragment();

        }, 2000);
    }


}