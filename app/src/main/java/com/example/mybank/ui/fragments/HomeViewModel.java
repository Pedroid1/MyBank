package com.example.mybank.ui.fragments;


import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mybank.database.MyDatabaseHelper;
import com.example.mybank.model.Client;

public class HomeViewModel extends ViewModel {

    private MyDatabaseHelper myDB;

    private Client currentClient = null;

    private String optionEdit = null;

    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> cpf = new MutableLiveData<>();
    private MutableLiveData<String> data = new MutableLiveData<>();
    private MutableLiveData<String> phone = new MutableLiveData<>();
    private MutableLiveData<String> city = new MutableLiveData<>();
    private MutableLiveData<String> state = new MutableLiveData<>();

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getCpf() {
        return cpf;
    }

    public LiveData<String> getData() {
        return data;
    }

    public LiveData<String> getPhone() {
        return phone;
    }

    public LiveData<String> getCity() {
        return city;
    }

    public LiveData<String> getState() {
        return state;
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;

        name.setValue(currentClient.getName());
        email.setValue(currentClient.getEmail());
        cpf.setValue(currentClient.getCpf());
        data.setValue(currentClient.getDate());
        phone.setValue(currentClient.getPhone());
        city.setValue(currentClient.getCity());
        state.setValue(currentClient.getState());
    }

    public MyDatabaseHelper getMyDB() {
        return myDB;
    }

    public void setMyDB(Activity activity) {
        this.myDB = new MyDatabaseHelper(activity);
    }

    public String getOptionEdit() {
        return optionEdit;
    }

    public void setOptionEdit(String optionEdit) {
        this.optionEdit = optionEdit;
    }
}
