package com.demo.tiny_url.repository;

import com.demo.tiny_url.entity.AliasDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AliasDetailsRepository extends CrudRepository<AliasDetails, String> {
    Optional<AliasDetails> findById(String id);
    AliasDetails save(AliasDetails aliasDetails);
}
