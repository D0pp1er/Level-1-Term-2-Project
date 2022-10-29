package server;


import util.*;

public class ClubInfo {
    private boolean isOnline;
    private NetworkUtil networkUtil;

    public ClubInfo()
    {
        isOnline=false;
    }
    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }
}
