package project.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.bookstore.entity.Book;
import project.bookstore.entity.Order;
import project.bookstore.entity.User;
import project.bookstore.repo.BookRepo;
import project.bookstore.repo.OrderRepo;
import project.bookstore.repo.UserRepo;
import project.bookstore.request.UpdateOrderRequest;
import project.bookstore.service.AdminService;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    BookRepo bookRepo;
    @Override
    public List<User> view() {
        return userRepo.findAll();
    }

    @Override
    public List<Order> viewOrder() {
        return orderRepo.findAll();
    }

    @Override
    public Optional<Order> updateOrder(Long id, UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepo.findById(id).get();
        order.setOrderStatus(updateOrderRequest.getOrder_status());
        order.setShippingStatus(updateOrderRequest.getShipping_status());
        orderRepo.save(order);
        return orderRepo.findById(id);
    }

    @Override
    public String addBookQuantity(Long id, Long quantity) {
        Book book = bookRepo.findById(id).get();
        book.setQuantity(book.getQuantity()+quantity);
        bookRepo.save(book);
        return "Them sach thanh cong";
    }

    @Override
    public String addNewBook(Book book) {
        bookRepo.save(book);
        return "Them sach moi thanh cong";
    }
}