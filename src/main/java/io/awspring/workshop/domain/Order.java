package io.awspring.workshop.domain;

import java.math.BigDecimal;

public class Order {
    private String orderId;
    private String productName;
    private String userId;
    private BigDecimal amount;

    public Order(String orderId, String productName, String userId, BigDecimal amount) {
        this.orderId = orderId;
        this.productName = productName;
        this.userId = userId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
