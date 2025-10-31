package com.benevo.model;

//@Table(name = "Doador")
//@Entity
//public class Doador {
//
//    @Id
//    private Integer id;
//
//    @Column(name = "email", unique = true)
//    private String email;
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
//    public Doador() {
//    }
//
//    // 🔹 Construtor com todos os argumentos
//    public Doador(Integer id, String email, String nome, Integer idade, String endereco) {
//        this.id = id;
//        this.email = email;
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
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
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
//        private String email;
//        private String nome;
//        private Integer idade;
//        private String endereco;
//
//        public Builder id(Integer id) {
//            this.id = id;
//            return this;
//        }
//
//        public Builder email(String email) {
//            this.email = email;
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
//        public Doador build() {
//            return new Doador(id, email, nome, idade, endereco);
//        }
//    }
//
//    public static Builder builder() {
//        return new Builder();
//    }
//}
