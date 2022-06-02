package com.example.interface3.Menius;

import com.example.interface3.Client;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
        import javafx.scene.layout.HBox;
        import javafx.scene.text.Font;
        import javafx.scene.text.FontWeight;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;

import java.io.IOException;

public class Admin extends Application {
    private static Client client;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Admin Menu");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 510, 290);
        stage.setScene(scene);

        Text scenetitle = new Text("Welcome to the admin menu!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);


        Button Back = new Button("<-Back");
        HBox SuBox = new HBox(4);
        SuBox.setAlignment(Pos.BOTTOM_LEFT);
        SuBox.getChildren().add(Back);
        grid.add(SuBox, 0, 5);
//////************************************* |
        //*****************************************
    Button SRegist = new Button("Stop registration");
    HBox RSBox = new HBox(4);
        RSBox.setAlignment(Pos.BOTTOM_RIGHT);
                RSBox.getChildren().add(SRegist);
                SRegist.setVisible(false);
                grid.add(RSBox, 1, 1);
////*************************************
                Button Regist = new Button("Start registration");
                HBox RBox = new HBox(4);
                RBox.setAlignment(Pos.BOTTOM_LEFT);
                RBox.getChildren().add(Regist);
                grid.add(RBox, 0, 1);
        //*****************************************
        Label generate = new Label("Generate the random lists:");
        grid.add(generate, 0, 2);

        Button Generate = new Button("Generate");
        HBox GBox = new HBox(4);
        GBox.setAlignment(Pos.BOTTOM_CENTER);
        GBox.getChildren().add(Generate);
        grid.add(GBox, 1, 2);
        //*****************************************
        Label alg = new Label("Apply the algorithm:");
        grid.add(alg, 0, 3);

        Button Algo = new Button("Apply");
        HBox ABox = new HBox(4);
        ABox.setAlignment(Pos.BOTTOM_CENTER);
        ABox.getChildren().add(Algo);
        grid.add(ABox, 1, 3);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 4);
        stage.show();

        Regist.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    client.sendMyMessage("ConstructLists");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Profile pp = new Profile();
                try {
                    pp.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    client.sendMyMessage("generateRandomLists");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Algo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    client.sendMyMessage("StartAlgorithm");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public Admin() {
    }

    public Admin(Client client) {
        this.client = client;
    }
}