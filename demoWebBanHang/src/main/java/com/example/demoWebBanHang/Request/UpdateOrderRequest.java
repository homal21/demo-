package com.example.demoWebBanHang.Request;

import lombok.Data;

@Data
public class UpdateOrderRequest {
    private String order_status;
    private String shipping_status;
}
