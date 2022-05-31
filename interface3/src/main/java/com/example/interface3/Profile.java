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

import java.io.IOException;

public class Profile extends Application {

    private static Client client;

    @Override
    public void start(Stage stage) throws Exception {
        String username = "OKAY ";
        String isAdmin = "false";

        //Comentezi liniile 34-39 ca sa mearga
        String response = recieveServerData();
        String[] commandParam = response.split(" ");
        String firstName = commandParam[0];
        String lastName = commandParam[1];
        isAdmin = commandParam[2];
        username = makeUsername(firstName, lastName);

        stage.setTitle("Profile");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 580, 280);
        stage.setScene(scene);

        Text scenetitle = new Text("Welcome " + username + "!");
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
        if(isAdmin.equals("true")){
        grid.add(ABox, 3, 9);
        }

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


        changePsswd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangePassword cpass = new ChangePassword();
                try {
                    cpass.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public Profile() {
    }

    public Profile(Client client) {
        this.client = client;
    }

    private String recieveServerData() throws IOException {
        StringBuilder message = new StringBuilder();
        message.append("ProfileData");
        client.sendMyMessage(message.toString());
        return client.receiveMyMessage();
    }

    private String makeUsername(String firstName,String lastName){
        StringBuilder username = new StringBuilder();
        username.append(firstName);
        username.append(" ");
        username.append(lastName);
        return username.toString();
    }

}