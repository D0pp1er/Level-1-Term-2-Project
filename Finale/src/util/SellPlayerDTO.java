package util;

import java.io.Serializable;

public class SellPlayerDTO implements Serializable {
    private volatile Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
