package model;

public class LargePizzaSize implements PizzaSize{
    @Override
    public double getExtraCharge() {
        return 60;
    }

    @Override
    public String getLabel() {
        return "LARGE";
    }
}
