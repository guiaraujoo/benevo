package com.benevo.service;

import com.benevo.model.Usuario; // o molde de usuario q peu fez
import com.benevo.repository.UsuarioRepository; // o acesso pro banco q peu criou
import org.springframework.beans.factory.annotation.Autowired; // o spring pra conectar
import org.springframework.stereotype.Service; // a etiqueta de servico

import java.util.Optional;

// a etiqueta @Service diz pro spring que essa classe cuida das RN
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    // MUDA DE 'boolean' PARA 'Optional<Usuario>'
    public Optional<Usuario> autenticar(String login, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);

        if (usuarioOpt.isPresent()) {
            Usuario usuarioDoBanco = usuarioOpt.get();
            if (usuarioDoBanco.getSenha().equals(senha)) {
                return usuarioOpt; // RETORNA O USUÁRIO INTEIRO
            }
        }
        return Optional.empty(); // Senha errada OU usuário não existe
    }
}