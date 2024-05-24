package com.demo.tiny_url.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Generated;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class AliasDetails {
    @Id
    @Column(name = "alias")
    private String alias;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public AliasDetails(String alias, String longUrl, LocalDateTime expiresAt) {
        this.alias = alias;
        this.longUrl = longUrl;
        this.expiresAt = expiresAt;
    }

    @Version
    @Generated
    @Column(name = "version")
    private Long version;

}
