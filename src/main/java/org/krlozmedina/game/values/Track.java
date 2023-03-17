package org.krlozmedina.game.values;

public class Track {
    private static double kilometers;
    private static int numberOfRails;

//  Getters
    public static double getKilometers() {
        return kilometers;
    }

    public static int getNumberOfRails() {
        return numberOfRails;
    }

//    Setters
    public static void setKilometers(double km) {
        kilometers = km;
    }

    public static void setNumberOfRails(int nRails) {
        numberOfRails = nRails;
    }
}
