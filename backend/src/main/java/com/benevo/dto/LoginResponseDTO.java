package com.benevo.dto;

public class LoginResponseDTO {
    
    private String id;
    private String token;
    private String tipo;
    private String nome;

    public LoginResponseDTO(String id, String token, String tipo, String nome) {
        this.id = id;
        this.token = token;
        this.tipo = tipo;
        this.nome = nome;
    }

    public String getId() { return id; }
    public String getToken() { return token; }
    public String getTipo() { return tipo; }
    public String getNome() { return nome; }
}