package util;

import java.io.Serializable;

public class BuyPlayerDTO implements Serializable {
    private volatile Player player;
    private volatile String Buyer;

    public String getBuyer() {
        return Buyer;
    }

    public void setBuyer(String buyer) {
        Buyer = buyer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
