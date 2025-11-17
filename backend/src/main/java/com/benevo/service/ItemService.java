package com.benevo.service;

import com.benevo.dto.ItemResponseDTO;
import com.benevo.dto.ItemCadastroDTO;
import com.benevo.model.Item;
import com.benevo.repository.DoadorRepository;
import com.benevo.repository.ItemRepository;
import com.benevo.model.Doador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DoadorRepository doadorRepository;

    // REGISTRAR ITEM
    public Item registrarItem(Item item) {
        validarItem(item);

        item.setStatus("Disponível");
        item.setDataEntrada(LocalDateTime.now());

        return itemRepository.save(item);
    }

    // LISTAR TODOS OS ITENS (sem filtros)
    public List<ItemResponseDTO> listarItensComFiltros(String termoBusca, String condicao, String sortBy, String sortDir) {

        // 1. CONSTRUIR A CONSULTA SIMPLES (como era na Sprint 2)
        final Query query = new Query();
        final List<Criteria> criterios = new ArrayList<>();

        if (termoBusca != null && !termoBusca.trim().isEmpty()) {
            criterios.add(new Criteria().orOperator(
                Criteria.where("nome").regex(termoBusca, "i"),
                Criteria.where("categoria").regex(termoBusca, "i")
            ));
        }
        if (condicao != null && !condicao.trim().isEmpty()) {
            criterios.add(Criteria.where("condicao").is(condicao));
        }

        if (!criterios.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criterios.toArray(new Criteria[0])));
        }

        // 2. ORDENAÇÃO (como era na Sprint 2)
        Sort.Direction direcao = "ASC".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        String campoDeSort = "dataEntrada".equalsIgnoreCase(sortBy) ? "dataEntrada" : "condicao";
        query.with(Sort.by(direcao, campoDeSort));

        // 3. EXECUTAR A CONSULTA SIMPLES
        // Isso retorna a lista de ITENS, mas sem o nome do doador.
        List<Item> itensDoBanco = mongoTemplate.find(query, Item.class);

        // 4. TRANSFORMAR E "ENRIQUECER" A LISTA (A Nova Lógica)
        List<ItemResponseDTO> listaDeResposta = new ArrayList<>();

        // Para cada item encontrado...
        for (Item item : itensDoBanco) {
            // Converte o Item para o nosso DTO de resposta
            ItemResponseDTO dto = new ItemResponseDTO(item); //

            // Agora, vamos buscar o nome do doador
            if (item.getDoadorId() != null && !item.getDoadorId().isEmpty()) {
                
                // Busca no repositório de Doadores
                Optional<Doador> doadorOpt = doadorRepository.findById(item.getDoadorId()); //

                if (doadorOpt.isPresent()) {
                    // Se achou, define o nome
                    dto.setDoadorNome(doadorOpt.get().getNome()); //
                } else {
                    dto.setDoadorNome("ID Doador Inválido");
                }
            } else {
                dto.setDoadorNome("Doação Anônima");
            }

            // Adiciona o DTO (agora completo) à lista final
            listaDeResposta.add(dto);
        }

        // 5. RETORNAR A LISTA COMPLETA
        return listaDeResposta;
    }

    // BUSCAR POR ID
    public Optional<Item> buscarPorId(String id) {
        return itemRepository.findById(id);
    }

    // ATUALIZAR ITEM
    public Item atualizarItem(String id, ItemCadastroDTO dto) {
        Optional<Item> itemOpt = itemRepository.findById(id);

        if (itemOpt.isEmpty()) {
            throw new RuntimeException("Item com ID " + id + " não encontrado.");
        }

        Item item = itemOpt.get();

        item.setNome(dto.getNome());
        item.setCategoria(dto.getCategoria());
        item.setCondicao(dto.getCondicao());
        item.setTamanho(dto.getTamanho());
        item.setDescricao(dto.getDescricao());

        validarItem(item);

        return itemRepository.save(item);
    }

    // DELETAR ITEM (nova regra de negócio - RN002)
    public boolean deletarItem(String id) {
        Optional<Item> itemOpt = itemRepository.findById(id);

        if (itemOpt.isEmpty()) {
            return false;
        }

        Item item = itemOpt.get();

        if ("Reservado".equalsIgnoreCase(item.getStatus())) {
            throw new RuntimeException("Não é possível excluir um item com status 'Reservado'. (RN002)");
        }

        itemRepository.deleteById(id);
        return true;
    }

    // RESERVAR ITEM (Fluxo 3)
    public Item reservarItem(String id) {
        Optional<Item> itemOpt = itemRepository.findById(id);

        if (itemOpt.isEmpty()) {
            throw new RuntimeException("Item com ID " + id + " não encontrado.");
        }

        Item item = itemOpt.get();

        if (!"Disponível".equalsIgnoreCase(item.getStatus())) {
            throw new RuntimeException("Somente itens com status 'Disponível' podem ser reservados. (RN003)");
        }

        item.setStatus("Reservado");
        return itemRepository.save(item);
    }

    // Validação de regras de negócio
    private void validarItem(Item item) {
        if (isNullOrEmpty(item.getNome()))
            throw new IllegalArgumentException("O nome do item é obrigatório.");

        if (isNullOrEmpty(item.getCategoria()))
            throw new IllegalArgumentException("A categoria é obrigatória.");

        if (isNullOrEmpty(item.getCondicao()))
            throw new IllegalArgumentException("A condição é obrigatória.");

        if (isNullOrEmpty(item.getTamanho()))
            throw new IllegalArgumentException("O tamanho é obrigatório.");

        if (isNullOrEmpty(item.getDescricao()))
            throw new IllegalArgumentException("A descrição é obrigatória.");
    }

    private boolean isNullOrEmpty(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
