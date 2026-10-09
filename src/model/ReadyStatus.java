package model;

public class ReadyStatus implements OrderStatus{
    @Override
    public OrderStatus next() {
        return new CompletedStatus();
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public String getLabel() {
        return "READY";
    }
}
