package io.awspring.workshop.domain;

public record Invoice(String fileName, byte[] content) {
    public Invoice(Order order, byte[] content) {
        this(fileNameFor(order.getOrderId()), content);
    }
    public static String fileNameFor(String orderId) {
        return "invoice-" + orderId + ".pdf";
    }
}
