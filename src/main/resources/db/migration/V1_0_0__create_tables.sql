CREATE TABLE short_url_details(short_url_id VARCHAR(16) PRIMARY KEY,
    long_url VARCHAR(255) NOT NULL UNIQUE,
     expires_at DATE NOT NULL);


CREATE TABLE alias_details(alias VARCHAR(16) PRIMARY KEY,
    long_url VARCHAR(255) NOT NULL,
     expires_at DATE NOT NULL);