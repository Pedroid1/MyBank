package com.example.mybank.ui.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mybank.R;
import com.example.mybank.databinding.FragmentConfirmEmailBinding;
import com.example.mybank.ui.ProgressButton;
import com.example.mybank.ui.activity.HomeActivity;
import com.example.mybank.ui.utils.EditTextError;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ConfirmEmailFragment extends Fragment {

    private FragmentConfirmEmailBinding bind = null;
    private View finishBtn;
    private RegisterViewModel viewModel = null;

    private Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentConfirmEmailBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        finishBtn = view.findViewById(R.id.finish_btn);
        ProgressButton progressButton = new ProgressButton(requireContext(),
                "Confirmar e-mail",
                "Código inválido",
                "E-mail confirmado",
                finishBtn);

        sendEmail();

        finishBtn.setOnClickListener(view1 -> {
            finishBtn.setClickable(false);
            String codigo = bind.codigoEdt.getText().toString().trim();

            if (!codigo.isEmpty()) {

                progressButton.buttonActivated();

                new Handler().postDelayed(() -> {

                    if (codigo.equals(String.valueOf(viewModel.getCodeSent()))) {
                        progressButton.buttonFinishedSuccess();
                        new Handler().postDelayed(() -> {

                            popBackStack();

                            Intent intent = new Intent(requireActivity(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(HomeActivity.EMAIL_KEY, viewModel.getClient().getEmail());
                            intent.putExtra(HomeActivity.SENHA_KEY, viewModel.getClient().getSenha());
                            requireActivity().startActivity(intent);

                            requireActivity().finish();

                        }, 2000);
                    } else {
                        progressButton.buttonFinishedFail();

                        new Handler().postDelayed(progressButton::resetButton, 2000);
                    }
                }, 3000);

            } else {
                EditTextError.setEdtError(bind.codigoEdt, "Preencha o campo", requireContext());
            }

            finishBtn.setClickable(true);
        });

        bind.backImg.setOnClickListener(view1 -> {
            replaceSecondEnderecoRegisterFragment();
        });

    }

    private void popBackStack() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void sendEmail() {
        if (!viewModel.isSendEmail()) {
            viewModel.setCodeSent(generateEmailCode());

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("javabanksa@gmail.com", "javabank123!");
                        }
                    });

            RetreiveFeedTask task = new RetreiveFeedTask();
            task.execute();
        }
    }

    private void replaceSecondEnderecoRegisterFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, new SecondEnderecoRegisterFragment()).commit();
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            finishBtn.setVisibility(View.INVISIBLE);

            try {
                String messageSent = "Para validar seu e-mail, digite este código no aplicativo: " + viewModel.getCodeSent() + ".";

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("javabanksa@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(viewModel.getClient().getEmail()));
                message.setSubject("Código de validação de e-mail");
                message.setContent(messageSent, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            finishBtn.setVisibility(View.VISIBLE);
        }
    }

    private Integer generateEmailCode() {
        int min = 1000;
        int max = 10000;
        Random aleatorio = new Random();
        Integer randomGenerated = aleatorio.nextInt((max - min) + 1);
        randomGenerated += min;
        return randomGenerated;
    }
}