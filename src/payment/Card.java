package payment;

public class Card implements Payment, ElectronicRefund{
    @Override
    public PaymentResult pay(double amount) {
        if (!authorize(amount)) {
            return new PaymentResult(false, "Cash payment failed");
        }

        return new PaymentResult(true, "Cash payment sucessful");
    }

    private boolean authorize(double amount) {
        return (amount > 0);
    }

    @Override
    public PaymentResult refund(double amount) {
        if (amount <= 0) {
            return new PaymentResult(
                    false,
                    "Invalid refund amount"
            );
        }

        return new PaymentResult(
                true,
                "Card refund successful"
        );
    }
}
