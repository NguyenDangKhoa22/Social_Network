package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.InviteFriend;



public interface InviteRepository extends JpaRepository<InviteFriend, Long>{
} 
