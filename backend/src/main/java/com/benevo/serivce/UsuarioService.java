package com.benevo.serivce;

import com.benevo.model.Usuario; // o molde de usuario q peu fez
import com.benevo.repository.UsuarioRepository; // o acesso pro banco q peu criou
import org.springframework.beans.factory.annotation.Autowired; // o spring pra conectar
import org.springframework.stereotype.Service; // a etiqueta de servico

// a etiqueta @Service diz pro spring que essa classe cuida das RN
@Service
public class UsuarioService {

    // @Autowired é como pedir ao spring, o spring injeta o repositorio de usuario de peu aqu sem precisar de new UsuarioRepository()
    @Autowired
    private UsuarioRepository usuarioRepository;

    // essa é a função q ita vai chamar pra login
    // @param login - o login que veio do formulario do frontend
    // @param senha - a senha que veio do formulario do frontend
    // @return true se o login for valido, false se não for

    public boolean autenticar(String login, String senha) {

        // pede ao repo de peu pra buscar o usuario no banco pelo login, pergunta a ele o comando certo
        Usuario usuarioDoBanco = usuarioRepository.UsuarioRepository(login);

        // verifica oq peu retornou
        if (usuarioDoBanco != null) {
            // se achou um usuario verifica a senha com checagem simples, dps usa criptografia
            if (usuarioDoBanco.getSenha().equals(senha)) {
                // a senha bateu e pode ser autenticado
                return true;
            } else {
                // achou o usuario mas senha errada
                return false;
            }
        }

        // se o usuario for nulo, nao achou ngm do banco
        return false;

    }

    // a partir daqui segue com cadastrar usuario, etc com a mesma logica
}