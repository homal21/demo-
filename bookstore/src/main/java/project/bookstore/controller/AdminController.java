package project.bookstore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.bookstore.entity.Book;
import project.bookstore.entity.Order;
import project.bookstore.entity.User;
import project.bookstore.request.UpdateOrderRequest;
import project.bookstore.service.AdminService;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("/listAccounts")
    public List<User> view() {
        return adminService.view();
    }
    @GetMapping("/viewOrder")
    public List<Order> viewOrder() {
        return adminService.viewOrder();
    }

    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequest updateOrderRequest) {
        return ResponseEntity.ok().body(adminService.updateOrder(id, updateOrderRequest));
    }

    @PostMapping("/addNewBook")
    public ResponseEntity<?> addNewBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.addNewBook(book));
    }

    @PostMapping("/addBookQuantity/{id}")
    public ResponseEntity<?> addBookQuantity(@PathVariable Long id, @RequestBody Long quantity) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.addBookQuantity(id, quantity));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteUser(id));

    }

}
