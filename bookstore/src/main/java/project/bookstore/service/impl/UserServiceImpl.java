package project.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.bookstore.entity.*;
import project.bookstore.repo.*;
import project.bookstore.request.CartRequest;
import project.bookstore.response.CartResponse;
import project.bookstore.response.PurchaseResponse;
import project.bookstore.service.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    CartRepo cartRepo;




    Map<Long, List<Cart>> orderList = new HashMap<>();

    // lay ra user hien tai dang login
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            name = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        User user = userRepo.findByUsername(name);
        return user;
    }
    @Override
    public String addCart(CartRequest cartRequest, Long id) {
        User user = getUser();
        Long quantity;
        Cart cart = new Cart();
        Book book = new Book();
        List<Cart> cartListCheck = cartRepo.findByBookIdOrderByIdDesc(id);
        Optional<Book> bookOptional = bookRepo.findById(id);
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
        }

        if (cartListCheck.isEmpty()) {
            quantity = cartRequest.getQuantity();
        } else {
            quantity = cartListCheck.get(0).getQuantity() + cartRequest.getQuantity();
        }
        if (cartRequest.getQuantity() > book.getQuantity()) {
            return "sach trong kho khong du";
        } else {
            Long k = book.getQuantity() - cartRequest.getQuantity();
            book.setQuantity(k);
            bookRepo.save(book);
            cart.setBookId(id);
            cart.setUserId(user.getId());
            cart.setQuantity(quantity);
            cart.setPrice(book.getPrice() * quantity);
            cartRepo.save(cart);
            return "them sach thanh cong";
        }
    }
    @Override
    public String subCart(CartRequest cartRequest, Long id) {
        Long quantity;
        User user = getUser();
        Cart cart = new Cart();
        Book book = new Book();
        List<Cart> cartListCheck = cartRepo.findByBookIdOrderByIdDesc(id);
        Optional<Book> bookOptional = bookRepo.findById(id);
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
        }
        if (cartListCheck.isEmpty()) {
            return "khong co sach trong gio";

        } else if (cartRequest.getQuantity() > cartListCheck.get(0).getQuantity()) {
            return "khong du sach trong gio";
        } else {
            Long k = book.getQuantity() + cartRequest.getQuantity();
            book.setQuantity(k);
            cart.setBookId(id);
            cart.setUserId(user.getId());
            quantity = cartListCheck.get(0).getQuantity() - cartRequest.getQuantity();
            cart.setQuantity(quantity);
            cart.setPrice(book.getPrice()*quantity);
            bookRepo.save(book);
            cartRepo.save(cart);
            return "xoa thanh cong";
        }

    }
    @Override
    public CartResponse viewCart() {
        User user = getUser();
        List<Cart> finalCart = cartRepo.findAllByGroupBookId(user.getId());
        Double total = 0.0;
        for (Cart c : finalCart) {
            total += c.getPrice();
        }
        CartResponse respone = new CartResponse(finalCart, total);
        System.out.println(finalCart);
        return respone;
    }
    @Override
    public PurchaseResponse purchaseOrder(String payment_status) {
        Date date = new Date(System.currentTimeMillis());
        User user = getUser();
        List<Cart> finalCart = cartRepo.findAllByGroupBookId(user.getId());
        Set<OrderDetails> orderDetailset = new HashSet<OrderDetails>();
        Double total = 0.0;
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderStatus("pending");
        order.setPaymentStatus(payment_status);
        order.setOrderDate(date);
        orderRepo.save(order);

        for (Cart c : finalCart) {
            total += c.getPrice();
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setQuantity(c.getQuantity());
            orderDetails.setBookId(c.getBookId());
            orderDetailset.add(orderDetails);
        }

        order.setAmount(total);
        order.setOrderDetails(orderDetailset);
        orderRepo.save(order);
//        orderList.put(orderRepo.findById(order.getId()).get().getId(), finalCart);
        PurchaseResponse purchaseResponse = new PurchaseResponse(orderRepo.findById(order.getId()));
        cartRepo.deleteAllByUserId(user.getId());
        return purchaseResponse;
    }
    @Override
    public String cancelOrder(Long id) {
        orderList.remove(id);
        orderRepo.deleteById(id);
        return "huy don thanh cong";
    }

    @Override
    public List<Order> viewOrder() {
        User user = getUser();
        return orderRepo.findAllByUserId(user.getId());
    }
}
