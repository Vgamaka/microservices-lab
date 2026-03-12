package com.example.orders.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record OrderRequest(
        @NotBlank(message = "orderId is required")
        String orderId,
        @NotBlank(message = "item is required")
        String item,
        @Min(value = 1, message = "quantity must be at least 1")
        int quantity
) {
}
