// benevo-frontend/assets/js/login.js

import { fazerLogin } from "./services/api.js";

const form = document.querySelector("#form-login");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const login = document.querySelector("#login").value;
  const senha = document.querySelector("#senha").value;

  try {
    const resposta = await fazerLogin(login, senha);
    alert("Login realizado com sucesso!");

    // Exemplo: armazenar token (se o backend enviar)
    if (resposta.token) {
      localStorage.setItem("token", resposta.token);
    }

    // Redirecionar para a página de cadastro de item
    window.location.href = "cadastro-item.html";
  } catch (error) {
    alert("Erro ao fazer login. Verifique suas credenciais.");
  }
});
