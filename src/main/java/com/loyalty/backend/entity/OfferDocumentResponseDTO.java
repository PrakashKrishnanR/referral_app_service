package com.loyalty.backend.entity;

import java.time.LocalDateTime;

public class OfferDocumentResponseDTO {

    private byte[] document;
    private LocalDateTime updatedAt;

    public OfferDocumentResponseDTO(byte[] document, LocalDateTime updatedAt) {
        this.document = document;
        this.updatedAt = updatedAt;
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

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
