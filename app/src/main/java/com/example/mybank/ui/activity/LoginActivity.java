package com.example.mybank.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.mybank.R;
import com.example.mybank.databinding.ActivityLoginBinding;
import com.example.mybank.ui.ProgressButton;
import com.example.mybank.ui.utils.EditTextError;
import com.example.mybank.ui.utils.StringUtils;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding bind;
    private View loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
/*
        loginBtn = findViewById(R.id.login_btn);
        ProgressButton progressButton = new ProgressButton(this,
                "Entrar",
                "Falha ao logar",
                "Pronto",
                loginBtn);

        loginBtn.setOnClickListener(view -> {
            loginBtn.setClickable(false);

            String email, senha;
            email = bind.emailEdt.getText().toString().trim();
            senha = bind.senhaEdt.getText().toString().trim();

            if(email.isEmpty() || senha.isEmpty()) {
                if(email.isEmpty()) {
                    EditTextError.setEdtError(bind.emailEdt, "Campo obrigatório", this);
                } else {
                    EditTextError.setEdtError(bind.senhaEdt, "Campo obrigatório", this);
                }
            } else {
                if(StringUtils.validateEmail(email)) {
                    progressButton.buttonActivated();

                    new Handler().postDelayed(() -> {

                        if(myDB.checkEmail(email)) {

                            if(myDB.userLogin(email, senha)) {
                                progressButton.buttonFinishedSuccess();

                                new Handler().postDelayed(() -> {
                                    Intent intent = new Intent(this, HomeActivity.class);
                                    intent.putExtra(HomeActivity.EMAIL_KEY, email);
                                    intent.putExtra(HomeActivity.SENHA_KEY, senha);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }, 2000);
                            } else {
                                EditTextError.setEdtError(bind.senhaEdt, "Senha incorreta", this);
                                progressButton.buttonFinishedFail();
                                new Handler().postDelayed(() -> {
                                    progressButton.resetButton();
                                }, 2500);
                            }
                        } else {
                            EditTextError.setEdtError(bind.emailEdt, "Email não cadastrado", this);
                            progressButton.buttonFinishedFail();
                            new Handler().postDelayed(() -> {
                                progressButton.resetButton();
                            }, 2500);
                        }
                    }, 2000);

                } else {
                    EditTextError.setEdtError(bind.emailEdt, "Email inválido", this);
                }

            }
            loginBtn.setClickable(true);
        });

        bind.backImg.setOnClickListener(view -> {
            finish();
        });

 */
    }
}