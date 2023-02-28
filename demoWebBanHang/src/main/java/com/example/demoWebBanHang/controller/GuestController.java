package com.example.demoWebBanHang.controller;

import com.example.demoWebBanHang.Entity.Book;
import com.example.demoWebBanHang.Repo.BookRepo;
import com.example.demoWebBanHang.Repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GuestController {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    CartRepo cartRepo;

    @GetMapping("/home")
    public List<Book> home() {
        return bookRepo.findAll();
    }
}
