package com.loyalty.backend.controller;

import com.loyalty.backend.entity.OfferDocument;
import com.loyalty.backend.entity.OfferDocumentResponseDTO;
import com.loyalty.backend.repository.OfferDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/document/offer")
public class OfferDocumentController {

    @Autowired
    OfferDocumentRepository repo;

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestOfferDocument() {
        Optional<OfferDocument> latestDocumentOptional = repo
                .findFirstByOrderByUpdatedAtDesc();

        return latestDocumentOptional
                .map(pdfDocument -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header("Content-Disposition", "attachment; filename=\"" + "loyaltyOffer" + "\"")
                        .body(new ByteArrayResource(pdfDocument.getDocument())))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
