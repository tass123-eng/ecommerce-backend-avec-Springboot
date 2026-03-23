/**
 * 
 */
package com.TeachCode.E_commerce.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.TeachCode.E_commerce.Models.RefreshToken;
import com.TeachCode.E_commerce.Models.User;

/**
 * 
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    // Supprime tous les refresh tokens associés à un utilisateur
    // DELETE FROM refresh_tokens WHERE user_id = ?
    // Nécessite une transaction (déjà présente via @Transactional au niveau service)
    @Modifying // Suppression bidirectionnelle
    void deleteByUser(User user);
}
