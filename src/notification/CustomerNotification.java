package notification;

import model.Customer;

public class CustomerNotification {
    private Notification notification;

    public CustomerNotification(Notification notification) {
        this.notification = notification;
    }

    public void sendNotify(Customer customer) {
        String message = "Hello " + customer.getName()
                + ", your order is ready!";

        notification.send(message);
    }
}
