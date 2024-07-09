package it.uniroma3.siw.siwpers.controller;

public class RegisterRequest {
    private String nome;
    private String cognome;
    private String password;
    private String email;
    private String dob;

    public RegisterRequest() {
    }

    public RegisterRequest(String nome, String cognome, String password, String email, String dob) {
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }
}
