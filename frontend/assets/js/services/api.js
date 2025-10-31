const API_BASE_URL = 'http://localhost:8080'; // base url do backend

export async function fazerLogin(login, senha) {
    console.log('API: Tentando fazer login para:', login);

    const LOGIN_URL = `${API_BASE_URL}/auth/login`; // url de login do backend

    const dados = { // dados a serem enviados pro backend
        login,
        senha
    };

    
    const resposta = await fetch(LOGIN_URL, { // a entrega com fetch, o async/await significa que vai esperar o ok da api
        method: 'POST', // 'POST' significa que esta enviando dados
        headers: {
            'Content-Type': 'application/json' // notifica o envio de um pacode JSON
        },
        body: JSON.stringify(dados) // converter o pacote JS pra JSON
    });

    if (!resposta.ok) { // verifica a resposta da api
        
        console.error('api: erro no login', resposta.status); // verifica se a resposta nao foi ok/valida e o erro ( 404, 401, 500)
        
        throw new Error('usuário e/ou senha invalidos.'); // avisa pro front que teve erro
    }

    console.log('api: login realizado'); // confirmacao de tudo certo caso a resposta seja ok
    
    return await resposta.json(); // o resposta.json() pega a resposta da api e joga pra enrico
}

export async function cadastrarItem(itemData) {
    console.log('API: Tentando cadastrar item:', itemData.nome);

    const CADASTRO_URL = `${API_BASE_URL}/item/cadastrar`; // url de cadastro de item

    const dados = itemData; // ja ta no formato certo

    const resposta = await fetch(CADASTRO_URL, {
        method: 'POST', // enviando dados
        headers: {
            'Content-Type': 'application/json' // notifica o envio de um pacote JSON
        },
        body: JSON.stringify(dados) // converter o pacote JS pra JSON
    });

    if (!resposta.ok) {
        console.error('api: erro no cadastro', resposta.status);
        throw new Error('falha ao cadastrar item.');
    }

    console.log('api: item cadastrado com sucesso');
    return await resposta.json();
}