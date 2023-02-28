package com.example.demoWebBanHang.Response;

import com.example.demoWebBanHang.Entity.Cart;
import com.example.demoWebBanHang.Entity.Order;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class PurchaseResponse {
    private Optional<Order> orders;
    private List<Cart> cartList;

    public PurchaseResponse(Optional<Order> orders, List<Cart> cartList) {
        this.orders = orders;
        this.cartList = cartList;
    }
}
