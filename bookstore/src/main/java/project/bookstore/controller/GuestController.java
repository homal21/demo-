package project.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.entity.Book;
import project.bookstore.repo.BookRepo;
import project.bookstore.repo.CartRepo;

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
