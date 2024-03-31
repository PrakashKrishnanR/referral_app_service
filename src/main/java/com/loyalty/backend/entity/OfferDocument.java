package com.loyalty.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "offer_documents")
public class OfferDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "document", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] document;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

