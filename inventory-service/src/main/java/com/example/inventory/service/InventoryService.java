package com.example.inventory.service;

import com.example.common.events.OrderCreatedEvent;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InventoryService {

    private final Map<String, Integer> stockByItem = new ConcurrentHashMap<>();

    public InventoryService() {
        stockByItem.put("Laptop", 10);
        stockByItem.put("Mouse", 25);
        stockByItem.put("Keyboard", 15);
    }

    public void reserveStock(OrderCreatedEvent event) {
        stockByItem.compute(event.item(), (item, currentStock) -> {
            int existing = currentStock == null ? 0 : currentStock;
            return existing - event.quantity();
        });
    }

    public Map<String, Integer> getStockByItem() {
        return stockByItem;
    }
}
