package com.example.Security.services;

import com.example.Security.entities.SessionEntity;
import com.example.Security.entities.UserEntity;
import com.example.Security.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private static final int SESSION_LIMIT = 2;
    private static final long REFRESH_TOKEN_VALIDITY_DAYS = 7; // apne hisaab se adjust karo

    public void generateNewSession(UserEntity user, String refreshToken) {
        List<SessionEntity> userSessions = sessionRepository.findByUser(user);

        // Limit cross ho rahi hai to sabse purana (least recently used) session hatao
        if (userSessions.size() >= SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity leastRecentlyUsed = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsed);
        }

        SessionEntity newSession = SessionEntity.builder()
                .user(user)
                .refreshToken(refreshToken)
                .lastUsedAt(LocalDateTime.now())
                .build();

        sessionRepository.save(newSession);
    }

    public boolean validateSession(String refreshToken) {
        SessionEntity sessionEntity = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found for refresh token"));

        // Expiry check — agar token bahut purana hai to invalid maano
        boolean isExpired = sessionEntity.getLastUsedAt()
                .plusDays(REFRESH_TOKEN_VALIDITY_DAYS)
                .isBefore(LocalDateTime.now());

        if (isExpired) {
            sessionRepository.delete(sessionEntity); // expired session cleanup
            return false;
        }

        // Valid hai to sliding expiry ke liye lastUsedAt update karo
        sessionEntity.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(sessionEntity);

        return true;
    }
}