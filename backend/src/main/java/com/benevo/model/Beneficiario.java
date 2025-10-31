package com.benevo.model;

//@Table(name = "Beneficiario")
//@Entity
//public class Beneficiario {
//
//    @Id
//    private Integer id;
//
//    @Column(name = "nome")
//    private String nome;
//
//    @Column(name = "idade")
//    private Integer idade;
//
//    @Column(name = "endereco")
//    private String endereco;
//
//    // 🔹 Construtor sem argumentos
//    public Beneficiario() {
//    }
//
//    // 🔹 Construtor com todos os argumentos
//    public Beneficiario(Integer id, String nome, Integer idade, String endereco) {
//        this.id = id;
//        this.nome = nome;
//        this.idade = idade;
//        this.endereco = endereco;
//    }
//
//    // 🔹 Getters e Setters
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public Integer getIdade() {
//        return idade;
//    }
//
//    public void setIdade(Integer idade) {
//        this.idade = idade;
//    }
//
//    public String getEndereco() {
//        return endereco;
//    }
//
//    public void setEndereco(String endereco) {
//        this.endereco = endereco;
//    }
//
//    // 🔹 Implementação manual de um "builder"
//    public static class Builder {
//        private Integer id;
//        private String nome;
//        private Integer idade;
//        private String endereco;
//
//        public Builder id(Integer id) {
//            this.id = id;
//            return this;
//        }
//
//        public Builder nome(String nome) {
//            this.nome = nome;
//            return this;
//        }
//
//        public Builder idade(Integer idade) {
//            this.idade = idade;
//            return this;
//        }
//
//        public Builder endereco(String endereco) {
//            this.endereco = endereco;
//            return this;
//        }
//
//        public Beneficiario build() {
//            return new Beneficiario(id, nome, idade, endereco);
//        }
//    }
//
//    public static Builder builder() {
//        return new Builder();
//    }
//}
