package com.demo.tiny_url.repository;

import com.demo.tiny_url.entity.ShortUrlDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortUrlDetailsRepository extends CrudRepository<String, ShortUrlDetails> {
    Optional<ShortUrlDetails> findById(String id);
    void save(ShortUrlDetails shortUrlDetails);
}
