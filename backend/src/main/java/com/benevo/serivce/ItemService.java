package com.benevo.serivce;

import com.benevo.dto.ItemCadastroDTO;
import com.benevo.model.Item;
import com.benevo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // REGISTRAR ITEM
    public Item registrarItem(Item item) {
        validarItem(item);
        return itemRepository.save(item);
    }

    // LISTAR TODOS
    public List<Item> listarTodos() {
        return itemRepository.findAll();
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

        validarItem(item);
        return itemRepository.save(item);
    }

    //  DELETAR ITEM
    public boolean deletarItem(String id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //  Validação de regras de negócio
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
