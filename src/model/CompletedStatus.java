package model;

public class CompletedStatus implements OrderStatus{
    @Override
    public OrderStatus next() {
        return this;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public String getLabel() {
        return "COMPLETED";
    }
}
