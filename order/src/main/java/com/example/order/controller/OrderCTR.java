package com.example.order.controller;

import com.example.order.servive.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderCTR {

    @Autowired
    private OrderService orderService;

    @GetMapping("/get_order")
    public String getOrder() {
        return orderService.getOrder();
    }
}
