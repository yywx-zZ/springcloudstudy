package com.example.user.controller;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
public class UserCTR {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/nacos")
    public List<ServiceInstance> nacos(@RequestParam String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }


    //  RestTemplate注入，通过自定义随机负载
    @GetMapping("/get_order/rest")
    public String getOrder(@RequestParam String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        List<String> uris = instances.stream().map(e -> e.getUri().toString()).collect(Collectors.toList());
        int idx = ThreadLocalRandom.current().nextInt(instances.size());
        return restTemplate.getForObject(uris.get(idx) + "/get_order", String.class);
    }

    // lbv1, pom引入spring-cloud-starter-loadbalancer，注入loadBalancerClient，
    // 通过loadBalancerClient获取注册信息，通过restTemplate远程调用
    // 默认轮询调用
    @GetMapping("/get_order/lb/v1")
    public String getOrderBylbV1(@RequestParam String serviceId) {
        ServiceInstance instance = loadBalancerClient.choose(serviceId);
        return restTemplate.getForObject(instance.getUri().toString() + "/get_order", String.class);
    }

    // lbV2, pom引入spring-cloud-starter-loadbalancer，restTemplate注入增加@LoadBalanced注解
    // @LoadBalanced赋予了RestTemplate服务发现和负载的功能

    // lbv3 pom引入spring-cloud-starter-loadbalancer，restTemplate注入增加@LoadBalanced注解,
    // 自定义LoadBalanceConfiguration类，本例自定义随机负载
    // 启动类添加@LoadBalancerClients(defaultConfiguration = LoadBalanceConfiguration.class)

    @GetMapping("/get_order/lb/v2")
    public String getOrderBylbV2(@RequestParam String serviceId) {
        return restTemplate.getForObject("http://" +serviceId + "/get_order", String.class);
    }
}
