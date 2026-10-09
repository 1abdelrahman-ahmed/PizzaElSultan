package model;

public class Olives implements Topping{
    @Override
    public String getName() {
        return "Olives";
    }

    @Override
    public double getPrice() {
        return 15;
    }
}
