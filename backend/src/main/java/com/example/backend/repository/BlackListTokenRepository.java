package com.example.backend.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.BlackListToken;

import jakarta.transaction.Transactional;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken,String>{
    @Modifying
    @Transactional
    @Query("DELETE FROM BlackListToken t WHERE t.expiryTime < :now")
    void deleteExpiredToken(@Param("now")Date now);
}
