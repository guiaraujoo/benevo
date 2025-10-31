package com.benevo.controller;

import com.benevo.dto.LoginRequestDTO;
import com.benevo.serivce.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // define pro spring que o que vc ta fazendo é uma api
@RequestMapping("/auth") // todas as url daqui vao iniciar com /auth
@CrossOrigin("*") // libera o CORS, sem isso o navegador de dheff vai bloquear a chamada dele pra tua api
public class AuthController {

    // aqui é onde chama o que lingui fez, o service
    // o @Autowired é como o spring sconectas o service aqui dentro pra vc usar
    @Autowired
    private UsuarioService usuarioService; // confirma o nome da classe com lingui

    
    // aqui é a primeira porta na url /auth/login
    // o @PostMapping significa que ela aceita somente requisicoes do tipo POST.
    @PostMapping("/login")
    public ResponseEntity<String> fazerLogin(@RequestBody LoginRequestDTO loginDTO) {
        
        // @RequestBody pega o JSON que dheff enviou e transforma no molde de login q tu criou

        System.out.println("tentativa de login para: " + loginDTO.getLogin()); // é importante dar esses prints pra dar debug e saber se a variavel ta sendo armazenada no login dto

        // tu precisa chamar o metodo de lingui pra verificar se o login e a senha que tu puxou com os getters sao validos
        
        // ficaria tipo assim, tu tem que melhorar dps chamando os metodos de lingui
        boolean loginValido = usuarioService.autenticar(loginDTO.getLogin(), loginDTO.getSenha()); // o metodo que lingui criou vai retornar se é valido e tu armazena num boolean (0/false pra invalido e 1/true pra valido)

        if (loginValido) {
            // se o login deu valido retorna ok pra dheff
            return ResponseEntity.ok("login realizado");
        } else {
            // se deu invalido retorna pra dheff com status 401 - nao autorizado. da uma pesquisada sobre os numeros dos status tbm
            return ResponseEntity.status(401).body("login e/ou senha inválidos");
        }
    }
}
