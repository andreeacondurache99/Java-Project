package com.example.interface3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Main Menu");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 600, 600);
        stage.setScene(scene);
    }
}
