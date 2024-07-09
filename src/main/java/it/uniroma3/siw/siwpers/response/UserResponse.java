package it.uniroma3.siw.siwpers.response;

public class UserResponse {
    private final String email;
    private final String nome;
    private final String cognome;
    private final String dob;
    private final Long id;

    public UserResponse(String email, String nome, String cognome, String dob, Long id) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.dob = dob;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }
}
