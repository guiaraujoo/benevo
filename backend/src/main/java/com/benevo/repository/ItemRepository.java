package com.benevo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.benevo.model.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}
