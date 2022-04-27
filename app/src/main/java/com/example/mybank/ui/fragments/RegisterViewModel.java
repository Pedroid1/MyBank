package com.example.mybank.ui.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mybank.model.Client;

public class RegisterViewModel extends ViewModel {

    private Client client = new Client();

    private MutableLiveData<String> name = new MutableLiveData();
    private MutableLiveData<String> cpf = new MutableLiveData();
    private MutableLiveData<String> date = new MutableLiveData();
    private MutableLiveData<String> email = new MutableLiveData();
    private MutableLiveData<String> phone = new MutableLiveData();

    public Client getClient() {
        return client;
    }

    public void setName(String name) {
        this.name.setValue(name);
        client.setName(name);
    }

    public void setCpf(String cpf) {
        this.cpf.setValue(cpf);
        client.setCpf(cpf);
    }

    public void setDate(String date) {
        this.date.setValue(date);
        client.setDate(date);
    }

    public void setEmail(String email) {
        this.email.setValue(email);
        client.setEmail(email);
    }

    public void setPhone(String phone) {
        this.phone.setValue(phone);
        client.setPhone(phone);
    }

    public void setSenha(Integer senha) {
        client.setSenha(senha);
    }

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getCpf() {
        return cpf;
    }

    public LiveData<String> getDate() {
        return date;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPhone() {
        return phone;
    }

}
