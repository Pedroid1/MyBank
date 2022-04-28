package com.example.mybank.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.mybank.R;
import com.example.mybank.database.MyDatabaseHelper;
import com.example.mybank.databinding.ActivityProfileBinding;
import com.example.mybank.model.Client;

public class ProfileActivity extends AppCompatActivity {

    public static final String EMAIL_KEY = "EMAIL";
    public static final String SENHA_KEY = "SENHA";
    private MyDatabaseHelper myDB;

    private ActivityProfileBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        myDB = new MyDatabaseHelper(this);

        Intent getIntent = getIntent();
        if(getIntent != null) {
            String email = getIntent.getStringExtra(EMAIL_KEY);
            String senha = getIntent.getStringExtra(SENHA_KEY);

            if(!email.isEmpty() && !senha.isEmpty()) {
                Client currentClient = findClient(email, senha);
                updateUi(currentClient);
            }
        }
    }

    private void updateUi(Client currentClient) {
        
    }

    private Client findClient(String email, String senha) {
        Cursor cursor = myDB.findClientByEmailAndPassword(email, senha);
        Client currentClient = new Client();

        if(cursor != null) {
            while(cursor.moveToNext()) {
                Integer id;
                String name, cpf, date, phone;
                //Endereço
                String cep, state, city, district, address, number;

                id = cursor.getInt(0);
                name = cursor.getString(1);
                cpf = cursor.getString(2);
                date = cursor.getString(3);
                phone = cursor.getString(5);
                cep = cursor.getString(7);
                state = cursor.getString(8);
                city = cursor.getString(9);
                district = cursor.getString(10);
                address = cursor.getString(11);
                number = cursor.getString(12);

                currentClient.setId(id);
                currentClient.setName(name);
                currentClient.setCpf(cpf);
                currentClient.setDate(date);
                currentClient.setPhone(phone);
                currentClient.setCep(cep);
                currentClient.setState(state);
                currentClient.setCity(city);
                currentClient.setDistrict(district);
                currentClient.setAddress(address);
                currentClient.setNumber(number);
                currentClient.setSenha(senha);
                currentClient.setEmail(email);
            }
        }
        return currentClient;
    }
}