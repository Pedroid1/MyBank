package com.example.mybank.model;

public class Client {
    private Integer id;
    private String name, cpf, date, email, phone;
    private Integer senha;

    public Client() {
    }

    public Client(Integer id, String name, String cpf, String date, String email, String phone, Integer senha) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.date = date;
        this.email = email;
        this.phone = phone;
        this.senha = senha;
    }

    public Client(String name, String cpf, String date, String email, String phone, Integer senha) {
        this.name = name;
        this.cpf = cpf;
        this.date = date;
        this.email = email;
        this.phone = phone;
        this.senha = senha;
    }

    public Integer getSenha() {
        return senha;
    }

    public void setSenha(Integer senha) {
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
