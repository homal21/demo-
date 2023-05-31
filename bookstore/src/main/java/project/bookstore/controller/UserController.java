package project.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.bookstore.exception.AppApiException;
import project.bookstore.request.CartRequest;
import project.bookstore.service.UserService;

@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/addCart/{id}")
    public ResponseEntity<?> addCart(@RequestBody CartRequest cartRequest, @PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(userService.addCart(cartRequest,id));

        } catch (AppApiException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
    @PostMapping("/subCart/{id}")
    public ResponseEntity<?> subCart(@RequestBody CartRequest cartRequest, @PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(userService.subCart(cartRequest,id));

        } catch (AppApiException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/viewCart")
    public ResponseEntity<?> viewCart() {
        return ResponseEntity.ok().body(userService.viewCart());
    }

    @PostMapping("/purchaseOrder")
    public ResponseEntity<?> purchaseOrder(@RequestBody String payment_status) {
        return ResponseEntity.ok().body(userService.purchaseOrder(payment_status));
    }
    @DeleteMapping("/cancelOrder/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id)  {
        return ResponseEntity.ok().body(userService.cancelOrder(id));
    }
    @GetMapping("/viewOrder")
    public ResponseEntity<?> viewOrder() {
        return ResponseEntity.ok().body(userService.viewOrder());
    }

}
