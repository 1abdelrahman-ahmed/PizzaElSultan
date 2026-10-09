package pricing;

import model.Order;
import model.OrderItem;
import model.Pizza;

public class SpecialPromotion implements DiscountPolicy{
    @Override
    public double calculateDiscount(Order order) {
        double subtotal = order.getSubtotal();

        int pizzaCount = order.getItems().stream()
                .filter(item -> item.getItem() instanceof Pizza)
                .mapToInt(OrderItem::getQuantity)
                .sum();

        if (subtotal > 300 && pizzaCount >= 2) {
            return subtotal * 0.15;
        }

        return 0;
    }
}
