package com.benevo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "beneficiarios")
public class Beneficiario {

    @Id
    private String id;
    private String nomeCompleto;
    private String cpf;
    private String endereco;
    private List<String> categoriasInteresse;

    // Para login e aprovação
    private String idBenevo;
    private String status; // Ex: "Pendente", "Aprovado"

    public Beneficiario() {}

    public Beneficiario(String nomeCompleto, String cpf, String endereco, List<String> categoriasInteresse, String idBenevo, String status) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.endereco = endereco;
        this.categoriasInteresse = categoriasInteresse;
        this.idBenevo = idBenevo;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public List<String> getCategoriasInteresse() { return categoriasInteresse; }
    public void setCategoriasInteresse(List<String> categoriasInteresse) { this.categoriasInteresse = categoriasInteresse; }

    public String getIdBenevo() { return idBenevo; }
    public void setIdBenevo(String idBenevo) { this.idBenevo = idBenevo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}