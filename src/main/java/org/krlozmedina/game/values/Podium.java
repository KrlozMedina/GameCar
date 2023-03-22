package org.krlozmedina.game.values;

import org.krlozmedina.game.Player;

public class Podium {
    private Player firstPlace;
    private Player secondPlace;
    private Player thirdPlace;
    private Boolean isFull;

    private void assignFirstPlace(Player firstPlace) {
        this.firstPlace = firstPlace;
    }

    public Player getFirstPlace() {
        return firstPlace;
    }

    private void assignSecondPlace(Player secondPlace) {
        this.secondPlace = secondPlace;
    }

    public Player getSecondPlace() {
        return secondPlace;
    }

    private void assignThirdPlace(Player thirdPlace) {
        this.thirdPlace = thirdPlace;
    }

    public Player getThirdPlace() {
        return thirdPlace;
    }

    public boolean assignPlace(Player player) {
        if (getFirstPlace() == null) {
            assignFirstPlace(player);
        } else if (getSecondPlace() == null ) {
            if (getFirstPlace() != player) {
                assignSecondPlace(player);
            }
            assignSecondPlace(player);
        } else if (getThirdPlace() == null) {
            if ((getFirstPlace() != player) && (getSecondPlace() != player)) {
                assignThirdPlace(player);
                return false;
            }
        }
        return true;
    }

    public void clearPlaces() {
        assignFirstPlace(null);
        assignSecondPlace(null);
        assignThirdPlace(null);
    }
}
