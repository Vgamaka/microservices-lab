package com.example.common.events;

public record OrderCreatedEvent(String orderId, String item, int quantity) {
}
