package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage stage;
    private NetworkUtil networkUtil;
    private Club club=new Club();
    private List<Player> playersOnMarket=new ArrayList<>();
    private boolean State=true;
    private boolean OnSearch=false;

    public boolean isOnSearch() {
        return OnSearch;
    }

    public void setOnSearch(boolean onSearch) {
        OnSearch = onSearch;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Player> getPlayers() {
        return playersOnMarket;
    }

    public void Initialize(Club club,List<Player> players)
    {
        this.club=club;
        this.playersOnMarket=players;
    }

    public void setPlayers(List<Player> players) {
        this.playersOnMarket = players;
    }

    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThread(this);
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        State=false;
        OnSearch=false;
        LoginController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 995, 675));
        stage.show();
    }

    public void showHomePage(String userName) throws Exception {

        //System.out.println(club.getName()+"  "+club.getPlayerCount());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        // Loading the controller
        State=true;
        OnSearch=false;
        HomeController controller = loader.getController();
        controller.setMain(this);
        controller.setClub(club);
        controller.setPlayerOnMarket((ArrayList<Player>) playersOnMarket);
        controller.initialize(userName);

        // Set the primary stage
        stage.setTitle("Home");
        stage.setScene(new Scene(root, 995, 675));
        stage.show();
    }

    public void showBuyPage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BuyPage.fxml"));
        Parent root = loader.load();

        System.out.println(club.getName());
        System.out.println(playersOnMarket);
        State=false;
        OnSearch=false;
        BuyPageController controller = loader.getController();
        controller.setClub(club);
        controller.setPlayerOnMarket((ArrayList<Player>) playersOnMarket);
        controller.setMain(this);
        controller.initialize();

        stage.setTitle("Transfer Market");
        stage.setScene(new Scene(root, 995, 675));
        stage.show();
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        // This will launch the JavaFX application
        launch(args);
    }


    public Main getMain() {
        return this;
    }


    public void showSearchPage()throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Search.fxml"));
        Parent root = loader.load();

        // Loading the controller
        State=true;
        OnSearch=true;
        SearchController controller = loader.getController();
        controller.setMain(this);
        controller.setClub(club);
        controller.initialize();

        // Set the primary stage
        stage.setTitle("Search");
        stage.setScene(new Scene(root, 995, 675));
        stage.show();
    }
}
