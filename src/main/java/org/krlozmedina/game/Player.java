package org.krlozmedina.game;

import org.krlozmedina.car.Car;
import org.krlozmedina.car.Driver;
import org.krlozmedina.game.values.Track;
import org.krlozmedina.rail.Rail;

import java.util.ArrayList;

public class Player {
    public static final ArrayList<Player> players = new ArrayList<>();

    private String name;
    private String color;
    private int points;

    Driver driver;
    Car car;
    Rail rail;

//    Constructor
    public Player(String name, String color) {
        this.driver = new Driver(name);
        this.name = driver.getName();

        this.car = new Car(this.driver, color);
        this.color = color;

        this.rail = new Rail(Track.getKilometers());

        players.add(this);
    }

//    Setters
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

//  Private methods

}
