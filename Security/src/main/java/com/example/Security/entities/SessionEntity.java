package com.example.Security.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshToken; // typo fix

    @CreationTimestamp
    private LocalDateTime createdAt; // jab session bana

    private LocalDateTime lastUsedAt; // manually update karo har login/refresh pe

    @ManyToOne
    private UserEntity user;   // ye add karo wapas

}
