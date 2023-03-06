package com.example.order.servive;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public String getOrder() {
        return "this is your order";
    }
}
