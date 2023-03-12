package org.krlozmedina.game;

import org.krlozmedina.game.values.Track;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private int numberPlayers = 0;
    private boolean isReady = false;
    private final Scanner scanner = new Scanner(System.in);
    private final Track track = new Track();
    private final ArrayList<Player> players = new ArrayList<>();

//    Getters
    public int getNumberPlayers() {
        return numberPlayers;
    }

    public int getDistanceInKm() {
        return track.getKilometers();
    }

//    Setters
    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

//    Private methods
    private void bucleForPlayers() {
        do {
            System.out.println("How many players?");
            selectPlayers();
        } while (getNumberPlayers() == 0);
    }

    private void bucleForDistance() {
        do {
            System.out.println("Maximum track distance in Km?");
            selectDistance();
        } while (getDistanceInKm() == 0);
    }

    private void bucleForStartGame() {
        int intIsReady;
        do {
            System.out.println("You want to change the parameters of the game?");
            System.out.println("   1. Si.");
            System.out.println("   2. No.");
            intIsReady = validateNumberInt(scanner.nextLine());
            isReady = intIsReady == 2;
        } while (intIsReady == 0);
    }

    public void showPlayers() {
        int count = 0;
        for (Player p: players
        ) {
            count++;
            System.out.println("   " + count + ". " + "Name: " + p.getName());
            System.out.println("      " + "Car color: " + p.getColor());
        }
    }

    private void createPlayer(int i){
        Player player = new Player();
        System.out.println("What is your nickname player " + (i + 1));
        player.setName(scanner.nextLine());
        System.out.println("Select a color");
        player.setColor(scanner.nextLine());

        players.add(player);
    }

//    Public methods
    public void startGame() {
        do {
            players.clear();

            bucleForPlayers();
            bucleForDistance();

            System.out.println("-----------------------------------------------------------------");
            System.out.println("The following players have been created");
            showPlayers();
            System.out.println();
            System.out.println("The track has " + getDistanceInKm() + "Km");
            System.out.println("-----------------------------------------------------------------");

            bucleForStartGame();
        } while (!isReady);

        System.out.println("Begin...");
    }

    public int validateNumberInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            System.out.print("Please get into a number. ");
            return 0;
        }
    }

    public void selectPlayers() {
        String value = scanner.nextLine();
        setNumberPlayers(validateNumberInt(value));
        track.setNumberOfRails(getNumberPlayers());

        for (int i = 0; i < getNumberPlayers(); i++) {
            createPlayer(i);
        }
    }

    public void selectDistance() {
        String value = scanner.nextLine();
        track.setKilometers(validateNumberInt(value));
    }
}

/*
 * Crear juego con jugadores, el juego debe tener los limites de kilómetros por cada pista
 * (un jugador puede ser un conductor y un conductor debe tener un carro asociado y un carro
 * debe estar asociado a un pista)
*/