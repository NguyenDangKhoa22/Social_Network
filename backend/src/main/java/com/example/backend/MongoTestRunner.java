package com.example.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.backend.entity.mongodb.Page;
import com.example.backend.repository.mongodb.PageRepository;

@Component
public class MongoTestRunner implements CommandLineRunner{
    private final PageRepository pageRepository;

    public MongoTestRunner(PageRepository pagerRepository){
        this.pageRepository = pagerRepository;
    }
     @Override
    public void run(String... args) {
        Page page = new Page();
        page.setUsername("Hẹ Hẹ Hẹ");
        pageRepository.save(page);
        System.out.println("Đã lưu user vào MongoDB: " + page.getId());
    }
}
