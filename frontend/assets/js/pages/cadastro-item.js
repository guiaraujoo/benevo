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
                
                // === MODAL DE SUCESSO ===
                exibirModalAgradecimento(data.nome);

                formDoacao.reset(); 
            })
            .catch(error => {
                console.error('Erro ao cadastrar item:', error);
                alert('Erro ao cadastrar item. O item NÃO foi salvo.');
            });
    });
});


/**
 * Modal simples sem HTML extra no arquivo
 * Ele é injetado dinamicamente no final do <body>
 * @param {string} itemNome 
 */
function exibirModalAgradecimento(itemNome) {

    // Remove modal anterior se existir
    const modalExistente = document.getElementById("modal-agradecimento");
    if (modalExistente) modalExistente.remove();

    const nomeDoador = localStorage.getItem('user_nome') || "Doador";

    const modal = document.createElement("div");
    modal.id = "modal-agradecimento";
    modal.style.position = "fixed";
    modal.style.top = "0";
    modal.style.left = "0";
    modal.style.width = "100%";
    modal.style.height = "100%";
    modal.style.background = "rgba(0,0,0,0.6)";
    modal.style.display = "flex";
    modal.style.alignItems = "center";
    modal.style.justifyContent = "center";
    modal.style.zIndex = "9999";

    modal.innerHTML = `
        <div style="
            background: white;
            padding: 30px;
            width: 90%;
            max-width: 380px;
            border-radius: 14px;
            text-align: center;
            font-family: Poppins, sans-serif;
        ">
            <h2 style="margin-bottom: 10px;">Obrigado, ${nomeDoador}!</h2>
            <p>Seu item <strong>${itemNome}</strong> foi registrado com sucesso.</p>

            <button id="btn-fechar-modal" style="
                margin-top: 25px;
                background: #7A3EF5;
                color: white;
                padding: 12px 25px;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                cursor: pointer;
            ">Fechar</button>
        </div>
    `;

    document.body.appendChild(modal);

    document.getElementById("btn-fechar-modal").addEventListener("click", () => {
        modal.remove();
    });
}