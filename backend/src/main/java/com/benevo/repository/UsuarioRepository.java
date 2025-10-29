package com.benevo.repository;

import com.benevo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import com.benevo.model.Item;

public interface UsuarioRepository extends JpaRepository<Item, Integer> {
    Usuario UsuarioRepository(String login);
}

