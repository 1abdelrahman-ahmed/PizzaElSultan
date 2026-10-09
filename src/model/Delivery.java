package model;

public class Delivery implements Fulfillment{
    private String address;
    private String phoneNumber;

    public Delivery(String address, String phoneNumber) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Delivery address is required");
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public double getcharge() {
        return 30;
    }

    public void assign() {
        System.out.println("Delivery assigned.");
    }

    public int estimateDeliveryTime() {
        return 45;
    }
}
