package com.example.billing.consumer;

import com.example.billing.service.BillingService;
import com.example.common.events.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Component
@RestController
public class BillingConsumer {

    private static final Logger log = LoggerFactory.getLogger(BillingConsumer.class);

    private final BillingService billingService;

    public BillingConsumer(BillingService billingService) {
        this.billingService = billingService;
    }

    @KafkaListener(topics = "${app.kafka.order-topic}", groupId = "billing-group")
    public void consume(OrderCreatedEvent event) {
        billingService.generateInvoice(event);
        log.info("Invoice generated for order {}", event.orderId());
    }

    @GetMapping("/billing/invoices")
    public Collection<String> getInvoices() {
        return billingService.getInvoices();
    }
}
