package com.benevo; // Certifique-se que o pacote está correto

import com.benevo.model.Item;
import com.benevo.repository.ItemRepository;
import com.benevo.serivce.ItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List; // Importar a classe List
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    // --- MODIFICADO ---
    // Removemos o @AfterEach. O banco será limpo APENAS ANTES de cada teste.
    // O item do último teste executado permanecerá no banco.
    @BeforeEach
    void cleanupDatabase() {
        System.out.println("--- LIMPANDO O BANCO DE DADOS (ANTES DO TESTE) ---");
        itemRepository.deleteAll();
    }

    // --- NOVO TESTE PARA REGISTRAR, CONSULTAR E MOSTRAR NO OUTPUT ---
    @Test
    void testRegistrarEVerificarConsultaNoOutput() {
        System.out.println("--- INICIANDO: testRegistrarEVerificarConsultaNoOutput ---");

        // 1. Arrange (Registrar o item)
        Item itemParaSalvar = new Item(
                null,
                "Camiseta Regata (Teste Consulta)",
                "Vestimenta",
                "5",
                "G",
                "Camiseta para mostrar no output."
        );

        Item itemSalvo = itemService.registrarItem(itemParaSalvar);
        System.out.println("Item salvo com ID: " + itemSalvo.getId());

        // 2. Act (Consultar o banco de dados)
        // Vamos consultar todos os itens (deve ser apenas 1)
        List<Item> itensNoBanco = itemRepository.findAll();

        // 3. Assert & Print (Mostrar no output)
        assertFalse(itensNoBanco.isEmpty(), "O banco de dados não deveria estar vazio após salvar.");

        System.out.println("\n--- RESULTADO DA CONSULTA (findAll) ---");
        for (Item item : itensNoBanco) {
            // Graças ao método toString() que adicionamos, isso será impresso de forma legível
            System.out.println(item.toString());
        }
        System.out.println("--- FIM DO TESTE: testRegistrarEVerificarConsultaNoOutput ---");
    }
    // --- FIM DO NOVO TESTE ---


    @Test
    void testRegistrarItem_ComDadosValidos_DeveSalvarItemNoMongoDB() {
        // ... (este teste continua igual)
        Item itemParaSalvar = new Item(
                null, "Camiseta Branca", "Vestimenta", "3", "M", "Camiseta de algodão."
        );
        Item itemSalvo = itemService.registrarItem(itemParaSalvar);
        assertNotNull(itemSalvo);
        assertNotNull(itemSalvo.getId());
        Optional<Item> itemDoBanco = itemRepository.findById(itemSalvo.getId());
        assertTrue(itemDoBanco.isPresent());
    }

    @Test
    void testRegistrarItem_ComNomeNulo_DeveLancarExcecaoENaoSalvar() {
        // ... (este teste continua igual)
        Item itemInvalido = new Item(
                null, null, "Roupa", "Novo", "M", "Descrição."
        );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            itemService.registrarItem(itemInvalido);
        });
        assertEquals("O nome do item é obrigatório.", exception.getMessage());
        assertEquals(0, itemRepository.count());
    }

    @Test
    void testBuscarPorId_QuandoItemExiste_DeveRetornarItem() {
        // ... (este teste continua igual)
        Item itemSetup = new Item(null, "Item de Teste", "Categoria", "Novo", "P", "Desc");
        Item itemSalvoNoSetup = itemRepository.save(itemSetup);
        String idSalvo = itemSalvoNoSetup.getId();
        Optional<Item> itemEncontradoOpt = itemService.buscarPorId(idSalvo);
        assertTrue(itemEncontradoOpt.isPresent());
    }

    @Test
    void testBuscarPorId_QuandoItemNaoExiste_DeveRetornarVazio() {
        // ... (este teste continua igual)
        String idFalso = "60c72b9fffb4e0001f000001";
        Optional<Item> itemEncontradoOpt = itemService.buscarPorId(idFalso);
        assertTrue(itemEncontradoOpt.isEmpty());
    }
}