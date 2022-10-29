package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {
    private static final String INPUT_FILE_NAME = "players.txt";
    private static final String OUTPUT_FILE_NAME = "players.txt";


    public static List<Player> readFromFile() throws Exception {
        List<Player> PlayerList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            Player p = new Player();
            p.setName(tokens[0]);
            p.setCountry(tokens[1]);
            p.setAge(Integer.parseInt(tokens[2]));
            p.setHeight(Double.parseDouble(tokens[3]));
            p.setClubName(tokens[4]);
            p.setPosition(tokens[5]);
            p.setNumber(Integer.parseInt(tokens[6]));
            p.setWeeklySalary(Double.parseDouble(tokens[7]));
            PlayerList.add(p);
        }
        br.close();
        return PlayerList;
    }

    public static void writeToFile(List<Player> PlayerList) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));

        for (Player p : PlayerList) {
            bw.write(p.getName() + "," + p.getCountry() + "," + p.getAge() + "," + p.getHeight() + "," + p.getClubName() + "," + p.getPosition() + "," + p.getNumber() + "," + p.getWeeklySalary());
            bw.write("\n");
        }
        bw.close();
    }
    public static List<Club> ReturnClubs() throws Exception {
            List<Club> ClubList= new ArrayList<>();
            List<Player> ClubPlayerList = new ArrayList();
            ClubPlayerList=readFromFile();

            if(ClubList.isEmpty())
            {
                Club c=new Club(ClubPlayerList.get(0).getClubName());
                ClubList.add(c);
            }

            for(Player p:ClubPlayerList)
            {
                boolean match = false;

                for(int i=0;i<ClubList.size();i++)
                {
                    if(ClubList.get(i).getName().equals(p.getClubName()))
                    {
                        if(ClubList.get(i).getPlayerCount()<7){
                            ClubList.get(i).AddPlayer(p);}
                        match=true;
                        break;
                    }
                }
                if(!match)
                {
                    Club c=new Club(p.getClubName());
                    ClubList.add(c);
                    ClubList.get(ClubList.size()-1).AddPlayer(p);
                }
            }

            return ClubList;

    }

    public static List<Country> ReturnCountry() throws Exception
    {
        List<Country> CountryList=new ArrayList<>();
        List<Player> PlayerList=new ArrayList<>();
        PlayerList=readFromFile();
        if(CountryList.isEmpty())
        {
            Country c=new Country(PlayerList.get(0).getCountry());
            CountryList.add(c);
        }
        for(Player p:PlayerList)
        {
            boolean match = false;

            for(int i=0;i<CountryList.size();i++)
            {
                if(CountryList.get(i).getName().equals(p.getCountry()))
                {

                    CountryList.get(i).AddPlayer(p);
                    match=true;
                    break;
                }
            }

        if(!match)
            {
                Country c=new Country(p.getCountry());
                CountryList.add(c);
                CountryList.get(CountryList.size()-1).AddPlayer(p);
            }
        }

        return CountryList;
    }

    public static List<Country> ReturnCountry(Club club) throws Exception
    {
        List<Country> CountryList=new ArrayList<>();
        List<Player> PlayerList=new ArrayList<>();
        for (int i=0;i<club.getPlayerCount();i++)
        {
            PlayerList.add(club.getPlayers(i));
        }
        if(CountryList.isEmpty())
        {
            Country c=new Country(PlayerList.get(0).getCountry());
            CountryList.add(c);
        }
        for(Player p:PlayerList)
        {
            boolean match = false;

            for(int i=0;i<CountryList.size();i++)
            {
                if(CountryList.get(i).getName().equals(p.getCountry()))
                {

                    CountryList.get(i).AddPlayer(p);
                    match=true;
                    break;
                }
            }

            if(!match)
            {
                Country c=new Country(p.getCountry());
                CountryList.add(c);
                CountryList.get(CountryList.size()-1).AddPlayer(p);
            }
        }

        return CountryList;
    }

}



