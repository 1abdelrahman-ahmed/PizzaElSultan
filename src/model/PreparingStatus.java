package model;

public class PreparingStatus implements OrderStatus{
    @Override
    public OrderStatus next() {
        return new ReadyStatus();
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public String getLabel() {
        return "PREPARING";
    }
}
