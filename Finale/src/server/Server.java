package server;

import util.Club;
import util.FileOperations;
import util.NetworkUtil;
import util.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    public HashMap<String,ClubInfo> clubMap;
    private List<Club> ClubList;
    private List<Player> PlayerOnMarket;

    Server() throws Exception {
        ClubList=new ArrayList<>();
        ClubList= FileOperations.ReturnClubs();
        clubMap=new HashMap<>();
        PlayerOnMarket=new ArrayList<>();
        for (Club c:ClubList)
        {
            ClubInfo info=new ClubInfo();
            clubMap.put(c.getName(),info);
        }

        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReadThreadServer(clubMap,ClubList,PlayerOnMarket, networkUtil);
    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
