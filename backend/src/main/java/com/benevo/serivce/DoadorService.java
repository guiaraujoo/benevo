package com.benevo.service;

import com.benevo.model.Doador;
import com.benevo.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoadorService {

    @Autowired
    private DoadorRepository doadorRepository;

    // REGISTRAR DOADOR
    public Doador registrarDoador(Doador doador) {
        validarDoador(doador);

        // Verifica se já existe um doador com o mesmo e-mail
        Optional<Doador> existente = doadorRepository.findByEmail(doador.getEmail());
        if (existente.isPresent()) {
            throw new RuntimeException("Já existe um doador cadastrado com este e-mail.");
        }

        return doadorRepository.save(doador);
    }

    // AUTENTICAR DOADOR
    public Doador autenticarDoador(String email, String senha) {
        Optional<Doador> doadorOpt = doadorRepository.findByEmail(email);

        if (doadorOpt.isEmpty()) {
            throw new RuntimeException("E-mail não encontrado.");
        }

        Doador doador = doadorOpt.get();

        if (!doador.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta.");
        }

        return doador;
    }

    // Validação básica de campos obrigatórios
    private void validarDoador(Doador doador) {
        if (isNullOrEmpty(doador.getNome()))
            throw new IllegalArgumentException("O nome do doador é obrigatório.");

        if (isNullOrEmpty(doador.getEmail()))
            throw new IllegalArgumentException("O e-mail é obrigatório.");

        if (isNullOrEmpty(doador.getSenha()))
            throw new IllegalArgumentException("A senha é obrigatória.");
    }

    private boolean isNullOrEmpty(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
