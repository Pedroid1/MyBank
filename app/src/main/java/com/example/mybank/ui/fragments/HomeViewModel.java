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
    private Client recebedorPix = null;
    private Double valorPix = null;

    private String optionEdit = null;

    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> cpf = new MutableLiveData<>();
    private MutableLiveData<String> data = new MutableLiveData<>();
    private MutableLiveData<String> phone = new MutableLiveData<>();

    private MutableLiveData<String> city = new MutableLiveData<>();
    private MutableLiveData<String> state = new MutableLiveData<>();

    private MutableLiveData<String> renda = new MutableLiveData<>();
    private MutableLiveData<String> patrimonio = new MutableLiveData<>();

    private MutableLiveData<String> valor = new MutableLiveData<>();
    private MutableLiveData<String> chave = new MutableLiveData<>();

    public void setValor(String valor) {
        this.valor.setValue(valor);
    }

    public void setChavePix(String chave) {
        this.chave.setValue(chave);
    }

    public Double getValorPix() {
        return valorPix;
    }

    public void setValorPix(Double valorPix) {
        this.valorPix = valorPix;
    }

    public Client getRecebedorPix() {
        return recebedorPix;
    }

    public void setRecebedorPix(Client recebedorPix) {
        this.recebedorPix = recebedorPix;
    }

    public LiveData<String> getValor() {
        return valor;
    }

    public LiveData<String> getChavePix() {
        return chave;
    }

    public LiveData<String> getRenda() {
        return renda;
    }

    public LiveData<String> getPatrimonio() {
        return patrimonio;
    }

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

        patrimonio.setValue(String.valueOf(currentClient.getPatrimonioLiquido()));
        renda.setValue(String.valueOf(currentClient.getRendaMensal()));
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
