package com.example.mybank.ui;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.example.mybank.R;

public class ProgressButton {

    private Context context;
    private String initialMessage;
    private String failMessage;
    private String succesMessage;

    private ProgressBar progressBar;
    private TextView messageTxt;
    private ConstraintLayout constraintLayout;

    public ProgressButton(Context context, String initialMessage, String failMessage, String succesMessage, View view) {
        this.context = context;
        this.initialMessage = initialMessage;
        this.failMessage = failMessage;
        this.succesMessage = succesMessage;

        messageTxt = view.findViewById(R.id.message_txt);
        progressBar = view.findViewById(R.id.progress_bar);
        constraintLayout = view.findViewById(R.id.constraint);

        initializeButton();
    }

    private void initializeButton() {
        messageTxt.setText(initialMessage);
    }

    public void buttonActivated() {
        progressBar.setVisibility(View.VISIBLE);
        messageTxt.setText("Por favor, aguarde...");
    }

    public void buttonFinishedSuccess() {
        constraintLayout.setBackgroundColor(ActivityCompat.getColor(context, R.color.verde));
        progressBar.setVisibility(View.GONE);
        messageTxt.setText(succesMessage);
    }


    public void buttonFinishedFail() {
        constraintLayout.setBackgroundColor(ActivityCompat.getColor(context, R.color.verde));
        progressBar.setVisibility(View.GONE);
        messageTxt.setText(failMessage);
    }


    public void resetButton() {
        constraintLayout.setBackgroundColor(ActivityCompat.getColor(context, R.color.laranja));
        messageTxt.setText(initialMessage);
    }
}
