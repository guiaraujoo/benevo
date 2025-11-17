import { apiLoginDoador } from '../services/api.js';

document.addEventListener("DOMContentLoaded", function () {
    
    const formLogin = document.getElementById('form-login-doador');

    if (formLogin) {
        formLogin.addEventListener('submit', function (event) {
            
            event.preventDefault();

            const email = document.getElementById('login-doador').value;
            const senha = document.getElementById('senha-doador').value;

            if (!email || !senha) {
                alert('Email e senha são obrigatórios.');
                return;
            }

            apiLoginDoador(email, senha)
                .then(data => {
                    
                    alert('Login de Doador bem-sucedido! Bem-vindo, ' + data.nome);

                    localStorage.setItem('token_benevo', data.token);
                    localStorage.setItem('user_tipo', data.tipo);
                    localStorage.setItem('user_nome', data.nome);
                    localStorage.setItem('user_id', data.id);

                    window.location.href = 'cadastro-item.html'; 
                })
                .catch(error => {
                    console.error('Erro no login do doador:', error);
                    alert('Email ou senha inválidos.');
                });
        });
    }
});