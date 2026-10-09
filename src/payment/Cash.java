package payment;

public class Cash implements Payment{
    @Override
    public PaymentResult pay(double amount) {
        return new PaymentResult(true, "Cash payment sucessful");
    }
}
