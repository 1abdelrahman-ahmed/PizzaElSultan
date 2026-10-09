package pricing;

import model.Order;

public class OpeningPromotion implements DiscountPolicy{
    @Override
    public double calculateDiscount(Order order) {
        double subtotal = order.getSubtotal();
        if (subtotal >= 500) {
            return subtotal * 0.10;
        }

        return 0;
    }
}
