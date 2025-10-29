package com.benevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.benevo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}

