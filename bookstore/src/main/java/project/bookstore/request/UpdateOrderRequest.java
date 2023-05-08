package project.bookstore.request;
import lombok.Data;
@Data
public class UpdateOrderRequest {
    private String order_status;
    private String shipping_status;
}
