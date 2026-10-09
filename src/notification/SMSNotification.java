package notification;

public class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Message send using SMS notification");
    }
}
