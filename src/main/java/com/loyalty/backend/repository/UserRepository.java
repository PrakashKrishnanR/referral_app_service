package com.loyalty.backend.repository;

import com.loyalty.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // TODO: Include methods for find by phone number.
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

}
