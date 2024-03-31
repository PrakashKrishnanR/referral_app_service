package com.loyalty.backend.repository;

import com.loyalty.backend.entity.Referral;
import com.loyalty.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

}