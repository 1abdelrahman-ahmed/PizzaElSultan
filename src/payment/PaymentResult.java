package payment;

public class PaymentResult {
    private boolean successful;
    private String message;

    public PaymentResult(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }
}
