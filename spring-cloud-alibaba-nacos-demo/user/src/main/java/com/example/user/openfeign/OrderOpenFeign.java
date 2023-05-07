package com.example.user.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "order")
public interface OrderOpenFeign {

    @RequestMapping("/get_order")
    String getOrder();

}
