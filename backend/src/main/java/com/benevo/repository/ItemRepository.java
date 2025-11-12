package com.benevo.repository;

import com.benevo.model.Item;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    // 🔍 Busca por nome OU categoria (ignora maiúsculas/minúsculas) e permite ordenar
    List<Item> findByNomeContainingIgnoreCaseOrCategoriaContainingIgnoreCase(String nome, String categoria, Sort sort);
}
