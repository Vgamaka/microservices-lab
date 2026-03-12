package com.example.orders.service;

import com.example.common.events.OrderCreatedEvent;
import com.example.orders.model.OrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final Map<String, OrderCreatedEvent> orders = new ConcurrentHashMap<>();
    private final String topicName;

    public OrderService(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
            @Value("${app.kafka.order-topic}") String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public OrderCreatedEvent createOrder(OrderRequest request) {
        OrderCreatedEvent event = new OrderCreatedEvent(request.orderId(), request.item(), request.quantity());
        orders.put(event.orderId(), event);
        kafkaTemplate.send(topicName, event.orderId(), event);
        return event;
    }

    public Collection<OrderCreatedEvent> getOrders() {
        return orders.values();
    }
}
