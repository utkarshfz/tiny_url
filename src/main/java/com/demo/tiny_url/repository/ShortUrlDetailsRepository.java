package com.demo.tiny_url.repository;

import com.demo.tiny_url.entity.ShortUrlDetails;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ShortUrlDetailsRepository extends CrudRepository<ShortUrlDetails, String> {

    @Override
    Optional<ShortUrlDetails> findById(String id);

    @Override
    ShortUrlDetails save(ShortUrlDetails shortUrlDetails);

    Optional<ShortUrlDetails> findByLongUrl(String longUrl);

    void deleteInBatchByExpiresAtAfter(LocalDateTime currentDateTime);
}
