package pricing;

import model.Order;

public interface DiscountPolicy {
    double calculateDiscount(Order order);
}
