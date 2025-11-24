import { apiRegistrarBeneficiario } from '../services/api.js';

document.getElementById("form-cad-beneficiario").addEventListener("submit", async (e) => {
    e.preventDefault();

    const local = document.getElementById("local").value;
    
    if (local.length < 3) {
        alert("Por favor, digite um local válido (pelo menos 3 letras).");
        return;
    }

    const data = {
        nome: document.getElementById("nome").value,
        bairroCidade: local,
        email: document.getElementById("email").value || null,
        telefone: document.getElementById("telefone").value || null,
        perguntaSeguranca: document.getElementById("pergunta").value,
        respostaSeguranca: document.getElementById("resposta").value,
        categoriasInteresse: []
    };

    try {
        const res = await apiRegistrarBeneficiario(data);
        
        // Mostra o ID gerado e instrui o usuário
        alert(`Cadastro realizado com sucesso!\n\nSEU ID DE ACESSO É: ${res.idBenevo}\n\nPor favor, anote este ID. Você será redirecionado para o login.`);
        
        // CORREÇÃO: Redireciona para a tela de login do beneficiário
        window.location.href = "login-beneficiario.html";
        
    } catch (err) {
        console.error(err);
        alert("Erro ao cadastrar: " + err.message);
    }
});