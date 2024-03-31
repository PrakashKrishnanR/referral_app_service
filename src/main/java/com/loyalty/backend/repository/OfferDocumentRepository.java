package com.loyalty.backend.repository;

import com.loyalty.backend.entity.OfferDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferDocumentRepository extends JpaRepository<OfferDocument, Long> {
    Optional<OfferDocument> findFirstByOrderByUpdatedAtDesc();

}
