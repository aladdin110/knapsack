package com.example.fuck;

public class Item {

    private String name;
    private final double value;
    private final double weight;

    public Item(double weight, double value) {
        this.value = value;
        this.weight = weight;
    }

    public Item(String name, double weight, double value) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }
    public String getName() {
        return name;
    }
}
