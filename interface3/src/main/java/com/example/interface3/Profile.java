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

        Text scenetitle = new Text("Welcome"+" name(not string)"+ "!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
//        Text sceneoptions = new Text( "You have some options below:");
//        sceneoptions.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
//        grid.add(sceneoptions, 0, 0, 2, 2);

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
        grid.add(FBox, 1,2 );
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

        TextField userPreferences = new TextField();
        grid.add(userPreferences, 1, 4);
////***************************
        Label partener = new Label("Your partener will be:");
        grid.add(partener, 0, 8);

//        TextField userPartener = new TextField();
//        grid.add(userPartener, 1, 5);
////***************************
        Button Finish = new Button("Finish");
        HBox RBox = new HBox(10);
        RBox.setAlignment(Pos.BOTTOM_LEFT);
        RBox.getChildren().add(Finish);
        grid.add(RBox, 0, 6);
//**************************************
//        Label pw2 = new Label("Password:");
//        grid.add(pw2, 0, 5);
//
//        PasswordField password2 = new PasswordField();
//        grid.add(password2, 1, 5);
////******************
////        Button backLogIn = new Button("<- Log In");
////        HBox SBox = new HBox(10);
////        SBox.setAlignment(Pos.BOTTOM_LEFT);
////        SBox.getChildren().add(backLogIn);
////        grid.add(SBox, 0, 8);
//
//        Button btn = new Button("Sign up");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(btn);
//        grid.add(hbBtn, 1, 8);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 9);







        stage.show();
    }

    public Profile() {
    }

    public Profile(Client client) {
        this.client = client;
    }

    private String formMessage(String userEmail, String password1, String userFirstName, String userLastName) {
        StringBuilder message = new StringBuilder();
        message.append("register ");
        message.append(userEmail);
        message.append(" ");
        message.append(password1);
        message.append(" ");
        message.append(userFirstName);
        message.append(" ");
        message.append(userLastName);
        return String.valueOf(message);
    }

}