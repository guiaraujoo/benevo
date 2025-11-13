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

                    // --- NOVO: Armazena o token e tipo de usuário ---
                    if (data.token) {
                        // Verifica o tipo de usuário retornado pelo backend
                        if (data.tipo === 'doador') {
                            localStorage.setItem('token_doador', data.token);
                        } else if (data.tipo === 'funcionario') {
                            localStorage.setItem('token_funcionario', data.token);
                        } else {
                            console.warn('Tipo de usuário não reconhecido:', data.tipo);
                        }
                    } else {
                        console.warn('Nenhum token retornado pelo backend.');
                    }

                    // --- NOVO: Redireciona para o dashboard ---
                    window.location.href = 'dashboard-admin.html'; 
                })
                .catch(error => {
                    console.error('Erro no login:', error);
                    alert('Login ou senha inválidos.');
                });
        });
    }
});
