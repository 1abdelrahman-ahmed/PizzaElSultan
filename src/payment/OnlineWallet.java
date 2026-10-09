package payment;

public class OnlineWallet implements  Payment, ElectronicRefund{
    @Override
    public PaymentResult pay(double amount) {
        if (!authorize(amount)) {
            return new PaymentResult(false, "Wallet payment failed"
            );
        }

        return new PaymentResult(true, "Wallet payment successful"
        );
    }

    private boolean authorize(double amount) {
        return amount > 0;
    }

    @Override
    public PaymentResult refund(double amount) {
        if (amount <= 0) {
            return new PaymentResult(
                    false,
                    "Cash payment failed"
            );
        }

        return new PaymentResult(
                true,
                "Cash payment successful"
        );
    }
}
