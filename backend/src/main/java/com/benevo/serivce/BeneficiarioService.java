package com.benevo.service;

import com.benevo.model.Beneficiario;
import com.benevo.repository.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BeneficiarioService {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    // REGISTRAR BENEFICIÁRIO
    public Beneficiario registrarBeneficiario(Beneficiario benef) {
        validarBeneficiario(benef);

        // Define o status inicial como "Pendente"
        benef.setStatus("Pendente");

        // Gera um idBenevo (pode usar CPF, se existir, ou gerar aleatório)
        if (benef.getCpf() != null && !benef.getCpf().trim().isEmpty()) {
            benef.setIdBenevo(benef.getCpf());
        } else {
            benef.setIdBenevo("BENEVO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        return beneficiarioRepository.save(benef);
    }

    // AUTENTICAR BENEFICIÁRIO (só se status = "Aprovado")
    public Beneficiario autenticarBeneficiario(String idBenevo) {
        Optional<Beneficiario> benefOpt = beneficiarioRepository.findByIdBenevo(idBenevo);

        if (benefOpt.isEmpty()) {
            throw new RuntimeException("ID Benevo não encontrado.");
        }

        Beneficiario benef = benefOpt.get();

        if (!"Aprovado".equalsIgnoreCase(benef.getStatus())) {
            throw new RuntimeException("O beneficiário ainda não foi aprovado. (RN004)");
        }

        return benef;
    }

    // AJUDA LOGIN (busca pelo nome)
    public List<Beneficiario> ajudaLogin(String termoBusca) {
        System.out.println("Service: Buscando beneficiário por nome contendo: " + termoBusca);
        return beneficiarioRepository.findByNomeCompletoContainingIgnoreCase(termoBusca);
    }

    // LISTAR BENEFICIÁRIOS PENDENTES
    public List<Beneficiario> listarPendentes() {
        return beneficiarioRepository.findByStatus("Pendente");
    }

    // APROVAR BENEFICIÁRIO
    public Beneficiario aprovarBeneficiario(String id) {
        Optional<Beneficiario> benefOpt = beneficiarioRepository.findById(id);

        if (benefOpt.isEmpty()) {
            throw new RuntimeException("Beneficiário com ID " + id + " não encontrado.");
        }

        Beneficiario benef = benefOpt.get();

        if (!"Pendente".equalsIgnoreCase(benef.getStatus())) {
            throw new RuntimeException("Apenas beneficiários pendentes podem ser aprovados. (RN005)");
        }

        benef.setStatus("Aprovado");
        return beneficiarioRepository.save(benef);
    }

    // Validação básica de dados obrigatórios
    private void validarBeneficiario(Beneficiario benef) {
        if (isNullOrEmpty(benef.getNomeCompleto()))
            throw new IllegalArgumentException("O nome completo é obrigatório.");

        if (isNullOrEmpty(benef.getEmail()))
            throw new IllegalArgumentException("O e-mail é obrigatório.");

        if (isNullOrEmpty(benef.getTelefone()))
            throw new IllegalArgumentException("O telefone é obrigatório.");

        // O CPF pode ser opcional se gerar ID aleatório
    }

    private boolean isNullOrEmpty(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
