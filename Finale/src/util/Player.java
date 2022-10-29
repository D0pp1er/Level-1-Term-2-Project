package util;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Player implements Serializable {
    private String name;
    private String country;
    private String clubName;
    private int age;
    private int number;
    private String position;
    private double height;
    private double weeklySalary;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;

    public Player()
    {

    }

    public Player(String name,String country,int age,double height,String clubName,String position,int number,double weeklySalary)
    {
        this.name=name;
        this.country=country;
        this.age=age;
        this.height=height;
        this.clubName=clubName;
        this.position=position;
        this.number=number;
        this.weeklySalary=weeklySalary;
        price= (int)weeklySalary / 2000 +".0M";
    }

    @Override
    public String toString() {
        return getName() + "," + getCountry() + "," + getAge() + "," + getHeight() + "," + getClubName() + "," + getPosition() + "," + getNumber() + "," + getWeeklySalary();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeeklySalary(double weeklySalary) {
        this.weeklySalary = weeklySalary;
        price=(int) weeklySalary / 2000 +".0M";
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getClubName() {
        return clubName;
    }

    public int getAge() {
        return age;
    }

    public int getNumber() {
        return number;
    }

    public String getPosition() {
        return position;
    }

    public double getHeight() {
        return height;
    }

    public double getWeeklySalary() {
        return weeklySalary;
    }

    public static Player InPut() throws Exception {
        Player p1=new Player();
        String[] data= new String[8];
        Scanner scn=new Scanner(System.in);
        boolean wromgInput=false;
        for (int i=0;i<8;i++)
        {
            data[i]=scn.nextLine();
        }
        try {
            p1.setName(data[0]);
            p1.setCountry(data[1]);
            p1.setAge(Integer.parseInt(data[2]));
            p1.setHeight(Double.parseDouble(data[3]));
            p1.setClubName(data[4]);
            p1.setPosition(data[5]);
            p1.setNumber(Integer.parseInt(data[6]));
            p1.setWeeklySalary(Double.parseDouble(data[7]));

        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("    Your input is invalid");
            wromgInput=true;
        }
        finally {
            if(wromgInput){
                //MainMenu.Show();
                }
            return p1;
        }

    }

    public static void Insert(List<Player> PlayersList, List<Club> ClubList,List<Country> CountryList) throws Exception {

        Player p= InPut();
        boolean matched=false;
        if(!CheckName(p,PlayersList)) {
            for (Club c : ClubList) {
                if (c.getName().equals(p.getClubName())) {
                    matched = true;
                    if (c.getPlayerCount() < 7) {
                        PlayersList.add(p);
                        c.AddPlayer(p);
                        break;
                    } else System.out.println("     Error! Club's Player Number exceeds");
                }
            }
            if (!matched) {
                PlayersList.add(p);
                Club c = new Club(p.getClubName());
                ClubList.add(c);
                c.AddPlayer(p);
            }
            boolean CountryMatch=false;
            for(Country country:CountryList)
            {
                if(country.getName().equals(p.getCountry()))
                {
                    CountryMatch=true;
                    country.AddPlayer(p);
                    break;
                }
            }
            if(!CountryMatch)
            {
                Country c=new Country(p.getCountry());
                CountryList.add(c);
                c.AddPlayer(p);
            }
        }

    }

    public static boolean CheckName(Player p, List<Player> PlayersList)
    {
        for(Player player:PlayersList)
        {
            if (player.getName().equals(p.getName()))
            {
                System.out.println("        Two players can't have the same name!");
                return true;
            }
            if((p.getClubName().equals(player.getClubName()))&&(p.getNumber()==player.getNumber()))
            {
                System.out.println("        A club can't have two player with same number");
                return true;
            }
            boolean position=p.getPosition().equals("Goalkeeper")||p.getPosition().equals("Defender")||p.getPosition().equals("Midfielder")||p.getPosition().equals("Forward");
            if(!position)
            {
                System.out.println("        A player can't have your inserted position");
                return true;
            }
        }
        return false;
    }
}
