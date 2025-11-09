// benevo-project/frontend/assets/js/services/api.js

// URL base da API      
const API_BASE_URL = "http://localhost:8080";

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
export async function deletarItem(id) {

    const response = await fetch(`${API_BASE_URL}/itens/excluir/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token_benevo")}`
        }
    });

    if (!response.ok) {
        const erro = await response.text();
        alert("Erro ao excluir: " + erro);
        throw new Error(erro);
    }

    alert("Item excluído com sucesso!");
}

// ================================
// EDITAR ITEM (PUT)
// ================================
export async function editarItem(id, itemData) {

    const response = await fetch(`${API_BASE_URL}/itens/editar/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token_benevo")}`
        },
        body: JSON.stringify(itemData)
    });

    if (!response.ok) {
        const erro = await response.text();
        alert("Erro ao editar: " + erro);
        throw new Error(erro);
    }

    alert("Item atualizado com sucesso!");
    return response.json();
}