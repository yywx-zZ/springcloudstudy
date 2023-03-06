package com.example.user.controller;

import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCTR {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient discoveryClient;

//    @Autowired
//    private RestTemplate restTemplate;


    @GetMapping("/nacos")
    public List<ServiceInstance> nacos(@RequestParam String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }


//    @GetMapping("/getOrder")
//    public String getOrder(@RequestParam String serviceId) {
//        URI uri = discoveryClient.getInstances(serviceId).get(0).getUri();
//        return restTemplate.getForObject(uri + "/get_order", String.class);
//    }
    @GetMapping("/get_user")
    public String getUser() {
        return userService.getUser();
    }
}
