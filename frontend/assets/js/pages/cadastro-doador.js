import { apiRegistrarDoador } from '../services/api.js';

document.addEventListener("DOMContentLoaded", () => {
    
    // --- LÓGICA DE MOSTRAR SENHA ---
    function setupTogglePassword(inputId, iconId) {
        const input = document.getElementById(inputId);
        const icon = document.getElementById(iconId);
        
        icon.addEventListener('click', () => {
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            icon.classList.toggle('fa-eye');
            icon.classList.toggle('fa-eye-slash');
        });
    }

    setupTogglePassword('senha', 'toggleSenha');
    setupTogglePassword('confirmaSenha', 'toggleConfirmaSenha');

    // --- VALIDAÇÃO EM TEMPO REAL ---
    const senhaInput = document.getElementById('senha');
    const confirmaInput = document.getElementById('confirmaSenha');
    const ruleLength = document.getElementById('rule-length');
    const ruleMatch = document.getElementById('rule-match');

    function validarSenhas() {
        const s1 = senhaInput.value;
        const s2 = confirmaInput.value;

        // Regra tamanho
        if (s1.length >= 6) ruleLength.className = 'valid';
        else ruleLength.className = '';

        // Regra igualdade
        if (s1 && s1 === s2) ruleMatch.className = 'valid';
        else ruleMatch.className = '';
    }

    senhaInput.addEventListener('input', validarSenhas);
    confirmaInput.addEventListener('input', validarSenhas);

    // --- SUBMIT ---
    const form = document.getElementById("form-cad-doador");
    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const s1 = senhaInput.value;
        const s2 = confirmaInput.value;

        if (s1.length < 6) {
            alert("A senha deve ter no mínimo 6 caracteres.");
            return;
        }
        if (s1 !== s2) {
            alert("As senhas não coincidem.");
            return;
        }

        const doadorData = {
            nome: document.getElementById("nome").value,
            email: document.getElementById("email").value,
            senha: s1,
            cpfCnpj: document.getElementById("cpf").value,
            telefone: document.getElementById("telefone").value
        };

        try {
            await apiRegistrarDoador(doadorData);
            alert("Cadastro realizado com sucesso! Faça seu login.");
            window.location.href = "login-doador.html";
        } catch (error) {
            alert("Erro ao cadastrar: " + error.message);
        }
    });
});