package org.krlozmedina.game;

import org.krlozmedina.Colors;
import org.krlozmedina.car.Driver;
import org.krlozmedina.game.values.Podium;
import org.krlozmedina.game.values.Track;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;
import java.util.Scanner;

public class Game {
    // Server data --------------------------
    static final String URL_SERVER = "127.0.0.1";
    static final String PORT_SERVER = "3306";
    static final String USERNAME_SERVER = "root";
    static final String PASSWORD_SERVER = "";
    //----------------------------------------

    static int id = 0;
    static final int LENGTH_LINE = 103;

    private int numberPlayers = 0;
    private boolean playing = false;

    private final Scanner scanner = new Scanner(System.in);
    private final Podium podium = new Podium();

//  Classes
    private static class Calculated {
        private static int positionPercentage(int position) {
            return position * 100;
        }

        public static int distanceInConsole(int positionActual, int goalInMeters) {
            return positionPercentage(positionActual) / goalInMeters;
        }

        public static void spaceBeforeCar(int distance) {
            for (int i = 0; i < distance; i++) {
                TextInConsole.messageInLine(" ", "", Colors.BLACK.toString());
            }
        }
    }

    public static class TextInConsole {
        private TextInConsole() {}

        private static String defineColorLetter(String colorLetter) {
            return switch (colorLetter) {
                case "BLACK" -> "\u001B[30m";
                case "RED" -> "\u001B[31m";
                case "GREEN" -> "\u001B[32m";
                case "YELLOW" -> "\u001B[33m";
                case "BLUE" -> "\u001B[34m";
                case "PURPLE" -> "\u001B[35m";
                case "CYAN" -> "\u001B[36m";
                case "GRAY" -> "\u001B[37m";
                default -> "\u001B[38m";
            };
        }

        private static String defineColorBackground(String colorBackground) {
            return switch (colorBackground) {
                case "BLACK" -> "\u001B[40m";
                case "RED" -> "\u001B[41m";
                case "GREEN" -> "\u001B[42m";
                case "YELLOW" -> "\u001B[43m";
                case "BLUE" -> "\u001B[44m";
                case "PURPLE" -> "\u001B[45m";
                case "CYAN" -> "\u001B[46m";
                case "GRAY" -> "\u001B[47m";
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
                messageInLine("-", "", Colors.BLACK.toString());
            }
            messageInOtherLine("", "", Colors.BLACK.toString());
        }

        public static void drawRail(String name, int position, int meters, String color) {
            String msg = name + ":" + position + "/" + meters + "m";
            messageInLine(msg, color, Colors.BLACK.toString());
            drawLineOfRail(LENGTH_LINE - msg.length());
        }

        public static void drawCar(int distance, String color) {
            Calculated.spaceBeforeCar(distance);
            messageInLine("}<>)", color, Colors.BLACK.toString());
            messageInOtherLine("", "", Colors.BLACK.toString());
        }
    }

    private static class CRUD {
        private static final String URL = "jdbc:mysql://" + URL_SERVER + ":" + PORT_SERVER + "/GameCar";

        private static Connection getConnection() {
            Connection conn = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.setProperty("database.username", USERNAME_SERVER);
                System.setProperty("database.password", PASSWORD_SERVER);
                String username = System.getProperty("database.username");
                String password = System.getProperty("database.password");
                conn = DriverManager.getConnection(URL, username, password);
            } catch (Exception e) {
                TextInConsole.messageInOtherLine(e.toString(), Colors.RED.toString(), "");
            }

            return conn;
        }

        private static int getColorOfCar(String color) {
            int id = 0;
            try {
                Connection conn = getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT * \n" +
                                                                "FROM Cars WHERE color = ?");
                ps.setString(1, color);

                ResultSet res = ps.executeQuery();
                res.next();

                id = res.getInt("idCar");

                conn.close();
            } catch (Exception e){
                TextInConsole.messageInOtherLine(e.toString(), Colors.RED.toString(), "");
            }
            return id;
        }

        private static int getScore(String name) {
            int score = 0;
            try {
                Connection conn = getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT * \n" +
                                                                "FROM Players \n" +
                                                                "WHERE name = ?");
                ps.setString(1, name);

                ResultSet res = ps.executeQuery();
                res.next();

                score = res.getInt("score");

                conn.close();
            } catch (Exception e){
                TextInConsole.messageInOtherLine(e.toString(), Colors.RED.toString(), "");
            }
            return score;
        }

        public static void createData(String name, String color) {
            try {

                Connection conn = getConnection();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO Players \n" +
                                                                "VALUES (?, ?, ?, ?)");
                ps.setInt(1, ++id);
                ps.setInt(2, getColorOfCar(color));
                ps.setString(3, name);
                ps.setInt(4, 0);
                ps.executeUpdate();

                conn.close();
            } catch (Exception e) {
                TextInConsole.messageInOtherLine(e.toString(), Colors.RED.toString(), "");
            }
        }

        public static void readData() {
            try {
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Players, Cars \n" +
                                                                "WHERE Players.idCar = Cars.idCar \n" +
                                                                "ORDER BY score DESC");
                ResultSet res = ps.executeQuery();

                if (!res.next()) {
                    TextInConsole.messageInOtherLine("Database empty", Colors.RED.toString(), "");
                } else {
                    do {
                        new Player( res.getString("name"),
                                    res.getString("color"),
                                    res.getInt("score"));
                    } while (res.next());
                }

                conn.close();
            } catch (Exception e) {
                TextInConsole.messageInOtherLine(e.toString(), Colors.RED.toString(), "");
            }

            id = Player.players.size();
        }

        public static void updateDataColor(String name, String color) {
           try {
               Connection conn = getConnection();
               PreparedStatement ps = conn.prepareStatement("UPDATE Players \n" +
                                                                "SET idCar = ? \n" +
                                                                "WHERE name = ?");
               ps.setInt(1, getColorOfCar(color));
               ps.setString(2, name);
               ps.executeUpdate();
               conn.close();
           } catch (Exception e) {
               TextInConsole.messageInOtherLine(e.toString(), Colors.RED.toString(), "");
           }
        }

        public static void updateDataScore(String name, int score) {
            try {
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE Players \n" +
                                                                "SET score = ? \n" +
                                                                "WHERE name = ?");
                ps.setInt(1, getScore(name) + score);
                ps.setString(2, name);
                ps.executeUpdate();
                conn.close();
            } catch (Exception e) {
                TextInConsole.messageInOtherLine(e.toString(), Colors.RED.toString(), "");
            }
        }
    }

    private static class ErrorHandling {
        public static int validateNumber(String value) {
            int valueInt;
            try {
                valueInt = Integer.parseInt(value);
                return Math.max(valueInt, 0);
            } catch (Exception e) {
                TextInConsole.messageInLine("Please get into a number. ", Colors.RED.toString(), Colors.BLACK.toString());
                return 0;
            }
        }

        public static double validateNumberDouble(String value) {
            double valueDouble;
            try {
                valueDouble = Double.parseDouble(value);
                return Math.max(valueDouble, 0);
            } catch (Exception e) {
                TextInConsole.messageInLine("Please get into a number. ", Colors.RED.toString(), Colors.BLACK.toString());
                return 0;
            }
        }
    }

    private static class VerifyData {
        private static void verifyPlayerColor(Player playerInGame, Player playerInDB) {
            if (!Objects.equals(playerInGame.getColor(), playerInDB.getColor())) {
                CRUD.updateDataColor(playerInGame.getName(), playerInGame.getColor());
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
                    CRUD.createData(p.getName(), p.getColor());
                }
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
    private void bestScores() {
        Player.players.clear();
        CRUD.readData();

        TextInConsole.messageInOtherLine("BEST SCORES", Colors.GREEN.toString(), Colors.BLACK.toString());

        for (int i = 0; i < Math.min(Player.players.size(), 3); i++) {
            String name = Player.players.get(i).getName();
            String color = Player.players.get(i).getColor();
            int score = Player.players.get(i).getScore();
            TextInConsole.messageInOtherLine("   " + name + ":" + score, color, Colors.BLACK.toString());
        }

        TextInConsole.messageInLine("Click for continue", "", "");
        scanner.nextLine();
    }

    private String waitSelectColor() {
        int flagColor = 0;
        do {
            selectColor();
            flagColor = ErrorHandling.validateNumber(scanner.nextLine());
        } while (flagColor == 0);

        return Colors.values()[flagColor].toString();
    }

    private void selectColor() {
        TextInConsole.messageInOtherLine("Select a color", "", "");
        for (int i = 1; i < Colors.values().length; i++) {
            TextInConsole.messageInOtherLine(" " + (i) + ". " + Colors.values()[i], Colors.values()[i].toString(), "");
        }
    }

    private void createPlayers(int i) {
        TextInConsole.messageInOtherLine("What is your nickname player " + i, "", "");
        String playerName = scanner.nextLine();
        String carColor = waitSelectColor();
        new Player(playerName.toUpperCase(), carColor);
    }

    private void selectNumberPlayers() {
        String value = scanner.nextLine();
        setNumberPlayers(ErrorHandling.validateNumber(value));
        Track.setNumberOfRails(getNumberPlayers());

        for (int i = 1; i <= getNumberPlayers(); i++) {
            createPlayers(i);
        }
    }

    private void waitForSelectPlayers() {
        do {
            TextInConsole.messageInOtherLine("How many players?", "", "");
            selectNumberPlayers();
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

        VerifyData.verifyPlayers();
    }

    private void setParamForGame() {
        do {
            Player.playersInGame.clear();

            waitForSelectDistance();
            waitForSelectPlayers();

            TextInConsole.messageInOtherLine("-----------------------------------------------------------------", "", "");
            TextInConsole.messageInOtherLine("The following players have been created", "", "");

            int count = 0;
            for (Player p: Player.playersInGame
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
        for (Player p: Player.playersInGame) {
            TextInConsole.drawRail(p.getName(), p.rail.getPosition(), p.rail.getMeters(), p.car.getColor());
            p.car.setDistance(Calculated.distanceInConsole(p.rail.getPosition(), p.rail.getMeters()));
            TextInConsole.drawCar(p.car.getDistance(), p.car.getColor());
        }
        TextInConsole.drawLineOfRail(LENGTH_LINE);
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
            TextInConsole.messageInOtherLine("Throw dice " + player.getName(), player.car.getColor(), "");

            scanner.nextLine();
            player.rail.moveCar(Driver.throwDice());
            showGameInConsole();

            selectPodium(player);
        }
    }

    private void lestGoToPlay() {
        TextInConsole.messageInOtherLine("Begin...", "", "");
        showGameInConsole();

        podium.clearPlaces();

        do {
            for (Player p: Player.playersInGame) {
                distancePlayer(p);
            }
        } while (isPlaying());

        TextInConsole.messageInOtherLine("Finished", "", "");
    }

    private void assignScore() {
        CRUD.updateDataScore(podium.getFirstPlace().getName(), 2000);
        CRUD.updateDataScore(podium.getSecondPlace().getName(), 1000);
        CRUD.updateDataScore(podium.getThirdPlace().getName(), 500);

        TextInConsole.messageInOtherLine("First place: " + podium.getFirstPlace().getName(), podium.getFirstPlace().car.getColor(), "");
        TextInConsole.messageInOtherLine("Second place: " + podium.getSecondPlace().getName(), podium.getSecondPlace().car.getColor(), "");
        TextInConsole.messageInOtherLine("Third place: " + podium.getThirdPlace().getName(), podium.getThirdPlace().car.getColor(), "");
    }

    public void startGame() {
        boolean isPlaying = true;

        do {
            bestScores();
            setParamForGame();
            lestGoToPlay();
            assignScore();

            TextInConsole.messageInOtherLine("Re play?", "", "");
            TextInConsole.messageInOtherLine("1. Si", "", "");
            TextInConsole.messageInOtherLine("2. No", "", "");

            if (ErrorHandling.validateNumber(scanner.nextLine()) == 2) {
                isPlaying = false;
            }
        } while (isPlaying);

        TextInConsole.messageInOtherLine("The game is finished", "", "");
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

/*
    3. Se debe seleccionar primer, segundo y tercer lugar en la medida que los carros llegan a
    la meta (final del recorrido), crear el objeto objeto podio.
*/

/*
    4. Se debe persistir los resultados con los nombres de los conductores y agregar un contador
    de las veces que ha ganado.
*/