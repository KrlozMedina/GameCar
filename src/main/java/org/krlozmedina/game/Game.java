package org.krlozmedina.game;

import org.krlozmedina.car.Driver;
import org.krlozmedina.game.values.Podium;
import org.krlozmedina.game.values.Track;
import java.util.Scanner;

public class Game {
    private static final int LENGTH_LINE = 103;
    private int numberPlayers = 0;
    private boolean playing = false;
    private final Scanner scanner = new Scanner(System.in);
    private final String[] colors = {"white", "red", "green", "yellow", "blue", "purple", "cyan", "gray"};
    private Podium podium = new Podium();


//  Classes
    public static class TextInConsole {
        private static String defineColorLetter(String colorLetter) {
            return switch (colorLetter) {
                case "black" -> "\u001B[30m";
                case "red" -> "\u001B[31m";
                case "green" -> "\u001B[32m";
                case "yellow" -> "\u001B[33m";
                case "blue" -> "\u001B[34m";
                case "purple" -> "\u001B[35m";
                case "cyan" -> "\u001B[36m";
                case "gray" -> "\u001B[37m";
                default -> "\u001B[38m";
            };
        }

        private static String defineColorBackground(String colorBackground) {
            return switch (colorBackground) {
                case "black" -> "\u001B[40m";
                case "red" -> "\u001B[41m";
                case "green" -> "\u001B[42m";
                case "yellow" -> "\u001B[43m";
                case "blue" -> "\u001B[44m";
                case "purple" -> "\u001B[45m";
                case "cyan" -> "\u001B[46m";
                case "gray" -> "\u001B[47m";
                default -> "\u001B[48m";
            };
        }

        public static void messageInLine(String phrase, String colorFont, String colorBackground) {
            System.out.print(defineColorLetter(colorFont) + defineColorBackground(colorBackground) + phrase);
        }

        public static void messageInOtherLine(String phrase, String colorFont, String colorBackground) {
            System.out.println(defineColorLetter(colorFont) + defineColorBackground(colorBackground) + phrase);
        }

        public static void drawLineOfRail(int length) {
            for (int i = 0; i < length; i++) {
                TextInConsole.messageInLine("-", "", "black");
            }
            TextInConsole.messageInOtherLine("", "", "black");
        }

        public static void drawRailInConsole(String name, int position, int meters, String color) {
            String msg = name + ":" + position + "/" + meters + "m";
            TextInConsole.messageInLine(msg, color, "black");
            drawLineOfRail(LENGTH_LINE - msg.length());
        }

        public static void drawCarInConsole(int distance, String color) {
            Calculated.spaceBeforeCar(distance);
            TextInConsole.messageInLine("}<>)", color, "black");
            TextInConsole.messageInOtherLine("", "", "black");
        }
    }

    private static class ErrorHandling {
        public static int validateNumber(String value) {
            int valueInt;
            try {
                valueInt = Integer.parseInt(value);
                return Math.max(valueInt, 0);
            } catch (Exception e) {
                TextInConsole.messageInLine("Please get into a number. ", "red", "black");
                return 0;
            }
        }

        public static double validateNumberDouble(String value) {
            double valueDouble;
            try {
                valueDouble = Double.parseDouble(value);
                return Math.max(valueDouble, 0);
            } catch (Exception e) {
                TextInConsole.messageInLine("Please get into a number. ", "red", "black");
                return 0;
            }
        }
    }

    private static class Calculated {
        private static int positionPercentage(int position) {
            return position * 100;
        }

        public static int distanceInConsole(int positionActual, int metaInKm) {
            return positionPercentage(positionActual) / metaInKm;
        }

        public static void spaceBeforeCar(int distance) {
            for (int i = 0; i < distance; i++) {
                TextInConsole.messageInLine(" ", "", "black");
            }
        }
    }

//    Getters and Setters
    public int getNumberPlayers() {
        return numberPlayers;
    }

    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    public double getDistanceInKm() {
        return Track.getKilometers();
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

//  Methods
    private String waitSelectColor() {
        int flagColor = 0;
        do {
            selectColor();
            flagColor = ErrorHandling.validateNumber(scanner.nextLine());
        } while (flagColor == 0);
        return colors[flagColor - 1];
    }

    private void selectColor() {
        TextInConsole.messageInOtherLine("Select a color", "", "");
        for (int i = 0; i < colors.length; i++) {
            TextInConsole.messageInOtherLine(" " + (i + 1) + ". " + colors[i], colors[i], "");
        }
    }

    private void createPlayers(int i) {
        TextInConsole.messageInOtherLine("What is your nickname player " + (i + 1), "", "");
        String playerName = scanner.nextLine();
        String carColor = waitSelectColor();
        new Player(playerName, carColor);
    }

    private void selectPlayers() {
        String value = scanner.nextLine();
        setNumberPlayers(ErrorHandling.validateNumber(value));
        Track.setNumberOfRails(getNumberPlayers());

        for (int i = 0; i < getNumberPlayers(); i++) {
            createPlayers(i);
        }
    }

    private void waitForSelectPlayers() {
        do {
            TextInConsole.messageInOtherLine("How many players?", "", "");
            selectPlayers();
        } while (getNumberPlayers() == 0);
    }

    private void waitForSelectDistance() {
        do {
            TextInConsole.messageInOtherLine("Maximum track distance in Km?", "", "");
            String value = scanner.nextLine();
            Track.setKilometers(ErrorHandling.validateNumberDouble(value));
        } while (getDistanceInKm() == 0);
    }

    private void waitForIsReady() {
        int flagPlaying;
        do {
            TextInConsole.messageInOtherLine("You want to change the parameters of the game?", "", "");
            TextInConsole.messageInOtherLine("   1. Si.", "", "");
            TextInConsole.messageInOtherLine("   2. No.", "", "");
            flagPlaying = ErrorHandling.validateNumber(scanner.nextLine());
            setPlaying(flagPlaying == 2);
        } while (flagPlaying == 0);
    }

    private void setParamForGame() {
        do {
            Player.players.clear();

            waitForSelectDistance();
            waitForSelectPlayers();

            TextInConsole.messageInOtherLine("-----------------------------------------------------------------", "", "");
            TextInConsole.messageInOtherLine("The following players have been created", "", "");

            int count = 0;
            for (Player p: Player.players
            ) {
                count++;
                TextInConsole.messageInOtherLine("   " + count + ". " + "Name: " + p.getName(), "", "");
                TextInConsole.messageInOtherLine("      " + "Car color: " + p.getColor(), "", "");
            }

            TextInConsole.messageInOtherLine("", "", "");
            TextInConsole.messageInOtherLine("The track has " + getDistanceInKm() + "Km", "", "");
            TextInConsole.messageInOtherLine("-----------------------------------------------------------------", "", "");

            waitForIsReady();
        } while (!isPlaying());
    }

    private void showGameInConsole() {
        for (Player p: Player.players) {
            TextInConsole.drawRailInConsole(p.getName(), p.rail.getPosition(), p.rail.getMeters(), p.car.getColor());
            p.car.setDistance(Calculated.distanceInConsole(p.rail.getPosition(), p.rail.getMeters()));
            TextInConsole.drawCarInConsole(p.car.getDistance(), p.car.getColor());
        }
        TextInConsole.drawLineOfRail(LENGTH_LINE);
    }

    private void lestGoToPlay() {
        showGameInConsole();
        do {
            for (Player p: Player.players) {
                if (p.rail.getPosition() < p.rail.getMeters()) {
                    TextInConsole.messageInOtherLine("Throw dice " + p.getName(), p.car.getColor(), "");

                    scanner.nextLine();
                    p.rail.moveCar(Driver.throwDice());
                    showGameInConsole();

                    if (p.rail.getPosition() >= p.rail.getMeters()) {
                        boolean statusPodium;
                        statusPodium = podium.assignPlace(p);
                        if (!statusPodium) {
                            setPlaying(false);
                            break;
                        }
                    }
                }
            }
        } while (isPlaying());
    }

    public void startGame() {
        setParamForGame();
        TextInConsole.messageInOtherLine("Begin...", "", "");
        lestGoToPlay();
        TextInConsole.messageInOtherLine("Finished", "", "");
        TextInConsole.messageInOtherLine("First place: " + podium.getFirstPlace().getName(), podium.getFirstPlace().car.getColor(), "");
        TextInConsole.messageInOtherLine("Second place: " + podium.getSecondPlace().getName(), podium.getSecondPlace().car.getColor(), "");
        TextInConsole.messageInOtherLine("Third place: " + podium.getThirdPlace().getName(), podium.getThirdPlace().car.getColor(), "");
    }
}

/*
    1. Crear juego con jugadores, el juego debe tener los limites de kilómetros por cada pista
    (un jugador puede ser un conductor y un conductor debe tener un carro asociado y un carro
    debe estar asociado a un pista)
*/

/*
    2. Iniciar el juego, con un identificado se debe iniciar el juego, se debe tener la lista
    de carros en donde se pueda iterar y avanzar según la posición de la pista, esto debe ser
    de forma aleatoria.
*/