package model;

public class MediumPizzaSize implements PizzaSize{
    @Override
    public double getExtraCharge() {
        return 30;
    }

    @Override
    public String getLabel() {
        return "MEDIUM";
    }
}
