package com.example.demoWebBanHang.Response;

import com.example.demoWebBanHang.Entity.Cart;
import lombok.Data;

import java.util.List;

@Data
public class CartRespone {
    private List<Cart> cart;
    private Double total;

    public CartRespone(List<Cart> cart, Double total) {
        this.cart = cart;
        this.total = total;
    }

}
