package com.benevo.service;

import com.benevo.model.Beneficiario;
import com.benevo.repository.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BeneficiarioService {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    // Registrar novo beneficiário
    public Beneficiario registrarBeneficiario(Beneficiario benef) {
        benef.setStatus("Pendente");

        // Gera idBenevo (pode usar o CPF, ou um UUID)
        if (benef.getCpf() != null && !benef.getCpf().isEmpty()) {
            benef.setIdBenevo(benef.getCpf());
        } else {
            benef.setIdBenevo(UUID.randomUUID().toString());
        }

        return beneficiarioRepository.save(benef);
    }

    // Autenticar beneficiário (só se aprovado)
    public Optional<Beneficiario> autenticarBeneficiario(String idBenevo) {
        Optional<Beneficiario> benef = beneficiarioRepository.findByIdBenevo(idBenevo);

        if (benef.isPresent() && "Aprovado".equalsIgnoreCase(benef.get().getStatus())) {
            return benef;
        }

        return Optional.empty();
    }

    // Ajuda de login — busca por nome, cpf ou email
    public Optional<Beneficiario> ajudaLogin(String termoBusca) {
        Optional<Beneficiario> porCpf = beneficiarioRepository.findByCpf(termoBusca);
        if (porCpf.isPresent()) return porCpf;

        // Você pode adicionar buscas por nome ou email se quiser:
        return Optional.empty();
    }
}

