package com.example.mybank.ui.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mybank.model.Cliente;

public class HomeViewModel extends ViewModel {

    private Cliente currentClient = null;
    private String optionEdit = null;

    private MutableLiveData<String> nome = new MutableLiveData();
    private MutableLiveData<String> cpf = new MutableLiveData();
    private MutableLiveData<String> date = new MutableLiveData();
    private MutableLiveData<String> email = new MutableLiveData();
    private MutableLiveData<String> celular = new MutableLiveData();
    private MutableLiveData<String> renda = new MutableLiveData();
    private MutableLiveData<String> patrimonio = new MutableLiveData();

    public Cliente getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Cliente currentClient) {
        this.currentClient = currentClient;

        nome.setValue(currentClient.getNome());
        email.setValue(currentClient.getEmail());
        cpf.setValue(currentClient.getCpf());
        date.setValue(currentClient.getDataNascimento());
        celular.setValue(currentClient.getCelular());
        patrimonio.setValue(String.valueOf(currentClient.getPatrimonio()));
        renda.setValue(String.valueOf(currentClient.getRenda()));
    }

    public String getOptionEdit() {
        return optionEdit;
    }

    public void setOptionEdit(String optionEdit) {
        this.optionEdit = optionEdit;
    }

    public LiveData<String> nomeObserver() {
        return nome;
    }

    public LiveData<String> cpfObserver() {
        return cpf;
    }

    public LiveData<String> dateObserver() {
        return date;
    }

    public LiveData<String> emailObserver() {
        return email;
    }

    public LiveData<String> celularObserver() {
        return celular;
    }

    public LiveData<String> rendaObserver() {
        return renda;
    }

    public LiveData<String> patrimonioObserver() {
        return patrimonio;
    }

}
