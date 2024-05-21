package com.demo.tiny_url.repository;

import com.demo.tiny_url.entity.AliasDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AliasDetailsRepository extends CrudRepository<String, AliasDetails> {
    Optional<AliasDetails> findById(String id);
    void save(AliasDetails aliasDetails);
}
