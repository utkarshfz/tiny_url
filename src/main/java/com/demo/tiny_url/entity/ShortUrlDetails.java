package com.demo.tiny_url.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.sql.Date;

@Getter
@Entity
public class ShortUrlDetails {

    @Id
    @Column(name = "short_url_id")
    private String shortUrlId;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "expires_at")
    private Date expiresAt;
}
