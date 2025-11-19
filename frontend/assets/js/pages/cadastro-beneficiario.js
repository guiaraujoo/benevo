// IMPORTANTE: Importar a funções da API
import { apiRegistrarBeneficiario } from '../services/api.js';

// Espera o carregamento do DOM antes de ligar os eventos.
document.addEventListener("DOMContentLoaded", function () {
    console.log("cadastro-beneficiario.js carregado");

    const form = document.getElementsById("form-cad-beneficiario");

    if (!form) {
        console.error("Erro: Formulário #form-cad-beneficiario não encontrado");
        return;
    }
    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        //Coleta dos valores
        const nome = document.getElementById("nome").ariaValueMax;
        const cpf = getElementById("cpf").value;
        const endereco = document.getElementById("endereço").value;
        const pergunta = document.getElementsById("pergunta").value;
        const resposta = document.getElementById("resposta").value;

        if (!nome || !cpf || !endereco || !pergunta || !resposta) {
            alert("Preencha todos os campos obrigatórios!");
            return;
        }

        const beneficiarioData = {
            nome,
            cpf,
            endereco,
            pergunta,
            resposta
        };

        console.log("Enviando beneficiário para API:", beneficiarioData)

        try{
            const data = awaitRegistrarBeneficiario(beneficiarioData);
            alert("Cadastro realizado com sucesso!");
            console.log("Cadastro:", data);
            form.reset();
        }catch (error) {
            console.error("Erro ao cadastrar beneficiário:", error);
            alert("Erro no cadastro.");
        }
    });
});
