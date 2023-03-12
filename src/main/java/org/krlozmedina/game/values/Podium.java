package org.krlozmedina.game.values;

import org.krlozmedina.game.Player;

public class Podium {
    Player firstPlace;
    Player secondPlace;
    Player thirdPlace;
    Boolean isFull;

    public void assignFirstPlace(Player firstPlace) {
        this.firstPlace = firstPlace;
    }

    public void assignSecondPlace(Player secondPlace) {
        this.secondPlace = secondPlace;
    }

    public void assignThirdPlace(Player thirdPlace) {
        this.thirdPlace = thirdPlace;
    }

    public Player getFirstPlace() {
        return firstPlace;
    }

    public Player getSecondPlace() {
        return secondPlace;
    }

    public Player getThirdPlace() {
        return thirdPlace;
    }
}
