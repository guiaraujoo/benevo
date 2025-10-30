import com.benevo.model.Item;
import com.benevo.repository.ItemRepository;
import com.benevo.serivce.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    // Injeta o mock 'itemRepository' dentro do 'itemService'
    @InjectMocks
    private ItemService itemService; // <-- Adicionado (precisa testar o service)

    @Test
    void testRegistrarItem_ComDadosValidos_DeveSalvarItem() {
        // 1. Arrange
        Item itemParaSalvar = new Item();
        itemParaSalvar.setNome("Camiseta Branca");
        itemParaSalvar.setCategoria("Roupa");
        itemParaSalvar.setCondicao("Novo");
        itemParaSalvar.setTamanho("M");
        itemParaSalvar.setDescricao("Camiseta de algodão.");

        // Define o comportamento esperado do mock
        when(itemRepository.save(any(Item.class))).thenReturn(itemParaSalvar);

        // 2. Act
        Item itemSalvo = itemService.registrarItem(itemParaSalvar);

        // 3. Assert
        assertNotNull(itemSalvo);
        assertEquals("Camiseta Branca", itemSalvo.getNome());
        verify(itemRepository, times(1)).save(itemParaSalvar);
    }

    @Test
    void testRegistrarItem_ComNomeNulo_DeveLancarExcecao() {
        // 1. Arrange
        Item itemInvalido = new Item();
        itemInvalido.setNome(null); // Inválido
        itemInvalido.setCategoria("Roupa");
        itemInvalido.setCondicao("Novo");
        itemInvalido.setTamanho("M");
        itemInvalido.setDescricao("Descrição.");

        // 2. Act & 3. Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            itemService.registrarItem(itemInvalido);
        });

        assertEquals("O nome do item é obrigatório.", exception.getMessage());
        verify(itemRepository, never()).save(itemInvalido);
    }
}