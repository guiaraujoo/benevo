// IMPORTANTE: Importar funções da API
import { apiAjudarLogin } from "../services/api.js";

document.addEventListener("DOMContentLoaded", function () {
    console.log("ajuda-login.js carregado");

    const btnBuscar = document.getElementById("btn-buscar-benef");
    const campoNome = document.getElementById("busca-nome-benef");
    const campoResposta = document.getElementById("resposta-benef");
    const divResultados = document.getElementById("resultados-ajuda");

    if (!btnBuscar || !campoNome || !campoResposta || !divResultados) {
        console.error("Elementos da página de ajuda-login não encontrados.");
        return;
    }

    // Clique em buscar beneficiário
    btnBuscar.addEventListener("click", async () => {
        const termo = campoNome.value;

        if (!termo) {
            alert("Digite um nome para buscar.");
            return;
        }

        try {
            const resultados = await apiAjudarLogin(termo);

            divResultados.innerHTML = "";

            resultados.forEach(user => {
                const el = document.createElement("div");
                el.className = "resultado-busca";

                el.innerHTML = `
                    <p><strong>${user.nome}</strong></p>
                    <p>Pergunta: ${user.pergunta}</p>
                    <button class="btn-validar" data-id="${user.id}" data-resp="${user.resposta}">
                        Validar Resposta
                    </button>
                `;

                divResultados.appendChild(el);
            });

        } catch (error) {
            console.error("Erro ao buscar beneficiário:", error);
            alert("Erro ao buscar. Tente novamente.");
        }
    });

    // Clique em validar resposta secreta
    divResultados.addEventListener("click", (event) => {
        if (event.target.classList.contains("btn-validar")) {

            const respostaCorreta = event.target.dataset.resp;
            const respostaDigitada = campoResposta.value;

            if (!respostaDigitada) {
                alert("Digite a resposta secreta.");
                return;
            }

            if (respostaDigitada === respostaCorreta) {
                alert("Resposta correta! Você pode recuperar sua conta!");
            } else {
                alert("Resposta incorreta!");
            }
        }
    });
});
