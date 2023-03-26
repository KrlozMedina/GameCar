package org.krlozmedina;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.krlozmedina.game.Game;
import org.krlozmedina.game.Player;

import java.sql.*;

public class Utils {
    static int id = 0;

    Utils(){}

//    ------------------------------------------------------------------------------------------------------------------
    /**
     * The class performs the necessary calculations for the movement of the cars in the
     * console and provides the appropriate ratio of the distance traveled against the
     * total distance while maintaining the same dimension.
     */
    public static class Calculated {
        Calculated(){}

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
//    ------------------------------------------------------------------------------------------------------------------

//    ------------------------------------------------------------------------------------------------------------------
    /**
     * The class helps to implement a more user-friendly text in the console for changing
     * text and background colors, as well as for displaying necessary messages such as
     * errors.
     */
    public static class TextInConsole {
        private TextInConsole() {}

        public static void messageInLine(String text, String colorFont, String colorBackground) {
            System.out.print(Colors.getColorLetter(colorFont) + Colors.getColorBackground(colorBackground) + text);
        }

        public static void messageInLine(String text, String colorFont) {
            messageInLine(text, colorFont, "");
        }

        public static void messageInLine(String text) {
            messageInLine(text, "");
        }

        public static void messageWithJumpLine(String text, String colorFont, String colorBackground) {
            messageInLine(text, colorFont, colorBackground);
            System.out.println();
        }

        public static void messageWithJumpLine(String text, String colorFont) {
            messageWithJumpLine(text, colorFont, "");
        }

        public static void messageWithJumpLine(String text) {
            messageWithJumpLine(text, "");
        }

        public static void errorInLine(String text) {
            messageInLine(text, Colors.RED.toString());
        }

        public static void errorWithJumpLine(String text) {
            errorInLine(text);
            System.out.println();
        }

        public static void exceptionError(String text) {
            messageWithJumpLine(text, Colors.GRAY.toString());
        }

        public static void drawLineOfRail(int length) {
            for (int i = 0; i < length; i++) {
                messageInLine("-", "", Colors.BLACK.toString());
            }
            messageWithJumpLine("", "", Colors.BLACK.toString());
        }

        public static void drawRail(String name, int position, int meters, String color) {
            String msg = name + ":" + position + "/" + meters + "m";
            messageInLine(msg, color, Colors.BLACK.toString());
            drawLineOfRail(Game.LENGTH_LINE - msg.length());
        }

        public static void drawCar(int distance, String color) {
            Calculated.spaceBeforeCar(distance);
            messageInLine("}<>)", color, Colors.BLACK.toString());
            messageWithJumpLine("", "", Colors.BLACK.toString());
        }
    }
//    ------------------------------------------------------------------------------------------------------------------

//    ------------------------------------------------------------------------------------------------------------------

    /**
     * CRUD (Create, Read, Update, Delete) helps to manage databases.
     */
    public static class CRUD {
        CRUD(){}

        private static final String URL = "jdbc:mysql://" + Game.URL_SERVER + ":" + Game.PORT_SERVER + "/GameCar";

        private static Connection getConnection() {
            Connection conn = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.setProperty("database.username", Game.USERNAME_SERVER);
                System.setProperty("database.password", Game.PASSWORD_SERVER);
                String username = System.getProperty("database.username");
                String password = System.getProperty("database.password");
                conn = DriverManager.getConnection(URL, username, password);
            } catch (SQLException | ClassNotFoundException e) {
                TextInConsole.exceptionError(e.getMessage());
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
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
                TextInConsole.exceptionError(e.getMessage());
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
                TextInConsole.exceptionError(e.getMessage());
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
                TextInConsole.exceptionError(e.getMessage());
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
                    TextInConsole.errorWithJumpLine("Database empty");
                } else {
                    do {
                        new Player( res.getString("name"),
                                res.getString("color"),
                                res.getInt("score"));
                    } while (res.next());
                }

                conn.close();
            } catch (Exception e) {
                TextInConsole.exceptionError(e.getMessage());
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
                TextInConsole.exceptionError(e.getMessage());
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
                TextInConsole.exceptionError(e.getMessage());
            }
        }
    }
//    ------------------------------------------------------------------------------------------------------------------

//    ------------------------------------------------------------------------------------------------------------------

    /**
     * Handling errors during the code flow allows the code to not break and maintain
     * the flow.
     */
    public static class ErrorHandling {
        ErrorHandling(){}

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
//    ------------------------------------------------------------------------------------------------------------------
}
