package com.benevo.model;


import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Beneficiário")
@Entity
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "endereco")
    private String endereco;



}