package com.example.interface3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class ChooseRoommateList extends Application {

    private static Client client;

    @Override
    public void start(Stage stage) throws IOException {
        Controller cont = new Controller(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("sample.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 908, 396);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

    public ChooseRoommateList(Client client) {
        this.client=client;
    }

    public ChooseRoommateList(){}
}
