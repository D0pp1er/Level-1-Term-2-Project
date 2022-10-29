package sample;

import javafx.application.Platform;
import util.*;

import java.io.IOException;

public class ReadThread implements Runnable {
    private final Thread thr;
    private Main main;

    public ReadThread(Main main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = main.getNetworkUtil().read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        System.out.println(loginDTO.getUserName());
                        System.out.println(loginDTO.isStatus());
                        // the following is necessary to update JavaFX UI components from user created threads
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginDTO.isStatus()) {
                                    try {
                                        main.showHomePage(loginDTO.getUserName());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    main.showAlert();
                                }

                            }
                        });
                    }
                 if (o instanceof InitializeClient)
                    {
                     InitializeClient client=(InitializeClient) o;
                     Platform.runLater(new Runnable() {
                         @Override
                         public void run() {
                             main.setClub(client.getClub());
                             main.setPlayers(client.getPlayers());
                             if (!main.isState())
                             {
                                 try {
                                     main.showBuyPage();
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                             }
                             else if (main.isOnSearch())
                             {
                                 try {
                                     main.showSearchPage();
                                 }
                                 catch (Exception e)
                                 {
                                     e.printStackTrace();
                                 }
                             }
                             else
                             {
                                 try {
                                         main.showHomePage(client.getClub().getName());
                                     }
                                 catch (Exception e)
                                     {
                                         e.printStackTrace();
                                     }

                             }
                         }
                     });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                main.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



