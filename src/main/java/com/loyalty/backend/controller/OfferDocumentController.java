package com.loyalty.backend.controller;

import com.loyalty.backend.entity.OfferDocument;
import com.loyalty.backend.entity.OfferDocumentResponseDTO;
import com.loyalty.backend.repository.OfferDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

        if (latestDocumentOptional.isPresent()) {
            OfferDocument latestDocument = latestDocumentOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            OfferDocumentResponseDTO responseDTO = new OfferDocumentResponseDTO(
                    latestDocument.getDocument(),
                    latestDocument.getUpdatedAt()
            );

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
