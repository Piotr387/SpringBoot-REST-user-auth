package com.pp.app.io.repositories;

import com.pp.app.io.entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {

    Optional<PasswordResetTokenEntity> findByToken(String token);
}
