package com.example.mybank.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import com.example.mybank.R;
import com.example.mybank.database.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn, registerBtn;

    //https://www.behance.net/gallery/105862653/Mobile-Banking-App?tracking_source=search_projects%7Cbank%20app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        MyDatabaseHelper myDB = new MyDatabaseHelper(this);
        Cursor cursor = myDB.findClientById(1);
        if(cursor != null) {
            if(cursor.moveToNext()) {
                Integer integer = cursor.getInt(0);
                String name = cursor.getString(1);
                String cpf = cursor.getString(2);
                Integer senha = cursor.getInt(6);
                System.out.println();
            }
        }

         */

        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);

        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

}