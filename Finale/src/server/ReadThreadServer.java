package server;

import util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadThreadServer implements Runnable {
    private  Thread thr;
    private NetworkUtil networkUtil;
    public HashMap<String,ClubInfo> clubMap=new HashMap<>();
    private List<Club> ClubList=new ArrayList<>();
    private volatile List<Player> PlayerOnMarket=new ArrayList<>();


    public ReadThreadServer(HashMap<String,ClubInfo> clubMap,List<Club> ClubList,List<Player> PlayerOnMarket, NetworkUtil networkUtil) {
        this.clubMap=new HashMap<>();
        this.clubMap=clubMap;
        this.ClubList=new ArrayList<>();
        this.ClubList=ClubList;
        this.PlayerOnMarket=new ArrayList<>();
        this.PlayerOnMarket=PlayerOnMarket;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        loginDTO.setStatus(false);
                        for (Club c:ClubList)
                        {
                            if(c.getName().equalsIgnoreCase(loginDTO.getUserName()))
                            {
                                if(!clubMap.get(c.getName()).isOnline())
                                {
                                    loginDTO.setStatus(true);
                                    loginDTO.setUserName(c.getName());
                                    clubMap.get(c.getName()).setOnline(true);
                                    clubMap.get(c.getName()).setNetworkUtil(networkUtil);
                                    InitializeClient newClient=new InitializeClient();
                                    newClient.setClub(c);
                                    newClient.setPlayers(PlayerOnMarket);
                                    networkUtil.write(newClient);
                                    break;
                                }

                            }
                        }
                        networkUtil.write(loginDTO);
                    }

                    if (o instanceof LogOutDTO)
                    {
                        LogOutDTO logOutDTO=(LogOutDTO) o;
                        for (Club c:ClubList)
                        {
                            if (c.getName().equalsIgnoreCase(logOutDTO.getName()))
                            {
                                clubMap.get(c.getName()).setOnline(false);
                            }
                        }
                    }
                    synchronized (this) {
                        if (o instanceof SellPlayerDTO) {
                            SellPlayerDTO PlayerToSell = (SellPlayerDTO) o;
                            System.out.println("current market players"+PlayerOnMarket);
                            Player player=PlayerToSell.getPlayer();
                            PlayerOnMarket.add(player);
                            System.out.println("updated market players"+PlayerOnMarket);

                            for (Club c : ClubList) {
                                if (clubMap.get(c.getName()).isOnline()) {
                                    InitializeClient newClient = new InitializeClient();
                                    newClient.setClub(c);
                                    newClient.setPlayers(PlayerOnMarket);
                                    clubMap.get(c.getName()).getNetworkUtil().write(newClient);
                                }
                            }
                        }
                        if (o instanceof BuyPlayerDTO) {
                            BuyPlayerDTO PlayerToBuy = (BuyPlayerDTO) o;
                            Player player = new Player();
                            player = PlayerToBuy.getPlayer();
                            int Index=0;
                            for (int i=0;i<PlayerOnMarket.size();i++) {

                                if (PlayerOnMarket.get(i).getName().equals(player.getName())) {
                                        Index=i;
                                        break;
                                }
                            }
                            PlayerOnMarket.remove(Index);
                            int PreviousClubIndex=0,NewClubIndex=0;
                            for (int i=0;i<ClubList.size();i++)
                            {
                                if (player.getClubName().equals(ClubList.get(i).getName()))
                                {
                                    PreviousClubIndex=i;
                                }
                                if (PlayerToBuy.getBuyer().equals(ClubList.get(i).getName()))
                                {
                                    NewClubIndex=i;
                                }
                            }
                            ClubList.get(PreviousClubIndex).RemovePlayer(player);
                            ClubList.get(NewClubIndex).AddPlayer(player);
                            for (Club c : ClubList) {
                                if (clubMap.get(c.getName()).isOnline()) {
                                    InitializeClient newClient = new InitializeClient();
                                    newClient.setClub(c);
                                    newClient.setPlayers(PlayerOnMarket);
                                    clubMap.get(c.getName()).getNetworkUtil().write(newClient);
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



