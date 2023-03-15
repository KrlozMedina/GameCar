package org.krlozmedina.car;

public class Driver {
    private String name;

    public Driver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static int throwDice() {
        return (int) (Math.random() * 6) + 1;
    }
}
