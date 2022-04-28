package com.example.mybank.ui.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mybank.model.Client;
import com.example.mybank.model.Endereco;

public class RegisterViewModel extends ViewModel {

    private Client client = new Client();
    private Endereco endereco = new Endereco();

    private MutableLiveData<String> name = new MutableLiveData();
    private MutableLiveData<String> cpf = new MutableLiveData();
    private MutableLiveData<String> date = new MutableLiveData();
    private MutableLiveData<String> email = new MutableLiveData();
    private MutableLiveData<String> phone = new MutableLiveData();

    private MutableLiveData<String> cep = new MutableLiveData();
    private MutableLiveData<String> address = new MutableLiveData();
    private MutableLiveData<String> state = new MutableLiveData();
    private MutableLiveData<String> district = new MutableLiveData();
    private MutableLiveData<String> city = new MutableLiveData();

    public LiveData<String> getAddress() {
        return address;
    }

    public LiveData<String> getDistrict() {
        return district;
    }

    public LiveData<String> getCity() {
        return city;
    }

    public LiveData<String> getState() {
        return state;
    }

    public LiveData<String> getCep() {
        return cep;
    }

    public Client getClient() {
        return client;
    }

    public Endereco getEndereco() {
        return endereco;
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

    public void setEndereco(Endereco endereco) {
        client.setCep(endereco.getCep());

        cep.setValue(endereco.getCep());
        address.setValue(endereco.getAddress());
        city.setValue(endereco.getCity());
        state.setValue(endereco.getState());
        district.setValue(endereco.getDistrict());
    }

    public void setSenha(String senha) {
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
