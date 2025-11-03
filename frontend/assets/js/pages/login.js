// IMPORTANTE: Adicione esta linha no topo do arquivo.
import { fazerLogin } from '../services/api.js';

document.addEventListener("DOMContentLoaded", function () {
    
    const formLogin = document.getElementById('form-login');

    if (formLogin) {
        formLogin.addEventListener('submit', function (event) {
            event.preventDefault();

            const login = document.getElementById('login').value;
            const senha = document.getElementById('senha').value;

            if (!login || !senha) {
                alert('Login e senha são obrigatórios.');
                return;
            }

            fazerLogin(login, senha)
                .then(data => {
                    // Supondo que o backend retorna o usuário ou um token
                    alert('Login bem-sucedido!');
                    console.log('Resposta do login:', data);
                    // Aqui você redirecionaria o usuário
                    // window.location.href = 'dashboard.html'; 
                })
                .catch(error => {
                    console.error('Erro no login:', error);
                    alert('Login ou senha inválidos.');
                });
        });
    }
});