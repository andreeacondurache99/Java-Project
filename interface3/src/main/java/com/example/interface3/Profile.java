package com.example.interface3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Profile extends Application {

    private static Client client;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Profile");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 580, 280);
        stage.setScene(scene);

        Text scenetitle = new Text("Welcome" + " name" + "!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
//****************************************
        Button changePsswd = new Button("Change Password");
        HBox CBox = new HBox(10);
        CBox.setAlignment(Pos.BOTTOM_LEFT);
        CBox.getChildren().add(changePsswd);
        grid.add(CBox, 0, 2);
//******************************************
        Button changeFname = new Button("Change First Name");
        HBox FBox = new HBox(10);
        FBox.setAlignment(Pos.BOTTOM_CENTER);
        FBox.getChildren().add(changeFname);
        grid.add(FBox, 1, 2);
//        ************************************
        Button changeLname = new Button("Change Last Name");
        HBox LBox = new HBox(10);
        LBox.setAlignment(Pos.BOTTOM_RIGHT);
        LBox.getChildren().add(changeLname);
        grid.add(LBox, 2, 2);
//*****************************************
        Button Admin = new Button("Connect as admin");
        HBox ABox = new HBox(10);
        ABox.setAlignment(Pos.BOTTOM_RIGHT);
        ABox.getChildren().add(Admin);
        grid.add(ABox, 3, 9);
////************************************* |
        Label preferences = new Label("List of preferences:");
        grid.add(preferences, 0, 4);
////***************************
        Label partener = new Label("Your partener id is:");
        grid.add(partener, 0, 6);

        Button Finish = new Button("Choose your preferences");
        HBox RBox = new HBox(4);
        RBox.setAlignment(Pos.BOTTOM_LEFT);
        RBox.getChildren().add(Finish);
        grid.add(RBox, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 9);
        stage.show();
    }

    public Profile() {
    }

    public Profile(Client client) {
        this.client = client;
    }

}