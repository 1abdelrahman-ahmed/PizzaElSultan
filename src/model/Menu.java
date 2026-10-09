package model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Item> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
