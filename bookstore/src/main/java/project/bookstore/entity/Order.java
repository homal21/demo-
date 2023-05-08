package project.bookstore.entity;


import javax.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name ="order_status")
    private String orderStatus;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "shipping_status")
    private String shippingStatus;
    @Column(name = "amount")
    private Double amount;

}
