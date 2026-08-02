package com.example.Security.repositories;

import com.example.Security.entities.SessionEntity;
import com.example.Security.entities.UserEntity;
import com.mysql.cj.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Long> {

    List<SessionEntity> findByUser(UserEntity user);
    Optional<SessionEntity>findByRefreshToken(String refreshToken);
}
