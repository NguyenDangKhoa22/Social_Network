package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.BlackListToken;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken,String>{
}
