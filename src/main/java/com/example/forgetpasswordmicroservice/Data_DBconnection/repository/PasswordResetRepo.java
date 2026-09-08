package com.example.forgetpasswordmicroservice.Data_DBconnection.repository;

import com.example.forgetpasswordmicroservice.Data_DBconnection.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepo extends JpaRepository<PasswordResetToken, Integer> {
    void deleteByEmail(String email);
    Optional<PasswordResetToken> findByEmailAndToken(String email, String token);
}
