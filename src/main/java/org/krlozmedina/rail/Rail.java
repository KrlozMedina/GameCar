package org.krlozmedina.rail;

import org.krlozmedina.Utils;

public class Rail {
    private int meters;
    private int position;

    public Rail(double kilometers) {
        this.meters = (int) (kilometers * 1000);
    }

    public int getPosition() {
        return position;
    }

    public int getMeters() {
        return meters;
    }

    public int moveCar(int howMany) {
        this.position += howMany * 100;
        Utils.TextInConsole.messageWithJumpLine("The car move " + howMany * 100 + "m");
        return getPosition();
    }
}
