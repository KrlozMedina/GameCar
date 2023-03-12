package org.krlozmedina.car;

import org.krlozmedina.rail.Rail;

public class Car {
    static int carId;
    private Driver driver;
    int distance;

    public Car(Driver driver) {
        this.driver = driver;
        Rail rail = new Rail(carId);
    }
}
