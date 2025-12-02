import { apiRegistrarBeneficiario } from '../services/api.js';

// 1. Obtenção dos elementos do Modal
const modalSucesso = document.getElementById('modal-sucesso-cadastro');
const beneficiaryIdDisplay = document.getElementById('beneficiaryIdDisplay');
const btnEntendido = document.getElementById('btn-entendido-cadastro');

function mostrarModalSucesso(id) {
    beneficiaryIdDisplay.textContent = id;
    modalSucesso.classList.add('visivel');
}

function fecharModalSucesso() {
    modalSucesso.classList.remove('visivel');
    window.location.href = 'login-beneficiario.html'; 
}

btnEntendido.addEventListener('click', fecharModalSucesso);


document.getElementById("form-cad-beneficiario").addEventListener("submit", async (e) => {
    e.preventDefault();
    
    const btnCadastrar = document.querySelector('.btn-cadastrar');
    btnCadastrar.disabled = true;
    btnCadastrar.textContent = 'Aguarde...';

    const local = document.getElementById("local").value;
    
    if (local.length < 3) {
        alert("Por favor, digite um local válido (pelo menos 3 letras).");
        btnCadastrar.disabled = false;
        btnCadastrar.textContent = 'Solicitar Cadastro';
        return;
    }
    
    const data = {
        nome: document.getElementById("nome").value,
        bairroCidade: local,
        email: document.getElementById("email").value || null,
        telefone: document.getElementById("telefone").value || null,
        perguntaSeguranca: document.getElementById("pergunta").value,
        respostaSeguranca: document.getElementById("resposta").value, // Adicionei resposta para completar o objeto
    };

    try {
        const resultado = await apiRegistrarBeneficiario(data); 

        mostrarModalSucesso(resultado.id); 

    } catch (error) {
        alert("Erro no cadastro: " + (error.message || "Tente novamente mais tarde."));
        console.error("Detalhes do erro:", error);
    } finally {
        if (!modalSucesso.classList.contains('visivel')) {
            btnCadastrar.disabled = false;
            btnCadastrar.textContent = 'Solicitar Cadastro';
        }
    }
});