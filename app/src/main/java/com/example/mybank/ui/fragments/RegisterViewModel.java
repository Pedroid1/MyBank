package com.example.mybank.ui.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mybank.model.Cliente;

public class RegisterViewModel extends ViewModel {

    private Cliente client = new Cliente();

    private MutableLiveData<String> nome = new MutableLiveData();
    private MutableLiveData<String> cpf = new MutableLiveData();
    private MutableLiveData<String> date = new MutableLiveData();
    private MutableLiveData<String> email = new MutableLiveData();
    private MutableLiveData<String> celular = new MutableLiveData();
    private MutableLiveData<String> senha = new MutableLiveData<>();
    private MutableLiveData<String> renda = new MutableLiveData();
    private MutableLiveData<String> patrimonio = new MutableLiveData();

    private boolean sendEmail = false;
    private Integer codeSent;

    public Cliente getClient() {
        return client;
    }

    public void setClient(Cliente client) {
        this.client = client;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Integer getCodeSent() {
        return codeSent;
    }

    public void setCodeSent(Integer codeSent) {
        this.codeSent = codeSent;
    }

    public void setNome(String nome) {
        this.client.setNome(nome);
        this.nome.setValue(nome);
    }

    public void setCpf(String cpf) {
        this.client.setCpf(cpf);
        this.cpf.setValue(cpf);
    }

    public void setDate(String date) {
        //Converto to date
        //this.client.setDataNascimento(date);
        this.date.setValue(date);
    }

    public void setEmail(String email) {
        this.client.setEmail(email);
        this.email.setValue(email);
    }

    public void setCelular(String celular) {
        this.client.setCelular(celular);
        this.celular.setValue(celular);
    }

    public void setSenha(String senha) {
        Integer intSenha = Integer.parseInt(senha);
        this.client.setSenha(intSenha);
        this.senha.setValue(senha);
    }

    public void setRenda(Double renda) {
        this.client.setRenda(renda);
        this.renda.setValue(String.valueOf(renda));
    }

    public void setPatrimonio(Double patrimonio) {
        this.client.setPatrimonio(patrimonio);
        this.patrimonio.setValue(String.valueOf(patrimonio));
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

    public LiveData<String> senhaObserver() {
        return senha;
    }

    public LiveData<String> rendaObserver() {
        return renda;
    }

    public LiveData<String> patrimonioObserver() {
        return patrimonio;
    }


}
