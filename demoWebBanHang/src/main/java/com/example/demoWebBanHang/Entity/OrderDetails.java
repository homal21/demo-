package com.example.demoWebBanHang.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name ="orders_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long order_id;
    private Long book_id;
    private Long quantity;
}
