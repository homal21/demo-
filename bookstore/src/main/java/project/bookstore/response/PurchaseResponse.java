package project.bookstore.response;

import java.util.Optional;
import lombok.Data;
import project.bookstore.entity.Order;

@Data
public class PurchaseResponse {
    private Optional<Order> order;
    public PurchaseResponse(Optional<Order> order) {
        this.order = order;
    }
}