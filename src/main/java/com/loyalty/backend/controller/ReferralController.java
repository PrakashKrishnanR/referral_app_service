package com.loyalty.backend.controller;

import com.loyalty.backend.entity.Referral;
import com.loyalty.backend.repository.ReferralRepository;
import com.loyalty.backend.repository.UserRepository;
import com.loyalty.backend.security.formLogin.CurrentUser;
import com.loyalty.backend.security.formLogin.UserPrincipal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/referral")
public class ReferralController {

    @Autowired
    ReferralRepository repo;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/get")
    public String getReferralCode(@CurrentUser UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        String referralCode = UUID.randomUUID().toString();
        Referral referral = new Referral();
        referral.setUser(userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found")));
        referral.setReferralCode(referralCode);
        repo.save(referral);
        return referralCode;
    }
}
