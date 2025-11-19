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

        // Gera idBenevo (pode usar o CPF ou um UUID)
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

        // Aqui você pode adicionar mais buscas (nome, email, etc)
        return Optional.empty();
    }

    // Recuperação de ID — usando pergunta e resposta de segurança
    public String recuperarId(String nome, String pergunta, String resposta) {
        // Buscar beneficiário pelo nome
        Beneficiario benef = beneficiarioRepository.findByNome(nome);

        if (benef == null) {
            throw new RuntimeException("Beneficiário não encontrado.");
        }

        // Validar pergunta e resposta (ignorando maiúsculas/minúsculas)
        boolean perguntaOk = benef.getPerguntaSeguranca().equalsIgnoreCase(pergunta);
        boolean respostaOk = benef.getRespostaSeguranca().equalsIgnoreCase(resposta);

        if (!perguntaOk || !respostaOk) {
            throw new RuntimeException("Pergunta ou resposta de segurança incorretas.");
        }

        // Sucesso: retornar idBenevo
        return benef.getIdBenevo();
    }
}
