// package com.benevo.config;

// import com.benevo.model.Beneficiario;
// import com.benevo.model.Doador;
// import com.benevo.model.Usuario;
// import com.benevo.repository.BeneficiarioRepository;
// import com.benevo.repository.DoadorRepository;
// import com.benevo.repository.UsuarioRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
// import java.util.List;

// @Component
// public class DataSeeder implements CommandLineRunner {

//     @Autowired private UsuarioRepository usuarioRepository;
//     @Autowired private DoadorRepository doadorRepository;
//     @Autowired private BeneficiarioRepository beneficiarioRepository;
//     @Autowired private PasswordEncoder passwordEncoder;

//     @Override
//     public void run(String... args) throws Exception {
//         // Limpar bases para evitar conflito de IDs antigos
//         beneficiarioRepository.deleteAll(); 

//         if (usuarioRepository.findByLogin("admin").isEmpty()) {
//             usuarioRepository.save(new Usuario(null, "admin", "123", "ADMIN"));
//         }

//         if (doadorRepository.findByEmail("doador@teste.com").isEmpty()) {
//             doadorRepository.save(new Doador("Doador Teste", "000", "999", "doador@teste.com", "123"));
//         }

//         // Criar Beneficiário no NOVO MODELO
//         Beneficiario b1 = new Beneficiario(
//             "José Carlos da Silva",     // Nome
//             null,                       // Email (Opcional)
//             null,                       // Telefone (Opcional)
//             "Recife",                   // Bairro/Cidade (Chave de login fácil)
//             "Qual o nome do meu cachorro?", // Pergunta Customizada
//             "Rex",                      // Resposta
//             List.of("Roupas", "Comida")
//         );
//         b1.setIdBenevo("JOSE-123");
//         b1.setStatus("Aprovado");
//         beneficiarioRepository.save(b1);
        
//         System.out.println(">>> DATA SEEDER: Beneficiário criado. ID: JOSE-123 | Senha(Bairro): Recife | Pergunta: Rex");
//     }
// }