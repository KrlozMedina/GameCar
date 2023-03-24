package org.krlozmedina;

public enum Colors {
    BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, GRAY, WHITE;

    public static String getColorLetter(String color) {
        return switch (color) {
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

    public static String getColorBackground(String color) {
        return switch (color) {
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
}