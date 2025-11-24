// IMPORTANTE: Importar funções da API
import { apiLstarItens, apiReservarItem } from '../services/api.js';

// --- Elementos do HTML ---
const grid = document.getElementById("item-grid");
const FormBusca = document.getElementById("form-busca-resgate")

// Renderiza os cards dos itens para resgate
function renderizarItens(itens) {
    grid.innerHTML = "";

    if (!itens || itens.length == 0) {
        grid.innerHTML = "<p>Nenhum item encontrado</p>";
        return;
    }
    itens.forEach(item => {
        const card = document.createElement("div");
        card.className = "item-card";

        card.innerHTML = `
            <h3>${item.nome}</h3>
            <p>Categoria: ${item.categoria}</p>
            <p>Condição: ${item.condicao}</p>
            <button class="btn-resgatar" data-id="${item.id}">Quero este!</button>
        `;

        grid.appendChild(card);
    });
}

//Carrega os itens com base na busca/filtro
async function carregarItens() {
    const termoBusca = document.getElementById("search-input-resgate").value
    const sortData = document.getElementById("filtro-data-resgate").value;

    const itens = await apiLstarItens(termoBusca, sortData);
    renderizarItens(itens);
}

//Evento do formulário de busca
FormBusca.addEventListener("submit", (e) => {
    e.preventDefault();
    carregarItens();
});

// Botão 'Quero este!'
grid.addEventListener("click", async (event) => {
    if (event.target.classList.contains(btn-resgatar));

    const itemId = event.target.dataset.id;
    const beneficiarioId = localStorage.getItem("user_id"); //ID salvo no login

    if (!beneficiarioId) {
        alert("Erro: Usuário não está logado.");
        return;
    }

    try {
        await apiReservarItem(itemId, beneficiarioId);
        alert("Item reservado com sucesso!");
        carregarItens();
    } catch (error) {
        console.error("Erro ao reservar item", error);
        alert("Falha ao reservar item.");
    }
});

// Carrega os itens ao abrir a página
document.addEventListener("DOMContentLoaded", () => {
    carregarItens();
})