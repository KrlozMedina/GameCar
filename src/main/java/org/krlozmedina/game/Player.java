package org.krlozmedina.game;

import org.krlozmedina.car.Car;
import org.krlozmedina.car.Driver;
import org.krlozmedina.game.values.Track;
import org.krlozmedina.rail.Rail;

import java.util.ArrayList;

public class Player {
    public static final ArrayList<Player> players = new ArrayList<>();
    private final String name;
    private final String color;
    private int points;

    Driver driver;
    Car car;
    Rail rail;

    public Player(String name, String color) {
        this.driver = new Driver(name);
        this.name = driver.getName();

        this.car = new Car(this.driver, color);
        this.color = color;

        this.rail = new Rail(Track.getKilometers());

        players.add(this);
    }

//    Getters and Setters
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

//  Private methods

}
