package org.krlozmedina.rail;


public class Rail {
    private int meters;
    private int position;

    public Rail(int kilometers) {
        this.meters = kilometers * 1000;
    }

    public int getPosition() {
        return position;
    }

    public int moveCar(int howMany) {
        this.position += howMany * 100;
        System.out.println("The car move " + howMany * 100 + "m");
        return getPosition();
    }
}
