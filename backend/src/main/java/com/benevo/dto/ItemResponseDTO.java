package com.benevo.dto;

import com.benevo.model.Item; // Importe o Item
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// @Data e @NoArgsConstructor (do Lombok) criam os getters/setters/construtores
// Se você não usa Lombok, crie-os manualmente.
@Data 
@NoArgsConstructor 
public class ItemResponseDTO {

    // Todos os campos do Item
    private String id;
    private String nome;
    private String categoria;
    private String condicao;
    private String tamanho;
    private String descricao;
    private String status;
    private LocalDateTime dataEntrada;
    private String doadorId;

    // O novo campo que queremos "juntar"
    private String doadorNome;

    // Construtor para facilitar a conversão
    public ItemResponseDTO(Item item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.categoria = item.getCategoria();
        this.condicao = item.getCondicao();
        this.tamanho = item.getTamanho();
        this.descricao = item.getDescricao();
        this.status = item.getStatus();
        this.dataEntrada = item.getDataEntrada();
        this.doadorId = item.getDoadorId();
    }
}