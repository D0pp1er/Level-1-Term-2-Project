package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.Club;
import util.LogOutDTO;
import util.Player;
import util.SellPlayerDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class HomeController {

    private Main main;

    @FXML
    private Label message;

    @FXML
    private Label tableTitle;

    @FXML
    private TableView HomePageTable=new TableView<>();
    ObservableList<Player> data= FXCollections.observableArrayList();
    ArrayList<Player> PlayerToShow=new ArrayList<>();
    ArrayList<Player> AllClubPlayers=new ArrayList<>();
    ArrayList<Player> PlayerOnMarket=new ArrayList<>();

    public void setPlayerOnMarket(ArrayList<Player> playerOnMarket) {
        PlayerOnMarket = playerOnMarket;
    }

    Club club=new Club();
    private boolean init=true;

    public void setClub(Club club) {
        this.club = club;
    }

    @FXML
    public void initialize(String msg) {
        message.setText(msg);
        tableTitle.setText("Club Players");
        //club= main.getClub();
        //club.RemovePlayer(club.getPlayers(4));
        for (int i=0;i<club.getPlayerCount();i++)
        {
            AllClubPlayers.add(club.getPlayers(i));
        }
        //PlayerToShow.addAll(Arrays.asList(club.getPlayers()));
        if(init)
        {
            initializeColumns();
            init=false;
        }
        data.addAll(PlayerToShow);
        showPlayerTable(AllClubPlayers );
    }

    @FXML
    public void showPlayerTable(ArrayList<Player> playerToShow) {


        HomePageTable.setItems(null);
        data.removeAll(PlayerToShow);
        PlayerToShow=playerToShow;
        data.addAll(PlayerToShow);
        HomePageTable.setEditable(true);
        HomePageTable.setItems(data);
    }

    @FXML
    public void initializeColumns() {
        TableColumn<Player, String> NameCol = new TableColumn<>("Name");
        NameCol.setMaxWidth(187);
        NameCol.setStyle("-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> CountryCol = new TableColumn<>("Country");
        CountryCol.setMaxWidth(95);
        CountryCol.setStyle("-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        CountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Player, String> ClubNameCol = new TableColumn<>("Club Name");
        ClubNameCol.setMaxWidth(100);
        ClubNameCol.setStyle("-fx-border-color:#90ffb0;-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        ClubNameCol.setCellValueFactory(new PropertyValueFactory<>("clubName"));

        TableColumn<Player, Integer> AgeCol = new TableColumn<>("Age");
        AgeCol.setMaxWidth(60);
        AgeCol.setStyle("-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        AgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Player, Integer> NumberCol = new TableColumn<>("Number");
        NumberCol.setMaxWidth(80);
        NumberCol.setStyle("-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        NumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Player, String> PositionCol = new TableColumn<>("Position");
        PositionCol.setMaxWidth(100);
        PositionCol.setStyle("-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        PositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Player, Double> HeightCol = new TableColumn<>("Height");
        HeightCol.setMaxWidth(70);
        HeightCol.setStyle("-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        HeightCol.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableColumn<Player, Double> SalaryCol = new TableColumn<>(" Salary");
        SalaryCol.setMaxWidth(80);
        SalaryCol.setStyle("-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        SalaryCol.setCellValueFactory(new PropertyValueFactory<>("weeklySalary"));

        TableColumn<Player, String> PriceCol = new TableColumn<>("Price");
        PriceCol.setMaxWidth(70);
        PriceCol.setStyle("-fx-alignment: CENTER; -fx-background-color: #08605d; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-fx-font-name:Century Gothic;-fx-font-size:15.0;");
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        HomePageTable.getColumns().addAll(NameCol,CountryCol,ClubNameCol, AgeCol,NumberCol,PositionCol,HeightCol,SalaryCol,PriceCol);
        ClubNameCol.setVisible(false);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        try {
            LogOutDTO logOutDTO=new LogOutDTO();
            logOutDTO.setName(message.getText());
            main.getNetworkUtil().write(logOutDTO);
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void SellPlayer(ActionEvent actionEvent) {
        SellPlayerDTO playerDTO=new SellPlayerDTO();
        Player player=new Player();
        player=(Player) HomePageTable.getSelectionModel().getSelectedItem();
        if (player==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Sell Request");
            alert.setHeaderText("Invalid Sell Request");
            alert.setContentText("Please select a player before selling");
            alert.showAndWait();
        }
        else
        {
            boolean matched=true;
            if (PlayerOnMarket.isEmpty())
            {
                matched=false;
            }
            if(matched)
            {
                matched=false;
                for (Player p:PlayerOnMarket)
                {
                    if (p.getName().equals(player.getName()))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Sell Request");
                        alert.setHeaderText("Invalid Sell Request");
                        alert.setContentText("You can't sell a player who is already on Market");
                        alert.showAndWait();
                        matched=true;
                        break;
                    }
                }
            }
            if(!matched)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sell Request");
                alert.setHeaderText("Sell Request");
                alert.setContentText("Do you want to sell "+player.getName()+"?");
                Optional<ButtonType> confirmation=alert.showAndWait();
                if(confirmation.get()==ButtonType.CANCEL)
                {
                    RefreshPage(new ActionEvent());
                    return;
                }
                playerDTO.setPlayer(player);
                try {
                    main.getNetworkUtil().write(playerDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        RefreshPage(new ActionEvent());
    }
    @FXML
    public void RefreshPage(ActionEvent actionEvent) {
        try {
            main.showHomePage(club.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void OpenBuyPage(ActionEvent actionEvent) {
        try {
            main.showBuyPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void OpenSearchPage(ActionEvent actionEvent) {
        try {
            main.showSearchPage();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExitAction(ActionEvent actionEvent) {
        try {
            LogOutDTO logOutDTO=new LogOutDTO();
            logOutDTO.setName(message.getText());
            main.getNetworkUtil().write(logOutDTO);
            Platform.exit();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
