package project.bookstore.response;

import java.util.List;
import lombok.Data;
import project.bookstore.entity.Cart;

@Data
public class CartResponse {
    private List<Cart> cart;
    private Double total;

    public CartResponse(List<Cart> cart, Double total) {
        this.cart = cart;
        this.total = total;
    }

}
