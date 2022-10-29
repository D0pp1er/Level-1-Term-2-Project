package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.*;


import java.util.ArrayList;
import java.util.List;

public class SearchController {
    private Main main;

    @FXML
    private Label message;
    @FXML
    private Label YearlySalary;
    @FXML
    private TextField Input_1;
    @FXML
    private TextField Input_2;
    @FXML
    private ChoiceBox<String> choiceBox=new ChoiceBox<>();
    @FXML
    private TableView SearchPageTable=new TableView<>();
    ObservableList<Player> data= FXCollections.observableArrayList();
    ObservableList<Country> cpdata= FXCollections.observableArrayList();
    ArrayList<Player> PlayerToShow=new ArrayList<>();

    ArrayList<Player> AllClubPlayers=new ArrayList<>();

    Club club=new Club();
    private boolean init=true;
    private boolean cwpcount=false;
    //variables
    String StyleSet="-fx-alignment: CENTER;-fx-background-color:#08605d; -fx-text-fill:#d6f1f0;-font-name:Bodoni MT Condensed;-fx-font-size:15.0;";
    TableColumn<Player, String> NameCol = new TableColumn<>("Name");
    TableColumn<Player, String> CountryCol = new TableColumn<>("Country");
    TableColumn<Player, Integer> AgeCol = new TableColumn<>("Age");
    TableColumn<Player, Integer> NumberCol = new TableColumn<>("Number");
    TableColumn<Player, String> PositionCol = new TableColumn<>("Position");
    TableColumn<Player, Double> HeightCol = new TableColumn<>("Height");
    TableColumn<Player, Double> SalaryCol = new TableColumn<>(" Salary");
    TableColumn<Country, String> CountryNameCol = new TableColumn<>("Country");
    TableColumn<Country, Integer> CountCol = new TableColumn<>("Player Count");

    public void setAllClubPlayers(ArrayList<Player> allClubPlayers) {
        AllClubPlayers = allClubPlayers;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    

    public void setClub(Club club) {
        this.club = club;
    }

    @FXML
    public void initializeColumns() {

        NameCol.setMinWidth(210);
        NameCol.setStyle(StyleSet);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));


        CountryCol.setMinWidth(120);
        CountryCol.setStyle(StyleSet);
        CountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        AgeCol.setMinWidth(80);
        AgeCol.setStyle(StyleSet);
        AgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));


        NumberCol.setMinWidth(100);
        NumberCol.setStyle(StyleSet);
        NumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));


        PositionCol.setMinWidth(123);
        PositionCol.setStyle(StyleSet);
        PositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));


        HeightCol.setMinWidth(80);
        HeightCol.setStyle(StyleSet);
        HeightCol.setCellValueFactory(new PropertyValueFactory<>("height"));


        SalaryCol.setMinWidth(100);
        SalaryCol.setStyle(StyleSet);
        SalaryCol.setCellValueFactory(new PropertyValueFactory<>("weeklySalary"));

        SearchPageTable.getColumns().addAll(NameCol,CountryCol, AgeCol,NumberCol,PositionCol,HeightCol,SalaryCol);
        cwpcount=false;
        CountryNameCol.setMinWidth(414);
        CountryNameCol.setStyle(StyleSet);
        CountryNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));


        CountCol.setMinWidth(400);
        CountCol.setStyle(StyleSet);
        CountCol.setCellValueFactory(new PropertyValueFactory<>("PlayerCount"));

        YearlySalary.setVisible(false);

        choiceBox.getItems().addAll("Player Name","Country","Position","Salary Range","Max Salary","Max Age","Max Height");
        choiceBox.setValue("Player Name");
        choiceBox.setStyle("-fx-alignment: CENTER; -fx-text-fill:#2b2929;-font-name:Bodoni MT;-fx-font-size:20.0;");

    }

    @FXML
    public void initialize() {
        for (int i=0;i<club.getPlayerCount();i++)
        {
            AllClubPlayers.add(club.getPlayers(i));
        }
        if(init)
        {
            initializeColumns();
            init=false;
        }
        cwpcount=false;
        message.setText("Welcome,\n"+club.getName());
        data.addAll(PlayerToShow);
        showPlayerTable(PlayerToShow);
    }

    @FXML
    public void showPlayerTable(ArrayList<Player> playerToShow) {
        if(cwpcount)resetTable();
        SearchPageTable.setItems(null);
        data.removeAll(PlayerToShow);
        PlayerToShow=playerToShow;
        data.addAll(PlayerToShow);
        SearchPageTable.setEditable(true);
        SearchPageTable.setItems(data);
    }

    public void resetTable() {
        SearchPageTable.getColumns().removeAll(CountryNameCol,CountCol);
        SearchPageTable.getColumns().addAll(NameCol,CountryCol, AgeCol,NumberCol,PositionCol,HeightCol,SalaryCol);
    }

    public void RefreshClicked(ActionEvent actionEvent) {
        init=true;
        try {
            main.showSearchPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void YearlyCost(ActionEvent actionEvent) {
        YearlySalary.setText("Yearly cost of "+club.getName()+" is :"+club.TotalSalary());
        YearlySalary.setVisible(true);
    }

    public void BacktoHome(ActionEvent actionEvent) {
        init=true;
        try {
            main.showHomePage(club.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetClicked(ActionEvent actionEvent) {
        Input_1.setText(null);
        Input_2.setText(null);
    }

    public void CWPC_Clicked(ActionEvent actionEvent) {
        cwpcount=true;
        SearchPageTable.getColumns().removeAll(NameCol,CountryCol, AgeCol,NumberCol,PositionCol,HeightCol,SalaryCol);
        SearchPageTable.getColumns().addAll(CountryNameCol,CountCol);
        SearchPageTable.setItems(null);
        List<Country> CountryList=new ArrayList<>();
        try {
            CountryList=FileOperations.ReturnCountry(club);
        } catch (Exception e) {
            System.out.println("Problem in CountryList");
        }
        cpdata.clear();
        cpdata.addAll(CountryList);
        SearchPageTable.setEditable(true);
        SearchPageTable.setItems(cpdata);
    }

    public void SearchClicked(ActionEvent actionEvent) {
        String F_input=Input_1.getText();
        String S_input=Input_2.getText();
        String Option=choiceBox.getValue();
        ArrayList<Player> Result=new ArrayList<>();
        if (Option.equals("Max Salary"))
        {
            Result= (ArrayList<Player>) club.MaxSalary();
            showPlayerTable(Result);
        }
        else if (Option.equals("Max Age"))
        {
            Result= (ArrayList<Player>) club.MaxAge();
            showPlayerTable(Result);
        }
        else if(Option.equals("Max Height"))
        {
            Result= (ArrayList<Player>) club.MaxHeight();
            showPlayerTable(Result);
        }
        else if (Option.equals("Salary Range"))
        {
            boolean properinput = true;
            double r1 = 0, r2 = 0;
            try {
                r1 = Double.parseDouble(F_input);
                r2 = Double.parseDouble(S_input);
                if (r1 > r2)
                    throw new Exception();
            } catch (Exception e) {
                properinput = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please give appropriate salary");
                alert.showAndWait();
            }
            if (properinput) {
                for (Player p : AllClubPlayers) {
                    if ((p.getWeeklySalary() >= r1) && (p.getWeeklySalary() <= r2)) {
                        Result.add(p);
                    }
                }
            }
            if(Result.isEmpty()&&properinput)ShowAlert(Option);
            showPlayerTable(Result);
        }
        else if(Option.equals("Position"))
        {
            String Position=F_input;
            boolean match = Position.equalsIgnoreCase("Goalkeeper") || Position.equalsIgnoreCase("Defender") || Position.equalsIgnoreCase("Midfielder") || Position.equalsIgnoreCase("Forward");
            if (!match) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Position");
                alert.setHeaderText("Invalid Position");
                alert.setContentText("Please give appropriate position");
                alert.showAndWait();
                RefreshClicked(new ActionEvent());
                return;
            }
            else {
                for (Player p : AllClubPlayers) {
                    if (p.getPosition().equalsIgnoreCase(Position)) {
                        Result.add(p);
                    }
                }
            }
            if(Result.isEmpty()&&match)ShowAlert(Option);
            showPlayerTable(Result);
        }
        else if(Option.equals("Country"))
        {
            for (Player p:AllClubPlayers)
            {
                if(p.getCountry().equalsIgnoreCase(F_input))
                {
                        Result.add(p);
                }
            }
            if(Result.isEmpty())ShowAlert(Option);
            showPlayerTable(Result);
        }
        else if(Option.equals("Player Name"))
        {
            for (Player p:AllClubPlayers)
            {
                if(p.getName().equalsIgnoreCase(F_input))
                {
                    Result.add(p);
                    break;
                }
            }
            if(Result.isEmpty())ShowAlert(Option);
            showPlayerTable(Result);
        }
    }

    public void ShowAlert(String option) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Player Not Found");
        alert.setHeaderText("Player Not Found");
        alert.setContentText("No such player with this "+option);
        alert.showAndWait();
    }

    public void showAllPlayers(ActionEvent actionEvent) {
        showPlayerTable(AllClubPlayers);
    }
}
