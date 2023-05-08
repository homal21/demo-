package project.bookstore.service;

import project.bookstore.entity.Order;
import project.bookstore.request.CartRequest;
import project.bookstore.response.CartResponse;
import project.bookstore.response.PurchaseResponse;

import java.util.List;

public interface UserService {
    public String addCart(CartRequest cartRequest, Long id);

    public String subCart(CartRequest cartRequest, Long id);

    public CartResponse viewCart();

    public PurchaseResponse purchaseOrder(String payment_status);
    public String cancelOrder(Long id);

    public List<Order> viewOrder();

}
