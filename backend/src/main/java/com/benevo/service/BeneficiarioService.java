package com.benevo.service;

import com.benevo.model.Beneficiario;
import com.benevo.repository.BeneficiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.List;

@Service
public class BeneficiarioService {

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    public Beneficiario registrarBeneficiario(Beneficiario benef) {
        benef.setStatus("Aprovado");
        
        // LÓGICA DE ID FÁCIL (Ex: MAR-824)
        String prefixo = "USR";
        if (benef.getNome() != null && benef.getNome().length() >= 3) {
            prefixo = benef.getNome().substring(0, 3).toUpperCase();
            // Remove espaços se houver (ex: "A B" -> "AB")
            prefixo = prefixo.replaceAll("\\s+", "");
        }
        
        int numeroAleatorio = new Random().nextInt(900) + 100; // Gera entre 100 e 999
        String idFacil = prefixo + "-" + numeroAleatorio;

        benef.setIdBenevo(idFacil); 
        return beneficiarioRepository.save(benef);
    }

    public String verificarCredenciaisIniciais(String idBenevo, String infoSecundaria) {
        Optional<Beneficiario> benefOpt = beneficiarioRepository.findByIdBenevo(idBenevo);

        if (benefOpt.isEmpty()) return "FAIL";
        
        Beneficiario b = benefOpt.get();
        if (!"Aprovado".equalsIgnoreCase(b.getStatus())) return "PENDENTE";

        String info = infoSecundaria.trim().toLowerCase();
        String bairroReal = (b.getBairroCidade() != null) ? b.getBairroCidade().toLowerCase() : "";
        String nomeReal = b.getNome().toLowerCase();
        
        if (bairroReal.contains(info)) return "OK"; 
        if (nomeReal.equals(info)) return "OK";
        if (nomeReal.contains(info)) return "CHALLENGE";
    
        return "FAIL";
    }

    public Optional<Beneficiario> buscarPorIdBenevo(String idBenevo) {
        return beneficiarioRepository.findByIdBenevo(idBenevo);
    }

    public boolean validarRespostaSeguranca(String idBenevo, String resposta) {
        Optional<Beneficiario> benef = beneficiarioRepository.findByIdBenevo(idBenevo);
        if (benef.isEmpty()) return false;
        return benef.get().getRespostaSeguranca().equalsIgnoreCase(resposta.trim());
    }
    
    public List<Beneficiario> buscarPorDadosGerais(String termo) {
        return beneficiarioRepository.findByNomeContainingIgnoreCaseOrBairroCidadeContainingIgnoreCase(termo, termo);
    }
}