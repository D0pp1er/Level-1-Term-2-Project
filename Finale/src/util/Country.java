package util;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private String name;
    private List<Player> PlayerList;
    private int PlayerCount;

    Country()
    {
        name=new String();
        PlayerList=new ArrayList<>();
        PlayerCount=0;
    }
    Country(String name)
    {
        this.name=name;
        PlayerList=new ArrayList<>();
        PlayerCount=0;
    }
    public void AddPlayer(Player p)
    {
        PlayerList.add(p);
        PlayerCount++;
    }

    public int getPlayerCount() {
        return PlayerCount;
    }

    public String getName() {
        return name;
    }
}
