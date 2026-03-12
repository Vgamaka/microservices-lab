package com.example.billing.service;

import com.example.common.events.OrderCreatedEvent;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BillingService {

    private final Map<String, String> invoices = new ConcurrentHashMap<>();

    public void generateInvoice(OrderCreatedEvent event) {
        String invoice = "Invoice generated for order %s (%s x %d)".formatted(
                event.orderId(), event.item(), event.quantity()
        );
        invoices.put(event.orderId(), invoice);
    }

    public Collection<String> getInvoices() {
        return invoices.values();
    }
}
