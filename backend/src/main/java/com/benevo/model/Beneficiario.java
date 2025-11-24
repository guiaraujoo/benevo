package com.benevo.model;

public class Beneficiario {
    private Long id;
    private String nome;
    private String email;
    private String telefone;

    // Novos campos para recuperação de conta
    private String emailRecuperacao;
    private String telefoneRecuperacao;

    // Construtor
    public Beneficiario() {}

    public Beneficiario(Long id, String nome, String email, String telefone, String emailRecuperacao, String telefoneRecuperacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.emailRecuperacao = emailRecuperacao;
        this.telefoneRecuperacao = telefoneRecuperacao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmailRecuperacao() { return emailRecuperacao; }
    public void setEmailRecuperacao(String emailRecuperacao) { this.emailRecuperacao = emailRecuperacao; }

    public String getTelefoneRecuperacao() { return telefoneRecuperacao; }
    public void setTelefoneRecuperacao(String telefoneRecuperacao) { this.telefoneRecuperacao = telefoneRecuperacao; }

    @Override
    public String toString() {
        return "Beneficiario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", emailRecuperacao='" + emailRecuperacao + '\'' +
                ", telefoneRecuperacao='" + telefoneRecuperacao + '\'' +
                '}';
    }
}