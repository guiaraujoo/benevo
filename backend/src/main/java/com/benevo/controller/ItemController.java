package main.java.com.benevo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.seuprojeto.api.dto.ItemRequestDTO;
import com.seuprojeto.api.service.ItemService;

@RestController
@RequestMapping("/item")
@CrossOrigin("*") 
public class ItemController {
    
@Autowired
private ItemService itemService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarItem(@RequestBody ItemRequestDTO itemDTO) {

       
        System.out.println("Tentativa de cadastro do item: " + itemDTO.getNome());

        
        boolean cadastrado = itemService.cadastrarItem(itemDTO);

        if (cadastrado) {
            return ResponseEntity.ok("Item cadastrado com sucesso!");
        } else {
            
            return ResponseEntity.status(400).body("Erro ao cadastrar o item.");
        }
    }

 
    // @GetMapping("/listar")
    // public ResponseEntity<?> listarItens() {

       
    //     var lista = itemService.listarItens();

    
    //     return ResponseEntity.ok(lista);
    // }

   
    // @GetMapping("/{id}")
    // public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

        
    //     var item = itemService.buscarPorId(id);

    //     if (item != null) {
    //         return ResponseEntity.ok(item);
    //     } else {
           
    //         return ResponseEntity.status(404).body("Item não encontrado.");
    //     }
    // }


    // @DeleteMapping("/{id}")
    // public ResponseEntity<String> deletarItem(@PathVariable Long id) {

    //     boolean deletado = itemService.deletarItem(id);

    //     if (deletado) {
    //         return ResponseEntity.ok("Item deletado com sucesso!");
    //     } else {
    //         return ResponseEntity.status(404).body("Item não encontrado para exclusão.");
    //     }
    // }


    // @PutMapping("/{id}")
    // public ResponseEntity<String> atualizarItem(@PathVariable Long id, @RequestBody ItemRequestDTO itemDTO) {

    //     boolean atualizado = itemService.atualizarItem(id, itemDTO);

    //     if (atualizado) {
    //         return ResponseEntity.ok("Item atualizado com sucesso!");
    //     } else {
    //         return ResponseEntity.status(404).body("Item não encontrado para atualização.");
    //     }
    // }
}
    


