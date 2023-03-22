package org.krlozmedina.game;

import org.krlozmedina.car.Car;
import org.krlozmedina.car.Driver;
import org.krlozmedina.game.values.Track;
import org.krlozmedina.rail.Rail;

import java.util.ArrayList;

public class Player {
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Player> playersInGame = new ArrayList<>();
    private final String name;
    private final String color;
    private int score;

    Driver driver;
    Car car;
    Rail rail;

    public static int id = 0;

    public Player(String name, String color, int score) {
        this.driver = new Driver(name);
        this.name = driver.getName();

        this.car = new Car(this.driver, color);
        this.color = color;

        this.score = score;

        this.rail = new Rail(Track.getKilometers());

        players.add(this);
        id++;
    }

    public Player(String name, String color) {
        this.driver = new Driver(name);
        this.name = driver.getName();

        this.car = new Car(this.driver, color);
        this.color = color;

        this.score = 0;

        this.rail = new Rail(Track.getKilometers());

        playersInGame.add(this);
    }

//    Getters and Setters
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    //  Private methods

}
