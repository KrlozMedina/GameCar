package org.krlozmedina.car;

public class Car {
    private Driver driver;
    private int distance;
    private String color;

    public Car(Driver driver, String color) {
        this.driver = driver;
        this.color = color;
    }

//    public int moveInRail() {
//
//    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
