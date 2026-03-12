package com.example.orders.controller;

import com.example.common.events.OrderCreatedEvent;
import com.example.orders.model.OrderRequest;
import com.example.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderCreatedEvent event = orderService.createOrder(request);
        return Map.of(
                "message", "Order Created & Event Published",
                "order", event
        );
    }

    @GetMapping
    public Collection<OrderCreatedEvent> getOrders() {
        return orderService.getOrders();
    }
}
