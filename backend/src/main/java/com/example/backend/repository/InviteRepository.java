package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Invitetation;
import com.example.backend.entity.User;



public interface InviteRepository extends JpaRepository<Invitetation, Long>{
    boolean existsBySenderAndReceiver(User sender, User receiver);
} 
