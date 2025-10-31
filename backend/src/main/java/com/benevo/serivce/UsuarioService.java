package com.benevo.serivce;

import com.benevo.model.Usuario; // o molde de usuario q peu fez
import com.benevo.repository.UsuarioRepository; // o acesso pro banco q peu criou
import org.springframework.beans.factory.annotation.Autowired; // o spring pra conectar
import org.springframework.stereotype.Service; // a etiqueta de servico

import java.util.Optional;

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
        // Pede ao repo de peu pra buscar o usuario no banco pelo login
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);

        // verifica oq peu retornou
        if (usuarioOpt.isPresent()) {
            Usuario usuarioDoBanco = usuarioOpt.get();
            // se achou um usuario verifica a senha com checagem simples
            if (usuarioDoBanco.getSenha().equals(senha)) {
                // a senha bateu e pode ser autenticado
                return true;
            } else {
                // achou o usuario mas senha errada
                return false;
            }
        }

        // se o usuarioOpt for vazio, nao achou ngm do banco
        return false;
    }

    // a partir daqui segue com cadastrar usuario, etc com a mesma logica
}