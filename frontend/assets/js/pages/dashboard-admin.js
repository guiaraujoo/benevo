// --- IMPORTAÇÕES ---
// (supondo que as funções da API do Dhefferson estão neste arquivo)
import { apiBuscarItens, apiEditarItem, apiExcluirItem } from "../services/api.js";

// --- Elementos do HTML ---
const tabelaBody = document.getElementById("corpo-tabela-itens");
const formFiltros = document.getElementById("form-filtros");
const modal = document.getElementById("modal-editar");
const btnFecharModal = document.getElementById("btn-fechar-modal");
const formEditar = document.getElementById("form-editar-item");

// --- Recupera o token do usuário logado ---
const token = localStorage.getItem("token_doador") || localStorage.getItem("token_funcionario");

if (!token) {
  alert("Erro: Nenhum usuário logado. Faça login novamente para acessar o painel.");
  console.error("ERRO: Nenhum token encontrado no localStorage.");
  // opcional: redirecionar
  // window.location.href = "../pages/login.html";
}

// --- 1. Renderizar a Tabela ---
function renderizarTabela(itens) {
  tabelaBody.innerHTML = "";

  if (!itens || itens.length === 0) {
    tabelaBody.innerHTML = '<tr><td colspan="4">Nenhum item encontrado.</td></tr>';
    return;
  }

  itens.forEach(item => {
    const tr = document.createElement("tr");

    const tdNome = document.createElement("td");
    tdNome.textContent = item.nome;
    tr.appendChild(tdNome);

    const tdCategoria = document.createElement("td");
    tdCategoria.textContent = item.categoria;
    tr.appendChild(tdCategoria);

    const tdCondicao = document.createElement("td");
    tdCondicao.textContent = item.condicao;
    tr.appendChild(tdCondicao);

    const tdAcoes = document.createElement("td");

    const btnEditar = document.createElement("button");
    btnEditar.textContent = "Editar";
    btnEditar.className = "btn-editar";
    btnEditar.addEventListener("click", () => abrirModalParaEditar(item));
    tdAcoes.appendChild(btnEditar);

    const btnExcluir = document.createElement("button");
    btnExcluir.textContent = "Excluir";
    btnExcluir.className = "btn-excluir";
    btnExcluir.addEventListener("click", async () => {
      if (confirm(`Tem certeza que deseja excluir o item "${item.nome}"?`)) {
        await apiExcluirItem(item.id, token); // NOVO: Enviando token
        carregarItens(); // Recarrega a tabela
      }
    });
    tdAcoes.appendChild(btnExcluir);

    tr.appendChild(tdAcoes);
    tabelaBody.appendChild(tr);
  });
}

// --- 2. Abrir e Preencher o Modal ---
function abrirModalParaEditar(item) {
  document.getElementById("edit-item-id").value = item.id;
  document.getElementById("edit-nome").value = item.nome;
  document.getElementById("edit-categoria").value = item.categoria;
  // adicione mais campos se existirem...

  modal.classList.add("visivel");
}

// --- 3. Eventos ---
async function carregarItens(filtros = {}) {
  console.log("Carregando itens com filtros:", filtros); // DEBUG
  const itens = await apiBuscarItens(filtros, token); // NOVO: Enviando token
  renderizarTabela(itens);
}

formFiltros.addEventListener("submit", (e) => {
  e.preventDefault();
  const categoria = document.getElementById("filtro-categoria").value;
  const condicao = document.getElementById("filtro-condicao").value;
  carregarItens({ categoria, condicao });
});

btnFecharModal.addEventListener("click", () => {
  modal.classList.remove("visivel");
});

formEditar.addEventListener("submit", async (e) => {
  e.preventDefault();
  const id = document.getElementById("edit-item-id").value;
  const itemAtualizado = {
    nome: document.getElementById("edit-nome").value,
    categoria: document.getElementById("edit-categoria").value,
    // mais campos...
  };
  await apiEditarItem(id, itemAtualizado, token); // NOVO: Enviando token
  modal.classList.remove("visivel");
  carregarItens();
});

document.addEventListener("DOMContentLoaded", () => {
  carregarItens();
});
