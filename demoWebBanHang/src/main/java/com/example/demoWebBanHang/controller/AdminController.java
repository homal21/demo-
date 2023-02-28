package com.example.demoWebBanHang.controller;

import com.example.demoWebBanHang.Request.UpdateOrderRequest;
import com.example.demoWebBanHang.Entity.Book;
import com.example.demoWebBanHang.Entity.Order;
import com.example.demoWebBanHang.Entity.User;
import com.example.demoWebBanHang.Repo.BookRepo;
import com.example.demoWebBanHang.Repo.OrderRepo;
import com.example.demoWebBanHang.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    BookRepo bookRepo;
    @GetMapping("/listAccounts")
    public List<User> view() {
        return userRepo.findAll();
    }

    @GetMapping("/viewOrder")
    public List<Order> viewOrder() {
        return orderRepo.findAll();
    }

    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepo.findById(id).get();
        order.setOrder_status(updateOrderRequest.getOrder_status());
        order.setShipping_status(updateOrderRequest.getShipping_status());
        orderRepo.save(order);
        return ResponseEntity.ok().body(orderRepo.findById(id));
    }

    @PostMapping("/addNewBook")
    public ResponseEntity<?> addNewBook(@RequestBody Book book) {
        bookRepo.save(book);
        return ResponseEntity.ok("them sach thanh cong");
    }

    @PostMapping("/addBookQuantity/{id}")
    public ResponseEntity<?> addBookQuantity(@PathVariable Long id, @RequestBody Long quantity) {
        Book book = bookRepo.findById(id).get();
        book.setQuantity(book.getQuantity()+quantity);
        bookRepo.save(book);
        return ResponseEntity.ok("them so luong sach thanh cong");
    }

}
