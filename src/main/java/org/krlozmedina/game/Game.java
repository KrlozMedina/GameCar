package org.krlozmedina.game;

import org.krlozmedina.car.Driver;
import org.krlozmedina.game.values.Track;
import java.util.Scanner;

public class Game {
    private int numberPlayers = 0;
    private boolean playing = false;
    private final Scanner scanner = new Scanner(System.in);

//    Getters
    public int getNumberPlayers() {
        return numberPlayers;
    }

    public int getDistanceInKm() {
        return Track.getKilometers();
    }

    public boolean isPlaying() {
        return playing;
    }

    //    Setters
    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    //    Private methods
    private int validateNumber(String value) {
        int valueInt;
        try {
            valueInt = Integer.parseInt(value);
            return Math.max(valueInt, 0);
        } catch (Exception e) {
            System.out.print("Please get into a number. ");
            return 0;
        }
    }

//    30m Black
//    31m Red
//    32m Green
//    33m Yellow
//    34m Blue
//    35m Purple
//    36 Cyan
//    37 Gray

    private void createPlayers(int i) {
        System.out.println("\u001B[40m" + "What is your nickname player " + (i + 1));
        String playerName = scanner.nextLine();
        System.out.println("Select a color");
        String carColor = scanner.nextLine();

        Player player = new Player(playerName, carColor);
    }

    private void selectPlayers() {
        String value = scanner.nextLine();
        setNumberPlayers(validateNumber(value));
        Track.setNumberOfRails(getNumberPlayers());

        for (int i = 0; i < getNumberPlayers(); i++) {
            createPlayers(i);
        }
    }

    private void waitForSelectPlayers() {
        do {
            System.out.println("How many players?");
            selectPlayers();
        } while (getNumberPlayers() == 0);
    }

    private void waitForSelectDistance() {
        do {
            System.out.println("Maximum track distance in Km?");
            String value = scanner.nextLine();
            Track.setKilometers(validateNumber(value));
        } while (getDistanceInKm() == 0);
    }

    private void waitForIsReady() {
        int flagPlaying;
        do {
            System.out.println("You want to change the parameters of the game?");
            System.out.println("   1. Si.");
            System.out.println("   2. No.");
            flagPlaying = validateNumber(scanner.nextLine());
            setPlaying(flagPlaying == 2);
        } while (flagPlaying == 0);
    }

    private void setParamForGame() {
        do {
            Player.players.clear();

            waitForSelectPlayers();
            waitForSelectDistance();

            System.out.println("-----------------------------------------------------------------");
            System.out.println("The following players have been created");

            int count = 0;
            for (Player p: Player.players
            ) {
                count++;
                System.out.println("   " + count + ". " + "Name: " + p.getName());
                System.out.println("      " + "Car color: " + p.getColor());
            }

            System.out.println();
            System.out.println("The track has " + getDistanceInKm() + "Km");
            System.out.println("-----------------------------------------------------------------");

            waitForIsReady();
        } while (!isPlaying());
    }

    private void showGameInConsole() {
        for (Player p: Player.players) {
            System.out.print(p.rail.getPosition() + "/" + Track.getKilometers() * 1000 + "m");
            System.out.println("------------------------------------------------------------------------------------------");

            p.car.setDistance((p.rail.getPosition() * 100) / (Track.getKilometers() * 1000));
//            int position = (p.rail.getPosition() * 100) / (Track.getKilometers() * 1000);
            for (int i = 0; i < p.car.getDistance(); i++) {
                System.out.print(" ");
            }

            if (p.getName().length() >= 4) {
                System.out.println(p.getName().substring(0, 4));
            } else {
                System.out.println(p.getName().substring(0, p.getName().length()));
            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------");

    }

    private void lestGoToPlay() {
        do {
            for (Player p: Player.players) {
                showGameInConsole();
                System.out.print("Throw dice " + p.getName());
                scanner.nextLine();
                p.rail.moveCar(Driver.throwDice());
            }
        } while (isPlaying());
    }

//    Public methods
    public void startGame() {
        setParamForGame();

        System.out.println("Begin...");

        lestGoToPlay();
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