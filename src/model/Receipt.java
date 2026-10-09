package model;

public class Receipt {
    public void print(Order order) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        System.out.println("================================");
        System.out.println("          PIZZA RECEIPT         ");
        System.out.println("================================");

        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Status: " + order.getStatus().getLabel());

        System.out.println("--------------------------------");
        System.out.printf("%-18s %5s %8s%n",
                "Item", "Qty", "Total");
        System.out.println("--------------------------------");

        for (OrderItem orderItem : order.getItems()) {

            String itemName = orderItem.getItem().getName();
            int quantity = orderItem.getQuantity();
            double total = orderItem.getTotalPrice();

            System.out.printf("%-18s %5d %8.2f EGP%n",
                    itemName, quantity, total);
        }

        System.out.println("--------------------------------");

        System.out.printf("Subtotal:          %.2f EGP%n",
                order.getSubtotal());

        System.out.printf("Discount:         -%.2f EGP%n",
                order.getDiscount());

        System.out.printf("Fulfillment:       %.2f EGP%n",
                order.getFulfillment());

        System.out.println("--------------------------------");

        System.out.printf("FINAL TOTAL:       %.2f EGP%n",
                order.getFinalTotal());

        System.out.println("--------------------------------");

        System.out.println("Payment status: "
                + (order.isPaid() ? "PAID" : "NOT PAID"));

        System.out.println("================================");
        System.out.println("       Thank you for ordering!  ");
        System.out.println("================================");
    }
}
