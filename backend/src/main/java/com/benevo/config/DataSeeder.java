package com.benevo.config;

import com.benevo.model.Beneficiario;
import com.benevo.model.Doador;
import com.benevo.model.Usuario;
import com.benevo.repository.BeneficiarioRepository;
import com.benevo.repository.DoadorRepository;
import com.benevo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DoadorRepository doadorRepository;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. Criar Usuário ADMIN (para dashboard-admin.html)
        if (usuarioRepository.findByLogin("admin").isEmpty()) {
            Usuario admin = new Usuario(null, "admin", "123", "ADMIN");
            usuarioRepository.save(admin);
            System.out.println(">>> USUÁRIO ADMIN CRIADO: admin / 123");
        }

        // 2. Criar Usuário DOADOR (para cadastro-item.html)
        if (doadorRepository.findByEmail("doador@teste.com").isEmpty()) {
            Doador doador = new Doador(
                "Doador de Teste",
                "111.111.111-11",
                "99999-9999",
                "doador@teste.com",
                "123"
            );
            doadorRepository.save(doador);
            System.out.println(">>> USUÁRIO DOADOR CRIADO: doador@teste.com / 123");
        }

        // 3. Criar Usuário BENEFICIÁRIO (para painel-resgate.html)
        // (Status "Aprovado" para pular a RN006 e testar o login direto)
        if (beneficiarioRepository.findByCpf("222.222.222-22").isEmpty()) {
            Beneficiario benef = new Beneficiario(
                "Beneficiário de Teste",
                "222.222.222-22",
                "Rua da Esperança, 123",
                List.of("Vestimenta", "Brinquedo"),
                "BENE-TESTE-123", // ID Benevo
                "Aprovado"         // Status
            );
            beneficiarioRepository.save(benef);
            System.out.println(">>> USUÁRIO BENEFICIÁRIO (APROVADO) CRIADO: ID BENE-TESTE-123");
        }
    }
}