package com.example.inventory.consumer;

import com.example.common.events.OrderCreatedEvent;
import com.example.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Component
@RestController
public class InventoryConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryConsumer.class);

    private final InventoryService inventoryService;

    public InventoryConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "${app.kafka.order-topic}", groupId = "inventory-group")
    public void consume(OrderCreatedEvent event) {
        inventoryService.reserveStock(event);
        log.info("Inventory updated for order {} item {} quantity {}", event.orderId(), event.item(), event.quantity());
    }

    @GetMapping("/inventory")
    public Map<String, Integer> getInventory() {
        return inventoryService.getStockByItem();
    }
}
