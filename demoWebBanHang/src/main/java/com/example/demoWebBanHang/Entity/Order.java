package com.example.demoWebBanHang.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    @Temporal(TemporalType.DATE)
    private Date order_date;
    private String order_status;
    private String payment_status;
    private String shipping_status;
    @Column(name = "amount")
    private Double amount;

}
