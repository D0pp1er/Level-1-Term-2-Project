package util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Club implements Serializable {
    private String name;

    public Player[] getPlayers() {
        return players;
    }

    private Player[] players;
    private int playerCount;
    public Club()
    {
        players=new Player[25];
        playerCount=0;
    }
    public Club(String name)
    {
        this.name= name;
        players=new Player[25];
        playerCount=0;
    }


    public void AddPlayer(Player p)
    {
        p.setClubName(name);players[playerCount++]=p;
    }

    public String getName() {
        return name;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public Player getPlayers(int i) {
        return players[i];
    }

    public List<Player> MaxSalary()
    {
        List<Player> Max=new ArrayList<>();
        double max=0;
        for(int i=0;i<playerCount;i++)
        {
            if(max<players[i].getWeeklySalary())
            {
                max=players[i].getWeeklySalary();
            }
        }
        for(int i=0;i<playerCount;i++)
        {
            if(max==players[i].getWeeklySalary())
            {
                Max.add(players[i]);
            }
        }

        return Max;
    }

    public List<Player> MaxAge()
    {
        List<Player> Max=new ArrayList<>();
        int max=0;
        for(int i=0;i<playerCount;i++)
        {
            if(max<players[i].getAge())
            {
                max=players[i].getAge();
            }
        }

        for(int i=0;i<playerCount;i++)
        {
            if(max==players[i].getAge())
            {
                Max.add(players[i]);
            }
        }

        return Max;
    }

    public List<Player> MaxHeight()
    {
        List<Player> Max=new ArrayList<>();
        double max=0;
        for(int i=0;i<playerCount;i++)
        {
            if(max<players[i].getHeight())
            {
                max=players[i].getHeight();
            }
        }
        for(int i=0;i<playerCount;i++)
        {
            if(max==players[i].getHeight())
            {
                Max.add(players[i]);
            }
        }
        return Max;
    }

    public String TotalSalary()
    {
        double salary=0;
        for (int i=0;i<playerCount;i++)
        {
            salary+=players[i].getWeeklySalary();
        }
        salary=salary*52.0;
        NumberFormat format=new DecimalFormat("#0.00");
        String salary_x=format.format(salary);
        return salary_x;
    }

    public void RemovePlayer(Player player)
    {
        for (int i=0;i<playerCount;i++)
        {
            if (player.getName().equals(players[i].getName()))
            {
                for (int j=i;j<(playerCount-1);j++)
                {
                    players[j]=players[j+1];
                }
                playerCount--;
                break;
            }
        }
    }

}
