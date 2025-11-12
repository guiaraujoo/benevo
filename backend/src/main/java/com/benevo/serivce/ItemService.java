package com.benevo.service;

import com.benevo.dto.ItemCadastroDTO;
import com.benevo.model.Item;
import com.benevo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // REGISTRAR ITEM
    public Item registrarItem(Item item) {
        validarItem(item);

        item.setStatus("Disponível");
        item.setDataEntrada(LocalDateTime.now());

        return itemRepository.save(item);
    }

    // LISTAR ITENS FILTRADOS (Fluxo 2)
    public List<Item> listarItensFiltrados(String termoBusca, String sortData) {
        Sort sort;

        if ("ASC".equalsIgnoreCase(sortData)) {
            sort = Sort.by(Sort.Direction.ASC, "dataEntrada");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "dataEntrada");
        }

        if (termoBusca != null && !termoBusca.trim().isEmpty()) {
            System.out.println("Service: Buscando por TERMO: " + termoBusca);
            // ⚠️ Confirme com o Pedro o nome exato do método no ItemRepository
            return itemRepository.findByNomeContainingIgnoreCaseOrCategoriaContainingIgnoreCase(termoBusca, termoBusca, sort);
        }

        return itemRepository.findAll(sort);
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

        // Atualiza apenas os campos permitidos
        item.setNome(dto.getNome());
        item.setCategoria(dto.getCategoria());
        item.setCondicao(dto.getCondicao());
        item.setTamanho(dto.getTamanho());
        item.setDescricao(dto.getDescricao());

        // Validação antes de salvar
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
