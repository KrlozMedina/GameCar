package org.krlozmedina.game.values;

public class Track {
    private static int kilometers;
    private static int numberOfRails;

//  Getters
    public static int getKilometers() {
        return kilometers;
    }

    public static int getNumberOfRails() {
        return numberOfRails;
    }

//    Setters
    public static void setKilometers(int km) {
        kilometers = km;
    }

    public static void setNumberOfRails(int nRails) {
        numberOfRails = nRails;
    }
}
