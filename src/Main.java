import model.*;
import notification.CustomerNotification;
import notification.SMSNotification;
import notification.WhatsAppNotification;
import payment.*;
import pricing.OpeningPromotion;
import pricing.SpecialPromotion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("     PIZZA SULTAN DEMONSTRATION");
        System.out.println("==================================");

        // 1. Create menu items
        Pizza margherita =
                new Pizza("Margherita", 100, true, new LargePizzaSize());

        Pizza chickenRanch =
                new Pizza("Chicken Ranch", 160, true, new SmallPizzaSize());

        Drink cola = new Drink("Cola", 30, true);
        Drink water = new Drink("Water", 15, true);
        GarlicBread garlicBread = new GarlicBread(true);

        Customer customer1 = new Customer("Ahmed");
        Customer customer2 = new Customer("Mona");

        // ==================================
        // 2. TAKEAWAY ORDER
        // ==================================
        System.out.println("\n--- TAKEAWAY ORDER ---");

        Order takeawayOrder = new Order(
                "ORD-001",
                customer1,
                new Takeaway(),
                new OpeningPromotion()
        );

        takeawayOrder.addItem(margherita, 2);
        takeawayOrder.addItem(cola, 2);
        takeawayOrder.addItem(garlicBread, 1);

        System.out.println("Order ID: " + takeawayOrder.getOrderId());
        System.out.println("Customer: "
                + takeawayOrder.getCustomer().getName());
        System.out.println("Subtotal: "
                + takeawayOrder.getSubtotal() + " EGP");
        System.out.println("Discount: "
                + takeawayOrder.getDiscount() + " EGP");
        System.out.println("Fulfillment charge: "
                + takeawayOrder.getFulfillment() + " EGP");
        System.out.println("Final total: "
                + takeawayOrder.getFinalTotal() + " EGP");

        // Successful payment
        takeawayOrder.setPayment(new Cash());

        PaymentResult cashResult = takeawayOrder.pay();

        System.out.println("Payment: " + cashResult.getMessage());
        System.out.println("Paid: " + takeawayOrder.isPaid());

        // Status transitions
        takeawayOrder.nextStatus(); // NEW -> PREPARING
        takeawayOrder.nextStatus(); // PREPARING -> READY

        // SMS notification
        CustomerNotification smsNotifier =
                new CustomerNotification(new SMSNotification());

        smsNotifier.sendNotify(customer1);

        // Receipt
        new Receipt().print(takeawayOrder);


        // ==================================
        // 3. DELIVERY ORDER
        // ==================================
        System.out.println("\n--- DELIVERY ORDER ---");

        Order deliveryOrder = new Order(
                "ORD-002",
                customer2,
                new Delivery("Zagazig, Sharkia", "01012345678"),
                new SpecialPromotion()
        );

        deliveryOrder.addItem(chickenRanch, 2);
        deliveryOrder.addItem(water, 2);
        deliveryOrder.addItem(garlicBread, 1);

        System.out.println("Order ID: " + deliveryOrder.getOrderId());
        System.out.println("Customer: "
                + deliveryOrder.getCustomer().getName());
        System.out.println("Subtotal: "
                + deliveryOrder.getSubtotal() + " EGP");
        System.out.println("Discount: "
                + deliveryOrder.getDiscount() + " EGP");
        System.out.println("Delivery charge: "
                + deliveryOrder.getFulfillment() + " EGP");
        System.out.println("Final total: "
                + deliveryOrder.getFinalTotal() + " EGP");

        // Delivery behavior
        Delivery delivery =
                new Delivery("Zagazig, Sharkia", "01012345678");

        delivery.assign();
        System.out.println("Estimated delivery time: "
                + delivery.estimateDeliveryTime() + " minutes");

        // Failed payment simulation
        Payment failingPayment = amount ->
                new PaymentResult(false, "Payment declined");

        deliveryOrder.setPayment(failingPayment);

        PaymentResult failedResult = deliveryOrder.pay();

        System.out.println("Payment: " + failedResult.getMessage());
        System.out.println("Paid: " + deliveryOrder.isPaid());

        // WhatsApp notification
        CustomerNotification whatsappNotifier =
                new CustomerNotification(new WhatsAppNotification());

        whatsappNotifier.sendNotify(customer2);


        // ==================================
        // 4. EDGE CASES
        // ==================================
        System.out.println("\n--- EDGE CASES ---");

        // Edge case 1: Empty order payment
        Order emptyOrder = new Order(
                "ORD-003",
                new Customer("Empty Customer"),
                new Takeaway(),
                new OpeningPromotion()
        );

        emptyOrder.setPayment(new Cash());

        try {
            emptyOrder.pay();
            System.out.println("[FAIL] Empty order was accepted.");
        } catch (IllegalStateException e) {
            System.out.println(
                    "[PASS] Empty order rejected: " + e.getMessage());
        }

        // Edge case 2: Zero quantity
        try {
            emptyOrder.addItem(cola, 0);
            System.out.println("[FAIL] Zero quantity was accepted.");
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "[PASS] Zero quantity rejected: " + e.getMessage());
        }

        // Edge case 3: Out-of-stock item
        Drink unavailableDrink = new Drink("Unavailable Cola", 30, false);

        try {
            emptyOrder.addItem(unavailableDrink, 1);
            System.out.println("[FAIL] Unavailable item was accepted.");
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "[PASS] Unavailable item rejected: " + e.getMessage());
        }

        // Edge case 4: Invalid delivery information
        try {
            new Delivery("", "");
            System.out.println("[FAIL] Invalid delivery was accepted.");
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "[PASS] Invalid delivery rejected: " + e.getMessage());
        }

        // Edge case 5: Modify a cancelled order
        Order cancelledOrder = new Order(
                "ORD-004",
                new Customer("Cancelled Customer"),
                new Takeaway(),
                new OpeningPromotion()
        );

        cancelledOrder.addItem(cola, 1);
        cancelledOrder.cancel();

        try {
            cancelledOrder.addItem(water, 1);
            System.out.println("[FAIL] Cancelled order was modified.");
        } catch (IllegalStateException e) {
            System.out.println(
                    "[PASS] Cancelled order modification rejected: "
                            + e.getMessage());
        }

        System.out.println("\n==================================");
        System.out.println("     DEMONSTRATION FINISHED");
        System.out.println("==================================");
    }
}