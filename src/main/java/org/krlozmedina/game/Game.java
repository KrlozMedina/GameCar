package org.krlozmedina.game;

import org.krlozmedina.Colors;
import org.krlozmedina.Utils;
import org.krlozmedina.car.Driver;
import org.krlozmedina.game.values.Podium;
import org.krlozmedina.game.values.Track;

import java.util.Objects;
import java.util.Scanner;

public class Game {
    // Server data --------------------------
    public static final String URL_SERVER = "127.0.0.1";
    public static final String PORT_SERVER = "3306";
    public static final String USERNAME_SERVER = "root";
    public static final String PASSWORD_SERVER = "";
    //----------------------------------------

    public static final int LENGTH_LINE = 103;

    private int numberPlayers = 0;
    private boolean playing = false;

    private final Scanner scanner = new Scanner(System.in);
    private final Podium podium = new Podium();

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

//    Classes
    private static class VerifyData {
        private static void verifyPlayerColor(Player playerInGame, Player playerInDB) {
            if (!Objects.equals(playerInGame.getColor(), playerInDB.getColor())) {
                Utils.CRUD.updateDataColor(playerInGame.getName(), playerInGame.getColor());
            }
        }

        private static boolean verifyPlayersInDB(Player player) {
            for (Player p : Player.players) {
                if (Objects.equals(player.getName(), p.getName())) {
                    verifyPlayerColor(player, p);
                    return true;
                }
            }
            return false;
        }

        public static void verifyPlayers() {
            for (Player p : Player.playersInGame) {
                if (!verifyPlayersInDB(p)) {
                    Utils.CRUD.createData(p.getName(), p.getColor());
                }
            }
        }
    }

//  Methods
    private void bestScores() {
        Player.players.clear();
        Utils.CRUD.readData();

        Utils.TextInConsole.messageWithJumpLine("BEST SCORES", Colors.GREEN.toString(), Colors.BLACK.toString());

        for (int i = 0; i < Math.min(Player.players.size(), 3); i++) {
            String name = Player.players.get(i).getName();
            String color = Player.players.get(i).getColor();
            int score = Player.players.get(i).getScore();
            Utils.TextInConsole.messageWithJumpLine("   " + name + ":" + score, color, Colors.BLACK.toString());
        }

        Utils.TextInConsole.messageInLine("Click for continue");
        scanner.nextLine();
    }

    private String waitSelectColor() {
        int flagColor = 0;
        do {
            selectColor();
            flagColor = Utils.ErrorHandling.validateNumber(scanner.nextLine());
        } while (flagColor == 0);

        return Colors.values()[flagColor].toString();
    }

    private void selectColor() {
        Utils.TextInConsole.messageWithJumpLine("Select a color");
        for (int i = 1; i < Colors.values().length; i++) {
            Utils.TextInConsole.messageWithJumpLine(" " + (i) + ". " + Colors.values()[i], Colors.values()[i].toString());
        }
    }

    private void createPlayers(int i) {
        Utils.TextInConsole.messageWithJumpLine("What is your nickname player " + i);
        String playerName = scanner.nextLine();
        String carColor = waitSelectColor();
        new Player(playerName.toUpperCase(), carColor);
    }

    private void selectNumberPlayers() {
        String value = scanner.nextLine();
        setNumberPlayers(Utils.ErrorHandling.validateNumber(value));
        Track.setNumberOfRails(getNumberPlayers());

        for (int i = 1; i <= getNumberPlayers(); i++) {
            createPlayers(i);
        }
    }

    private void waitForSelectPlayers() {
        do {
            Utils.TextInConsole.messageWithJumpLine("How many players?");
            selectNumberPlayers();
        } while (getNumberPlayers() == 0);
    }

    private void waitForSelectDistance() {
        do {
            Utils.TextInConsole.messageWithJumpLine("Maximum track distance in Km?");
            String value = scanner.nextLine();
            Track.setKilometers(Utils.ErrorHandling.validateNumberDouble(value));
        } while (getDistanceInKm() == 0);
    }

    private void waitForIsReady() {
        int flagPlaying;

        do {
            Utils.TextInConsole.messageWithJumpLine("You want to change the parameters of the game?");
            Utils.TextInConsole.messageWithJumpLine("   1. Si.");
            Utils.TextInConsole.messageWithJumpLine("   2. No.");
            flagPlaying = Utils.ErrorHandling.validateNumber(scanner.nextLine());
            setPlaying(flagPlaying == 2);
        } while (flagPlaying == 0);

        VerifyData.verifyPlayers();
    }

    private void setParamForGame() {
        do {
            Player.playersInGame.clear();

            waitForSelectDistance();
            waitForSelectPlayers();

            Utils.TextInConsole.messageWithJumpLine("-----------------------------------------------------------------");
            Utils.TextInConsole.messageWithJumpLine("The following players have been created");

            int count = 0;
            for (Player p: Player.playersInGame
            ) {
                count++;
                Utils.TextInConsole.messageWithJumpLine("   " + count + ". " + "Name: " + p.getName());
                Utils.TextInConsole.messageWithJumpLine("      " + "Car color: " + p.getColor());
            }

            Utils.TextInConsole.messageWithJumpLine("");
            Utils.TextInConsole.messageWithJumpLine("The track has " + getDistanceInKm() + "Km");
            Utils.TextInConsole.messageWithJumpLine("-----------------------------------------------------------------");

            waitForIsReady();
        } while (!isPlaying());
    }

    private void showGameInConsole() {
        for (Player p: Player.playersInGame) {
            Utils.TextInConsole.drawRail(p.getName(), p.rail.getPosition(), p.rail.getMeters(), p.car.getColor());
            p.car.setDistance(Utils.Calculated.distanceInConsole(p.rail.getPosition(), p.rail.getMeters()));
            Utils.TextInConsole.drawCar(p.car.getDistance(), p.car.getColor());
        }
        Utils.TextInConsole.drawLineOfRail(LENGTH_LINE);
    }

    private void selectPodium(Player player) {
        if (player.rail.getPosition() >= player.rail.getMeters()) {
            boolean statusPodium;
            statusPodium = podium.assignPlace(player);
            if (!statusPodium) {
                setPlaying(false);
            }
        }
    }

    private void distancePlayer(Player player) {
        if (player.rail.getPosition() < player.rail.getMeters()) {
            Utils.TextInConsole.messageWithJumpLine("Throw dice " + player.getName(), player.car.getColor());

            scanner.nextLine();
            player.rail.moveCar(Driver.throwDice());
            showGameInConsole();

            selectPodium(player);
        }
    }

    private void lestGoToPlay() {
        Utils.TextInConsole.messageWithJumpLine("Begin...");
        showGameInConsole();

        podium.clearPlaces();

        do {
            for (Player p: Player.playersInGame) {
                distancePlayer(p);
            }
        } while (isPlaying());

        Utils.TextInConsole.messageWithJumpLine("Finished");
    }

    private void assignScore() {
        Utils.CRUD.updateDataScore(podium.getFirstPlace().getName(), 2000);
        Utils.CRUD.updateDataScore(podium.getSecondPlace().getName(), 1000);
        Utils.CRUD.updateDataScore(podium.getThirdPlace().getName(), 500);

        Utils.TextInConsole.messageWithJumpLine("First place: " + podium.getFirstPlace().getName(), podium.getFirstPlace().car.getColor());
        Utils.TextInConsole.messageWithJumpLine("Second place: " + podium.getSecondPlace().getName(), podium.getSecondPlace().car.getColor());
        Utils.TextInConsole.messageWithJumpLine("Third place: " + podium.getThirdPlace().getName(), podium.getThirdPlace().car.getColor());
    }

    public void startGame() {
        boolean isPlaying = true;

        do {
            bestScores();
            setParamForGame();
            lestGoToPlay();
            assignScore();

            Utils.TextInConsole.messageWithJumpLine("Re play?");
            Utils.TextInConsole.messageWithJumpLine("1. Si");
            Utils.TextInConsole.messageWithJumpLine("2. No");

            if (Utils.ErrorHandling.validateNumber(scanner.nextLine()) == 2) {
                isPlaying = false;
            }
        } while (isPlaying);

        Utils.TextInConsole.messageWithJumpLine("The game is finished");
    }
}