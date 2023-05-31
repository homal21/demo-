package project.bookstore.service;

import project.bookstore.entity.Book;
import project.bookstore.entity.Order;
import project.bookstore.entity.User;
import project.bookstore.request.UpdateOrderRequest;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    public List<User> view();

    public List<Order> viewOrder();

    public Optional<Order> updateOrder(Long id, UpdateOrderRequest updateOrderRequest);

    public String addNewBook(Book book);

    public String addBookQuantity(Long id, Long quantity);

    public String deleteUser(Long id);

}
