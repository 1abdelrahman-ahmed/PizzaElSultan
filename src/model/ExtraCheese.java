package model;

public class ExtraCheese implements Topping{
    @Override
    public String getName() {
        return "Extra Cheese";
    }

    @Override
    public double getPrice() {
        return 25;
    }
}
