package com.benevo.controller;

import com.benevo.model.Beneficiario;
import com.benevo.service.BeneficiarioService;
import com.benevo.dto.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/beneficiarios")
@CrossOrigin("*")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioService service;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Beneficiario b) {
        return ResponseEntity.ok(service.registrarBeneficiario(b));
    }

    // LOGIN - PASSO 1
    @PostMapping("/validar-acesso")
    public ResponseEntity<?> validarAcesso(@RequestBody Map<String, String> payload) {
        String idBenevo = payload.get("idBenevo");
        String info = payload.get("info"); 

        String resultado = service.verificarCredenciaisIniciais(idBenevo, info);
        Optional<Beneficiario> benefOpt = service.buscarPorIdBenevo(idBenevo);

        if (benefOpt.isEmpty()) return ResponseEntity.status(404).body("ID não encontrado");
        Beneficiario b = benefOpt.get();

        if (resultado.equals("OK")) {
            // CORREÇÃO: Adicionado b.getIdBenevo() no final
            return ResponseEntity.ok(new LoginResponseDTO(
                b.getId(), 
                "token_mock_" + b.getId(), 
                "BENEFICIARIO", 
                b.getNome(),
                b.getIdBenevo() 
            ));
        } else if (resultado.equals("CHALLENGE")) {
            return ResponseEntity.status(202).body(Map.of(
                "status", "CHALLENGE_REQUIRED",
                "nomeCompleto", b.getNome(),
                "perguntaSeguranca", b.getPerguntaSeguranca()
            ));
        } else if (resultado.equals("PENDENTE")) {
             return ResponseEntity.status(401).body("Cadastro ainda em análise.");
        } else {
            return ResponseEntity.status(401).body("Inconsistência nos dados informados.");
        }
    }

    // LOGIN - PASSO 2
    @PostMapping("/responder-desafio")
    public ResponseEntity<?> responderDesafio(@RequestBody Map<String, String> payload) {
        String idBenevo = payload.get("idBenevo");
        String resposta = payload.get("resposta");

        if (service.validarRespostaSeguranca(idBenevo, resposta)) {
            Beneficiario b = service.buscarPorIdBenevo(idBenevo).get();
            // CORREÇÃO: Adicionado b.getIdBenevo()
            return ResponseEntity.ok(new LoginResponseDTO(
                b.getId(), 
                "token_mock_" + b.getId(), 
                "BENEFICIARIO", 
                b.getNome(),
                b.getIdBenevo()
            ));
        }
        return ResponseEntity.status(401).body("Resposta incorreta.");
    }
    
    @GetMapping("/ajuda-login")
    public ResponseEntity<List<Map<String, String>>> buscarAjuda(@RequestParam String busca) {
        List<Beneficiario> encontrados = service.buscarPorDadosGerais(busca);
        List<Map<String, String>> resposta = encontrados.stream()
            .filter(b -> "Aprovado".equalsIgnoreCase(b.getStatus()))
            .map(b -> Map.of(
                "idInterno", b.getIdBenevo(),
                "nome", b.getNome(),
                "bairro", b.getBairroCidade(),
                "pergunta", b.getPerguntaSeguranca()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }
    
    @GetMapping("/pendentes")
    public List<Beneficiario> listarPendentes() {
        return service.buscarPorDadosGerais("").stream().filter(b -> "Pendente".equals(b.getStatus())).toList();
    }
    
    @PutMapping("/aprovar/{id}")
    public ResponseEntity<?> aprovar(@PathVariable String id) {
         return ResponseEntity.ok("Aprovado");
    }
}