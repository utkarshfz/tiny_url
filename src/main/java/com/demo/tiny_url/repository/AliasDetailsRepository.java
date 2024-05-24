package com.demo.tiny_url.repository;

import com.demo.tiny_url.entity.AliasDetails;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AliasDetailsRepository extends CrudRepository<AliasDetails, String> {
    Optional<AliasDetails> findById(String id);

    @Query(value = "INSERT INTO alias_details (alias, long_url, expires_at) VALUES(:alias, :longUrl, :expiresAt)", nativeQuery = true)
    void insert(@Param("alias") String alias, @Param("longUrl") String longUrl, @Param("expiresAt")LocalDateTime expiresAt);
}
