package model;

public class SmallPizzaSize implements PizzaSize{
    @Override
    public double getExtraCharge() {
        return 0;
    }

    @Override
    public String getLabel() {
        return "SMALL";
    }
}
