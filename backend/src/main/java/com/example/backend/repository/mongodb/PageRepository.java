package com.example.backend.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.backend.entity.mongodb.Page;

public interface PageRepository extends MongoRepository<Page, String >{
}
