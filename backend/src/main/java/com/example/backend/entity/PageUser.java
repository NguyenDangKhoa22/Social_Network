package com.example.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User createBy;

    String namePage;
    String titlePage;
    LocalDate createAt;
    LocalDate updateAt;
    
}
