package org.krlozmedina.car;

import java.util.Objects;

public class Driver {
    private String name;
    private static int id = 1;

    public Driver(String name) {
        setName(name);
        id++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!Objects.equals(name, "")) {
            this.name = name;
        } else {
            this.name = "Player" + id;
        }
    }

    public static int throwDice() {
        return (int) (Math.random() * 6) + 1;
    }
}
