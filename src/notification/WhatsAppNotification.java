package notification;

public class WhatsAppNotification implements Notification{
    @Override
    public void send(String message) {
        System.out.println("Message send using WhatsApp notification");
    }
}
