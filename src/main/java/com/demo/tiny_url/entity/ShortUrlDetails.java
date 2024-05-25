package com.demo.tiny_url.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlDetails {

    @Id
    @Column(name = "short_url_id")
    private String shortUrlId;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
