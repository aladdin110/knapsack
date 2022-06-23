package com.example.fuck;
import java.util.ArrayList;

public class KP {

    private final ArrayList<Item> items;
    private final double capacity;
    KP(ArrayList<Item> items, double capacity) {
        this.items = items;
        this.capacity = capacity;
    }
    public int getLength() {
        return items.size();
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    public Item getItem(int index) {
        return items.get(index);
    }

    public double getCapacity() {
        return capacity;
    }


}
