// benevo-frontend/assets/js/cadastro-item.js

import { cadastrarItem } from "./services/api.js";

const formItem = document.querySelector("#form-item");

formItem.addEventListener("submit", async (event) => {
  event.preventDefault();

  const itemData = {
    nome: document.querySelector("#nome-item").value,
    descricao: document.querySelector("#descricao-item").value,
    categoria: document.querySelector("#categoria").value,
  };

  try {
    await cadastrarItem(itemData);
    alert("Item cadastrado com sucesso!");
    formItem.reset();
  } catch (error) {
    alert("Erro ao cadastrar o item.");
  }
});
