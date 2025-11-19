// benevo-project/frontend/assets/js/services/api.js

// URL base da API      
const API_BASE_URL = "http://localhost:8080";

// ============================================
// FUNÇÕES DE AUTENTICAÇÃO (token e headers)
// ============================================

function getAuthToken() {
  return localStorage.getItem("token_benevo");
}

function getAuthHeaders() {
  const token = getAuthToken();
  return {
    "Content-Type": "application/json",
    "Authorization": "Bearer " + token
  };
}

// =====================================
// LOGIN
// =====================================
export async function fazerLogin(login, senha) {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ login, senha }),
    });

    if (!response.ok) {
      throw new Error("Erro ao fazer login");
    }

    const data = await response.json();
    localStorage.setItem("token", data.token);
    console.log("Login bem-sucedido:", data);
    return data;
  } catch (error) {
    console.error("Erro no login:", error);
    throw error;
  }
}

// =====================================
// CADASTRAR ITEM
// =====================================
export async function cadastrarItem(itemData) {
  try {
    const response = await fetch(`${API_BASE_URL}/itens`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        // Se o backend usar token:
        // "Authorization": `Bearer ${localStorage.getItem("token")}`,
      },
      body: JSON.stringify(itemData),
    });

    if (!response.ok) {
      throw new Error("Erro ao cadastrar item");
    }

    const data = await response.json();
    console.log("Item cadastrado com sucesso:", data);
    return data;
  } catch (error) {
    console.error("Erro ao cadastrar item:", error);
    throw error;
  }
}

// ================================
// BUSCAR ITENS COM FILTROS (GET)
// ================================
export async function getItemsComFiltros({ categoria, condicao }) {

    const queryParams = new URLSearchParams({
        categoria,
        condicao
    }).toString();

    const response = await fetch(`${API_BASE_URL}/itens?${queryParams}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token_benevo")}` // <-- ATENÇÃO AQUI
        }
    });

    if (!response.ok) {
        throw new Error("Erro ao buscar itens");
    }

    const data = await response.json();
    console.log("Itens filtrados:", data);
    return data;
}

// ================================
// DELETAR ITEM (DELETE)
// ================================

export async function apiDeletarItem(id) {
  try {
    const response = await fetch(`${API_BASE_URL}/item/deletar/${id}`, {
      method: "DELETE",
      headers: getAuthHeaders(),
    });

    if (!response.ok) throw new Error("Erro ao deletar item");

    console.log("Item deletado com sucesso!");
  } catch (error) {
    console.error("Erro ao deletar item:", error);
    throw error;
  }
}

// ================================
// EDITAR ITEM (PUT)
// ================================

export async function apiEditarItem(id, itemData) {
  try {
    const response = await fetch(`${API_BASE_URL}/item/atualizar/${id}`, {
      method: "PUT",
      headers: getAuthHeaders(),
      body: JSON.stringify(itemData),
    });

    if (!response.ok) throw new Error("Erro ao editar item");

    const data = await response.json();
    console.log("Item atualizado:", data);
    return data;
  } catch (error) {
    console.error("Erro ao editar item:", error);
    throw error;
  }
}

// ================================
// REGISTRAR BENEFICIÁRIO (POST)
// ================================

export async function apiRegistrarBeneficiario(benefData) {
  try {
    const response = await fetch(`${API_BASE_URL}/beneficiarios/registrar`, {
      method: "POST",
      headers: getAuthHeaders(),
      body: JSON.stringify(benefData),
    });

    if (!response.ok) throw new Error("Erro ao registrar beneficiário");

    return await response.json();
  } catch (error) {
    console.error("Erro ao registrar beneficiário:", error);
    throw error;
  }
}



// ================================
//  RESGATAR ITEM (PUT)
// ================================

export async function apiResgatarItem(id, dados) {
  try {
    const response = await fetch(`${API_BASE_URL}/item/reservar/${id}`, {
      method: "PUT",
      headers: getAuthHeaders(),
      body: JSON.stringify(dados),
    });

    if (!response.ok) throw new Error("Erro ao reservar item");

    return await response.json();
  } catch (error) {
    console.error("Erro ao reservar item:", error);
    throw error;
  }
}



// ================================
// RECUPERAR ID (POST)
// (envia resposta de segurança e recebe ID de volta)
// ================================

export async function apiRecuperarId(dados) {
  try {
    const response = await fetch(`${API_BASE_URL}/beneficiarios/recuperar-id`, {
      method: "POST",
      headers: getAuthHeaders(),
      body: JSON.stringify(dados),
    });

    if (!response.ok) throw new Error("Erro ao recuperar ID");

    return await response.json();
  } catch (error) {
    console.error("Erro ao recuperar ID:", error);
    throw error;
  }
}




// ================================
// SPRINT 3 - DOADOR (FLUXO 1)
// ================================

export async function apiRegistrarDoador(doadorData) {
  const response = await fetch(`${API_BASE_URL}/doadores/registrar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(doadorData),
  });
  if (!response.ok) throw new Error("Erro ao registrar doador");
  return await response.json();
}

export async function apiLoginDoador(email, senha) {
  const response = await fetch(`${API_BASE_URL}/doadores/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, senha }),
  });
  if (!response.ok) throw new Error("Erro no login do doador");
  return await response.json();
}

// ================================
// SPRINT 3 - BENEFICIÁRIO (FLUXO 3)
// ================================

export async function apiRegistrarBeneficiario(benefData) {
  const response = await fetch(`${API_BASE_URL}/beneficiarios/registrar`, {
    method: "POST",
    headers: getAuthHeaders(),
    body: JSON.stringify(benefData),
  });
  if (!response.ok) throw new Error("Erro ao registrar beneficiário");
  return await response.json();
}

export async function apiLoginBeneficiario(idBenevo) {
  const response = await fetch(`${API_BASE_URL}/beneficiarios/login`, {
    method: "POST",
    headers: getAuthHeaders(),
    body: JSON.stringify({ idBenevo }),
  });
  if (!response.ok) throw new Error("Erro no login do beneficiário");
  return await response.json();
}

export async function apiAjudarLogin(termoBusca) {
  const response = await fetch(
    `${API_BASE_URL}/beneficiarios/ajuda-login?busca=${termoBusca}`,
    {
      method: "GET",
      headers: getAuthHeaders(),
    }
  );
  if (!response.ok) throw new Error("Erro ao buscar ajuda de login");
  return await response.json();
}

// ================================
// SPRINT 3 - RESGATE E APROVAÇÃO
// ================================

export async function apiReservarItem(id) {
  const response = await fetch(`${API_BASE_URL}/item/reservar/${id}`, {
    method: "PUT",
    headers: getAuthHeaders(),
  });
  if (!response.ok) throw new Error("Erro ao reservar item");
  return await response.json();
}

export async function apiListarPendentes() {
  const response = await fetch(`${API_BASE_URL}/beneficiarios/pendentes`, {
    method: "GET",
    headers: getAuthHeaders(),
  });
  if (!response.ok) throw new Error("Erro ao listar pendentes");
  return await response.json();
}

export async function apiAprovarBeneficiario(id) {
  const response = await fetch(
    `${API_BASE_URL}/beneficiarios/aprovar/${id}`,
    {
      method: "PUT",
      headers: getAuthHeaders(),
    }
  );
  if (!response.ok) throw new Error("Erro ao aprovar beneficiário");
  return await response.json();
}


