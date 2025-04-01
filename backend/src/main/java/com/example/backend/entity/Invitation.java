package com.example.backend.entity;


import java.time.LocalDateTime;

import com.example.backend.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sender_id","receiver_id"})
    }
)
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false)
    User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id",nullable = false)
    User receiver;

    @Enumerated(EnumType.STRING)
    Status status;

    String message;
    
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
