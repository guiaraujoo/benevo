// Pega o formulário pelo ID
const formLogin = document.getElementById("form-login");

// Adiciona o ouvinte de envio
formLogin.addEventListener("submit", (event) => {
  event.preventDefault(); // Impede o envio automático

  // Pega os valores dos campos
  const login = document.getElementById("login").value.trim();
  const senha = document.getElementById("senha").value.trim();

  // Valida se os campos estão vazios
  if (login === "" || senha === "") {
    alert("Preencha todos os campos!");
    return; // Interrompe o código aqui
  }

  // Se estiver tudo certo, chama a função de Dheff
  fazerLogin(login, senha);
});
