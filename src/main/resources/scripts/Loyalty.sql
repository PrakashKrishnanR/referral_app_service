USE loyalty;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    imageUrl VARCHAR(255),
    emailVerified BOOLEAN NOT NULL DEFAULT FALSE,
    password VARCHAR(255),
    provider VARCHAR(50) NOT NULL,
    providerId VARCHAR(255),
    UNIQUE (email)
);


CREATE TABLE referral (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    referral_code VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE offer_documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    document MEDIUMBLOB NOT NULL,
    updated_at DATETIME NOT NULL
);

-- Sample
INSERT INTO offer_documents (document, updated_at) VALUES
    (LOAD_FILE('/path/doc_1.pdf'), NOW()),
    (LOAD_FILE('/path/doc_2.pdf'), NOW());

