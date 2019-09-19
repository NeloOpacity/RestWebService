package com.example.boxwebservice.model;



public class Item {
    private Integer id;
    private String color;
    private Integer containedIn;

    public Item(Integer id, String color, Integer containedIn) {
        this.id = id;
        this.color = color;
        this.containedIn = containedIn;
    }

    public Integer getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Integer getContainedIn() {
        return containedIn;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
