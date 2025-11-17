package com.benevo.repository;

import com.benevo.model.Beneficiario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficiarioRepository extends MongoRepository<Beneficiario, String> {
    Optional<Beneficiario> findByIdBenevo(String idBenevo);
    List<Beneficiario> findByStatus(String status);
    List<Beneficiario> findByNomeCompletoContainingIgnoreCase(String nome);
}