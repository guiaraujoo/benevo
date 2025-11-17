package com.benevo.controller;

import com.benevo.model.Beneficiario;
import com.benevo.service.BeneficiarioService;
import com.benevo.repository.BeneficiarioRepository;
import com.benevo.dto.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beneficiarios")
@CrossOrigin("*")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioService beneficiarioService;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository; 

    // Endpoint para registrar (via admin)
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarBeneficiario(@RequestBody Beneficiario beneficiario) {
        Beneficiario novo = beneficiarioService.registrarBeneficiario(beneficiario);
        return ResponseEntity.ok(novo);
    }

    // Endpoint para login do beneficiário (pelo ID Benevo)
    @PostMapping("/login")
    public ResponseEntity<?> loginBeneficiario(@RequestBody Beneficiario loginRequest) {
        Optional<Beneficiario> benefOpt = beneficiarioService.autenticarBeneficiario(
            loginRequest.getIdBenevo()
        );

        if (benefOpt.isPresent()) {
            Beneficiario benef = benefOpt.get();
            LoginResponseDTO resposta = new LoginResponseDTO(
                benef.getId(),
                "token_benef_mock_" + benef.getId(),
                "BENEFICIARIO",
                benef.getNomeCompleto()
            );
            return ResponseEntity.ok(resposta);
        } else {
            return ResponseEntity.status(401).body("ID Benevo inválido ou cadastro ainda não aprovado.");
        }
    }
    
    // Endpoint para Ajuda de Login
    @GetMapping("/ajuda-login")
    public ResponseEntity<?> ajudaLogin(@RequestParam String busca) {
        Optional<Beneficiario> benefOpt = beneficiarioService.ajudaLogin(busca);
         if (benefOpt.isPresent()) {
            return ResponseEntity.ok(benefOpt.get());
        } else {
            return ResponseEntity.status(404).body("Nenhum beneficiário encontrado com esse CPF.");
        }
    }

    // Endpoint para listar beneficiários "Pendentes"
    @GetMapping("/pendentes")
    public ResponseEntity<List<Beneficiario>> getPendentes() {
        List<Beneficiario> pendentes = beneficiarioRepository.findByStatus("Pendente");
        return ResponseEntity.ok(pendentes);
    }

    // Endpoint para Aprovar um beneficiário
    @PutMapping("/aprovar/{id}")
    public ResponseEntity<?> aprovarBeneficiario(@PathVariable String id) {
        Optional<Beneficiario> benefOpt = beneficiarioRepository.findById(id);
        if (benefOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Beneficiário não encontrado.");
        }
        Beneficiario b = benefOpt.get();
        b.setStatus("Aprovado");
        beneficiarioRepository.save(b);
        return ResponseEntity.ok(b);
    }
}