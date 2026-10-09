package model;

public interface OrderStatus {
    OrderStatus next();
    boolean isTerminal();
    String getLabel();
}
