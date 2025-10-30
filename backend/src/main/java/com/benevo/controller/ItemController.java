package com.benevo.controller;

import com.benevo.dto.ItemCadastroDTO;
import com.benevo.model.Item;
import com.benevo.serivce.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
@CrossOrigin("*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // CADASTRAR ITEM
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarItem(@RequestBody ItemCadastroDTO dto) {
        System.out.println("Tentativa de cadastro do item: " + dto.getNome());

        try {
            Item item = new Item(
                    null,
                    dto.getNome(),
                    dto.getCategoria(),
                    dto.getCondicao(),
                    dto.getTamanho(),
                    dto.getDescricao()
            );

            Item itemSalvo = itemService.registrarItem(item);
            return ResponseEntity.ok(itemSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro inesperado ao cadastrar o item.");
        }
    }

    // LISTAR TODOS OS ITENS
    @GetMapping("/listar")
    public ResponseEntity<List<Item>> listarItens() {
        List<Item> itens = itemService.listarTodos();
        return ResponseEntity.ok(itens);
    }

    // BUSCAR ITEM POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        Optional<Item> itemOpt = itemService.buscarPorId(id);

        if (itemOpt.isPresent()) {
            return ResponseEntity.ok(itemOpt.get());
        } else {
            return ResponseEntity.status(404).body("Item não encontrado.");
        }
    }

    // ATUALIZAR ITEM
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarItem(@PathVariable String id, @RequestBody ItemCadastroDTO dto) {
        try {
            Item atualizado = itemService.atualizarItem(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //  DELETAR ITEM
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarItem(@PathVariable String id) {
        boolean removido = itemService.deletarItem(id);

        if (removido) {
            return ResponseEntity.ok("Item removido com sucesso!");
        } else {
            return ResponseEntity.status(404).body("Item não encontrado.");
        }
    }
}

    


