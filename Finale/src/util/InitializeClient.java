package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InitializeClient implements Serializable {
    private volatile Club club=new Club();
    private volatile List<Player> players=new ArrayList<>();
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }



}
