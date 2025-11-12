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

    // Registrar novo doador
    public Doador registrarDoador(Doador doador) {
        if (doador.getEmail() == null || doador.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }

        if (doadorRepository.findByEmail(doador.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um doador com este e-mail.");
        }

        return doadorRepository.save(doador);
    }

    // Autenticar doador
    public Optional<Doador> autenticarDoador(String email, String senha) {
        Optional<Doador> doadorOpt = doadorRepository.findByEmail(email);

        if (doadorOpt.isPresent() && doadorOpt.get().getSenha().equals(senha)) {
            return doadorOpt;
        }

        return Optional.empty();
    }
}
