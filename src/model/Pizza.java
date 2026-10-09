package model;

import java.util.ArrayList;
import java.util.List;

public class Pizza extends Item{
    private PizzaSize size;
    private List<Topping> toppings;

    public Pizza(String name, double price, boolean available, PizzaSize size) {
        super(name, price, available);

        this.size = size;
        this.toppings = new ArrayList<>();
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    @Override
    public double getPrice() {
        double total = super.getPrice();

        total =+size.getExtraCharge();

        for (Topping topping: toppings) {
            total += topping.getPrice();
        }

        return total;
    }
}
