package com.benevo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "beneficiarios")
public class Beneficiario {

    @Id
    private String id;          // ID do MongoDB
    private String idBenevo;    // ID interno gerado
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String status;      // "Pendente", "Aprovado", etc.

    public Beneficiario() {}

    public Beneficiario(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.status = "Pendente";
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdBenevo() { return idBenevo; }
    public void setIdBenevo(String idBenevo) { this.idBenevo = idBenevo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Beneficiario{" +
                "id='" + id + '\'' +
                ", idBenevo='" + idBenevo + '\'' +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
