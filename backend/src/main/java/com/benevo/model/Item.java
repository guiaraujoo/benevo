package com.benevo.model;

import jakarta.persistence.*;

@Table(name = "item")
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "Nome", unique = true)
    private String nome;

    @Column(name = "Categoria")
    private String categoria;

    @Column(name = "Condição")
    private String condicao;

    @Column(name = "Tamanho")
    private String tamanho;

    @Column(name = "Descricao")
    private String descricao;

    // Construtor padrão
    public Item() {
    }

    // Construtor com todos os campos
    public Item(Integer id, String nome, String categoria, String condicao, String tamanho, String descricao) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.condicao = condicao;
        this.tamanho = tamanho;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
