package org.krlozmedina.game;

import org.krlozmedina.car.Car;
import org.krlozmedina.car.Driver;

public class Player {
    public static int id;
    private String name;
    private String color;
    private int points;

//    Constructor
    Player() {
        id++;
        Driver driver = new Driver();
//        driver.setName(name);
        Car car = new Car(driver);
    }

//    Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPoints(int points) {
        this.points = points;
    }

//    Getters
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getPoints() {
        return points;
    }
}
