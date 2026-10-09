package model;

import payment.Payment;
import payment.PaymentResult;
import pricing.DiscountPolicy;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;
    private Fulfillment fulfillment;
    private DiscountPolicy discountPolicy;
    private Payment payment;
    private PaymentResult paymentResult;

    public Order(
            String orderId,
            Customer customer,
            Fulfillment fulfillment,
            DiscountPolicy discountPolicy) {

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID is required");
        }

        if (customer == null) {
            throw new IllegalArgumentException("Customer is required");
        }

        if (fulfillment == null) {
            throw new IllegalArgumentException("Fulfillment is required");
        }

        if (discountPolicy == null) {
            throw new IllegalArgumentException("Discount policy is required");
        }

        this.orderId = orderId;
        this.customer = customer;
        this.fulfillment = fulfillment;
        this.discountPolicy = discountPolicy;
        this.items = new ArrayList<>();
        this.status = new NewStatus();
        this.payment = null;
        this.paymentResult = null;
    }

    // Items

     public void addItem(Item item, int quantity) {
        if (status.isTerminal()) {
            throw new IllegalStateException("Cannot modify a completed or cancelled order");
        }

         if (item == null) {
             throw new IllegalArgumentException("Item cannot be null");
         }

         if (!item.isAvailable()) {
             throw new IllegalArgumentException("Item is not available");
         }

         if (quantity <= 0) {
             throw new IllegalArgumentException("Quantity must be positive");
         }

         for (OrderItem orderItem : items) {
             if (orderItem.getItem() == item) {
                 orderItem.addQuantity(quantity);
                 return;
             }
         }

         items.add(new OrderItem(item, quantity));
     }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    // Pricing

    public double getSubtotal() {
        double subtotal = 0;
        for (OrderItem item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public double getDiscount() {
        return discountPolicy.calculateDiscount(this);
    }

    public double getFulfillment() {
        return fulfillment.getcharge();
    }

    public double getFinalTotal() {
        return getSubtotal() - getDiscount() + getFulfillment();
    }

    // Order Status

    public OrderStatus getStatus() {
        return status;
    }

    public void nextStatus() {
        if (status.isTerminal()) {
            throw new IllegalStateException("Cannot change a terminal order");
        }
        status = status.next();
    }

    public void cancel() {
        if (status.isTerminal()) {
            throw new IllegalStateException("Cannot cancel a terminal order");
        }
        status = new CancelledStatus();
    }

    // Payment

    public PaymentResult pay() {
        if (status.isTerminal()) {
            throw new IllegalStateException("Cannot pay for a completed or cancelled order");
        }

        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot pay for an empty order");
        }

        if (payment == null) {
            throw new IllegalStateException("Payment method is not selected");
        }

        paymentResult = payment.pay(getFinalTotal());

        return paymentResult;
    }

    public void setPayment(Payment payment) {
        if (status.isTerminal()) {
            throw new IllegalStateException("Cannot change payment for a terminal order");
        }

        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }

        this.payment = payment;
    }

    public boolean isPaid() {
        return paymentResult != null && paymentResult.isSuccessful();
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }
}
