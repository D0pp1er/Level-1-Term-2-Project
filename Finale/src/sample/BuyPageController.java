package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BuyPageController {

    private Main main;

    @FXML
    private Label tableTitle;

    @FXML
    private TableView BuyPageTable=new TableView<>();

    ObservableList<Player> data= FXCollections.observableArrayList();
    ArrayList<Player> PlayerOnMarket=new ArrayList<>();
    Club club=new Club();
    private boolean init=true;

    public void setPlayerOnMarket(ArrayList<Player> playerOnMarket) {
        PlayerOnMarket = playerOnMarket;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @FXML
    public void initialize() {

        if(init)
        {
            initializeColumns();
            init=false;
        }
        tableTitle.setText("Welcome,\n"+club.getName());
        data.addAll(PlayerOnMarket);
        BuyPageTable.setEditable(true);
        BuyPageTable.setItems(data);
    }

    @FXML
    public void initializeColumns() {
        String StyleSet="-fx-alignment: CENTER;-fx-background-color: #5382a7; -fx-text-fill:#d6f1f0; -fx-border-width: 5;-font-name:Bodoni MT Condensed;-fx-font-size:15.0;";
        TableColumn<Player, String> NameCol = new TableColumn<>("Name");
        NameCol.setMinWidth(210);
        NameCol.setStyle(StyleSet);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> CountryCol = new TableColumn<>("Country");
        CountryCol.setMinWidth(120);
        CountryCol.setStyle(StyleSet);
        CountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Player, String> ClubNameCol = new TableColumn<>("Current Club");
        ClubNameCol.setMinWidth(170);
        ClubNameCol.setStyle(StyleSet);
        ClubNameCol.setCellValueFactory(new PropertyValueFactory<>("clubName"));

        TableColumn<Player, Integer> AgeCol = new TableColumn<>("Age");
        AgeCol.setMinWidth(80);
        AgeCol.setStyle(StyleSet);
        AgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Player, Integer> NumberCol = new TableColumn<>("Number");
        NumberCol.setMinWidth(70);
        NumberCol.setStyle(StyleSet);
        NumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Player, String> PositionCol = new TableColumn<>("Position");
        PositionCol.setMinWidth(90);
        PositionCol.setStyle(StyleSet);
        PositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Player, Double> HeightCol = new TableColumn<>("Height");
        HeightCol.setMinWidth(80);
        HeightCol.setStyle(StyleSet);
        HeightCol.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableColumn<Player, Double> SalaryCol = new TableColumn<>(" Salary");
        SalaryCol.setMinWidth(100);
        SalaryCol.setStyle(StyleSet);
        SalaryCol.setCellValueFactory(new PropertyValueFactory<>("weeklySalary"));

        TableColumn<Player, String> PriceCol = new TableColumn<>("Price");
        PriceCol.setMinWidth(80);
        PriceCol.setStyle(StyleSet);
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        BuyPageTable.getColumns().addAll(NameCol,CountryCol,ClubNameCol, AgeCol,NumberCol,PositionCol,HeightCol,SalaryCol,PriceCol);

    }

    public void setMain(Main main) {
        this.main=main;
    }

    public void backtoHomePage(ActionEvent actionEvent) {
        try {
            main.showHomePage(club.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void BuyPlayer(ActionEvent actionEvent) {
        BuyPlayerDTO playerDTO=new BuyPlayerDTO();
        Player player=new Player();
        player=(Player) BuyPageTable.getSelectionModel().getSelectedItem();
        if (player==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Buy Request");
            alert.setHeaderText("Invalid Buy Request");
            alert.setContentText("Please select a player before buying");
            alert.showAndWait();
        }
        else
        {
            if(player.getClubName().equals(club.getName()))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Buy Request");
                alert.setHeaderText("Invalid Buy Request");
                alert.setContentText("You can not buy a player of your club");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Buy Request");
                alert.setHeaderText("Buy Request");
                alert.setContentText("Do you want to buy "+player.getName()+" for "+player.getPrice()+" ?");
                Optional<ButtonType> confirmation=alert.showAndWait();
                if(confirmation.get()==ButtonType.CANCEL)
                {
                    try {
                        main.showBuyPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                System.out.println("wanna buy "+player);
                playerDTO.setPlayer(player);
                playerDTO.setBuyer(club.getName());
                try {
                    main.getNetworkUtil().write(playerDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            main.showBuyPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
