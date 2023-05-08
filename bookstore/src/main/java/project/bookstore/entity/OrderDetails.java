package project.bookstore.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="orders_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "quantity")
    private Long quantity;
}