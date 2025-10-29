package com.benevo.serivce;


import com.benevo.model.Item;
import com.benevo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // ita chama essa funcao pra validar os itens
    // @param item - objeto Item que veio do formulario
    // @return - Item que foi salvo no banco ja com o id
    public Item registrarItem(Item item) {

        // aplicar RNs pra verificar se o item é valido?

        // RN not null
        if (item.getNome() == null || item.getNome().trim().isEmpty()) {
            // lança uma excecao depois, por agora da p retornar null
            return null;
        }

        if (item.getCategoria() == null || item.getCategoria().trim().isEmpty()) {
            return null;
        }

        if (item.getCondicao() == null || item.getCondicao().trim().isEmpty()) {
            return null; // Deu errado
        }

        if (item.getTamanho() == null || item.getTamanho().trim().isEmpty()) {
            return null; // Deu errado
        }

        if (item.getDescricao() == null || item.getDescricao().trim().isEmpty()) {
            return null; // Deu errado
        }

        // se o item passou nas validacoes, salva no repositorio de peu
        Item itemSalvo = itemRepository.save(item);

        // retorna o item salvo
        return itemSalvo;

    }

    // depois vai ter funcao p excluir, editar, etc, dnv com a mesma logica
}

