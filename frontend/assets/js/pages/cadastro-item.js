import { cadastrarItem } from '../services/api.js';

document.addEventListener("DOMContentLoaded", function () {
    
    // Lógica do Botão Sair
    document.getElementById("btn-logout").addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "index.html";
    });

    const formDoacao = document.getElementById('form-doacao');

    if (formDoacao) {
        formDoacao.addEventListener('submit', function (event) {
            event.preventDefault(); 

            const token = localStorage.getItem("token_benevo");
            const doadorId = localStorage.getItem('user_id');

            if (!token || !doadorId) {
                alert('Erro: Você precisa estar logado.');
                window.location.href = "login-doador.html";
                return;
            }

            const itemData = {
                nome: document.getElementById('nome').value,
                categoria: document.getElementById('categoria').value,
                condicao: document.getElementById('condicao').value,
                tamanho: document.getElementById('tamanho').value,
                descricao: document.getElementById('descricao').value, 
                doadorId: doadorId
            };

            cadastrarItem(itemData)
                .then(data => {
                    // SUCESSO: Alerta e Logout Automático
                    alert(`Item "${data.nome}" cadastrado com sucesso!\nObrigado pela sua doação.\nVocê será deslogado agora.`);
                    
                    localStorage.clear();
                    window.location.replace("login-doador.html"); // Replace para evitar voltar
                })
                .catch(error => {
                    console.error(error);
                    alert('Erro ao cadastrar item: ' + error.message);
                });
        });
    }
});