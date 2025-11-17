// IMPORTANTE: Adicione esta linha no topo do arquivo.
import { cadastrarItem } from '../services/api.js';

/**
 * Aguarda o carregamento completo do DOM antes de anexar os ouvintes de evento.
 */
document.addEventListener("DOMContentLoaded", function () {
    
    // DEBUG: Confirma que o script está rodando e o DOM está pronto.
    console.log('Script cadastro-item.js carregado e pronto.');

    const formDoacao = document.getElementById('form-doacao');

    // DEBUG: Verifica se o formulário foi encontrado
    if (formDoacao) {
        console.log('Formulário #form-doacao encontrado. Anexando ouvinte.');
    } else {
        console.error('ERRO: Formulário #form-doacao NÃO encontrado.');
        return;
    }

    formDoacao.addEventListener('submit', function (event) {
        
        event.preventDefault(); 
        console.log('Submit do formulário capturado.'); // DEBUG

        // 2. Coleta os valores
        const nome = document.getElementById('nome').value;
        const categoria = document.getElementById('categoria').value;
        const condicao = document.getElementById('condicao').value;
        const tamanho = document.getElementById('tamanho').value;
        const descricao = document.getElementById('descricao').value; 
           
        // 3. Validação
        if (!nome || !categoria || !descricao || !condicao || !tamanho) {
            alert('Por favor, preencha todos os campos obrigatórios.');
            return; 
        }

        // 4. Monta o DTO
        const doadorId = localStorage.getItem('user_id');

        const itemData = {
            nome: nome,
            categoria: categoria,
            descricao: descricao,
            condicao: condicao,
            tamanho: tamanho,
            doadorId: doadorId
        };

        const token = localStorage.getItem("token_benevo");

        if (!token) {
            alert('Erro: Nenhum doador logado. Faça login novamente antes de cadastrar um item.');
            console.error('ERRO: Token do doador não encontrado no localStorage.');
            return;
        }

        // 5. Chama a API
        console.log('Enviando para a API:', itemData); // DEBUG
        cadastrarItem(itemData, token) // ← agora enviando também o token
            .then(data => {
                console.log('Resposta da API:', data); // DEBUG
                alert('Item "' + data.nome + '" adicionado no banco de dados.');
                formDoacao.reset(); 
            })
            .catch(error => {
                console.error('Erro ao cadastrar item:', error);
                alert('Erro ao cadastrar item. O item NÃO foi salvo.');
            });
    });
});
