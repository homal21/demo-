package project.bookstore.response;

import java.util.List;
import java.util.Optional;
import lombok.Data;
import project.bookstore.entity.Cart;
import project.bookstore.entity.Order;

@Data
public class PurchaseResponse {
    private Optional<Order> orders;
    private List<Cart> cartList;

    public PurchaseResponse(Optional<Order> orders, List<Cart> cartList) {
        this.orders = orders;
        this.cartList = cartList;
    }
}