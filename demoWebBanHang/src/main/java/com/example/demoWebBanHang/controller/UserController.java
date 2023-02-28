package com.example.demoWebBanHang.controller;

import com.example.demoWebBanHang.Request.CartRequest;
import com.example.demoWebBanHang.Entity.Book;
import com.example.demoWebBanHang.Entity.Cart;
import com.example.demoWebBanHang.Entity.Order;
import com.example.demoWebBanHang.Entity.User;
import com.example.demoWebBanHang.Repo.BookRepo;
import com.example.demoWebBanHang.Repo.CartRepo;
import com.example.demoWebBanHang.Repo.OrderRepo;
import com.example.demoWebBanHang.Repo.UserRepo;
import com.example.demoWebBanHang.Response.CartRespone;
import com.example.demoWebBanHang.Response.PurchaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserController {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    CartRepo cartRepo;

    Map<Long, List<Cart>> orderList = new HashMap<>();


    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            name = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        User user = userRepo.findByUsername(name);
        return user;
    }

    public List<Cart> FinalCart() {
        User user = getUser();
        List<Cart> cartList = cartRepo.findAll();
        List<Cart> cartListCheck =cartList.stream()
                .filter(cart1 -> cart1.getUserId()== user.getId())
                .collect(Collectors.toList());

        List<Cart> finalCart = cartListCheck.stream().collect(Collectors.groupingBy(Cart::getBookId))
                .values()
                .stream()
                .map(idGroup -> idGroup.stream().max(Comparator.comparing(Cart::getId)).get())
                .collect(Collectors.toList());
        return finalCart;
    }

    public List<Cart> FilterCartByUserId(Long id, User user) {
        List<Cart> cartList = cartRepo.findAllByBookId(id);
        Collections.sort(cartList, Comparator.comparing(Cart::getId).reversed());
        List<Cart> cartListCheck =cartList.stream()
                .filter(cart1 -> cart1.getUserId()== user.getId())
                .collect(Collectors.toList());
        return  cartListCheck;
    }

    @PostMapping("/addCart/{id}")
    public ResponseEntity<?> addCart(@RequestBody CartRequest cartRequest, @PathVariable(name = "id") Long id) {
        User user = getUser();
        Long quantity;
        Cart cart = new Cart();
        Book book = new Book();
        List<Cart> cartListCheck = FilterCartByUserId(id, user);
        Optional<Book> bookOptional = bookRepo.findById(id);
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
        }
        cart.setBookId(id);
        cart.setUserId(user.getId());
        if (cartListCheck.isEmpty()) {
            quantity = cartRequest.getQuantity();
            cart.setBookId(id);
            cart.setUserId(user.getId());
            cart.setQuantity(quantity);
            cart.setPrice(book.getPrice()*quantity);
            if (cartRequest.getQuantity() > book.getQuantity()) {
                return ResponseEntity.ok("so luong trong kho khogn du");
            } else {
                Long k = book.getQuantity() - cartRequest.getQuantity();
                book.setQuantity(k);
                bookRepo.save(book);
            }
            cartRepo.save(cart);
            return ResponseEntity.ok().body(cartRepo.findAll());
        }
        if (cartRepo.existsByBookId(id)) {
            quantity = cartListCheck.get(0).getQuantity() + cartRequest.getQuantity();
            System.out.println(cartListCheck);
        } else {
            quantity = cartRequest.getQuantity();
        }
        if (cartRequest.getQuantity() > book.getQuantity()) {
            return ResponseEntity.ok("so luong trong kho khogn du");
        } else {
            Long k = book.getQuantity() - cartRequest.getQuantity();
            book.setQuantity(k);
            bookRepo.save(book);
        }
        cart.setQuantity(quantity);
        cart.setPrice(book.getPrice()*quantity);
        cartRepo.save(cart);
        return ResponseEntity.ok().body(cartRepo.findAll());

    }
    @PostMapping("/subCart/{id}")
    public ResponseEntity<?> subCart(@RequestBody CartRequest cartRequest, @PathVariable(name = "id") Long id) {
        Long quantity;
        User user = getUser();
        Cart cart = new Cart();
        Book book = new Book();
        List<Cart> cartListCheck = FilterCartByUserId(id, user);
        Optional<Book> bookOptional = bookRepo.findById(id);
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
        }
        if (cartListCheck.isEmpty()) {
            quantity = cartRequest.getQuantity();
            cart.setBookId(id);
            cart.setUserId(user.getId());
            cart.setQuantity(quantity);
            if (cartRequest.getQuantity() > book.getQuantity()) {
                return ResponseEntity.ok("so luong trong kho khogn du");
            } else {
                Long k = book.getQuantity() - cartRequest.getQuantity();
                book.setQuantity(k);
                bookRepo.save(book);
            }
            cartRepo.save(cart);
            return ResponseEntity.ok().body(cartRepo.findAll());
        }
        cart.setBookId(id);
        cart.setUserId(user.getId());
        if (cartRepo.existsByBookId(id)) {
            quantity = cartListCheck.get(0).getQuantity() - cartRequest.getQuantity();
//            System.out.println(cartList.get(0).getQuantity()+ " "+ quantity);
        } else {
            quantity = cartRequest.getQuantity();
        }
        if (cartRequest.getQuantity() > cartListCheck.get(0).getQuantity()) {
            return ResponseEntity.ok("huy don hang");
        } else {
            Long k = book.getQuantity() + cartRequest.getQuantity();
            book.setQuantity(k);
            bookRepo.save(book);
        }
        cart.setQuantity(quantity);
        cartRepo.save(cart);
        return ResponseEntity.ok().body(cartRepo.findAll());

    }

    @GetMapping("/viewCart")
    public ResponseEntity<?> viewCart() {
        List<Cart> finalCart = FinalCart();
        Double total = 0.0;
        for (Cart c : finalCart) {
            total += c.getPrice();
        }
        CartRespone respone = new CartRespone(finalCart, total);
        return ResponseEntity.ok().body(respone);
    }

    @PostMapping("/purchaseOrder")
    public ResponseEntity<?> purchaseOrder(@RequestBody String payment_status) {

        Date date = new Date(System.currentTimeMillis());
        User user = getUser();
        List<Cart> finalCart = FinalCart();
        Double total = 0.0;
        for (Cart c : finalCart) {
            total += c.getPrice();
        }
        Order order = new Order();
        order.setUser_id(user.getId());
        order.setOrder_status("pending");
        order.setPayment_status(payment_status);
        order.setOrder_date(date);
        order.setAmount(total);
        orderRepo.save(order);

        orderList.put(orderRepo.findById(order.getId()).get().getId(), finalCart);

        PurchaseResponse purchaseResponse = new PurchaseResponse(orderRepo.findById(order.getId()),finalCart);

        cartRepo.deleteAllByUserId(user.getId());

        return ResponseEntity.ok().body(purchaseResponse);
    }

    @DeleteMapping("/cancelOrder/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id)  {
        List<Cart> listCart = orderList.get(id);
        for (Cart cart: listCart) {
            Book book = bookRepo.findById(cart.getBookId()).get();
            long quantity = book.getQuantity();
            book.setQuantity(quantity+ cart.getQuantity());
            bookRepo.save(book);
        }
        orderList.remove(id);
        orderRepo.deleteById(id);
        return ResponseEntity.ok("huy don thanh cong");
    }

}
