package model;

public class NewStatus implements OrderStatus{
    @Override
    public OrderStatus next() {
        return new PreparingStatus();
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public String getLabel() {
        return "NEW";
    }
}
