package com.example.demoWebBanHang.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(name = "user_id")
    @JsonIgnore
    private Long userId;
    @Column(name = "book_id")
    private Long bookId;
    private Long quantity;
    @Column(name = "price")
    private Double price;
}
