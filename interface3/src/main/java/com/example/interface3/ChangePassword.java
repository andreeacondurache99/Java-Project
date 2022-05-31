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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePassword extends Application {
    private static Client client;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Change Password");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 480, 240);
        stage.setScene(scene);

        Text scenetitle = new Text("Welcome to the change password menu!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
//****************************************
        Label changePass = new Label("Introduce your old password:");
        grid.add(changePass, 0, 1);

        PasswordField userChange = new PasswordField();
        grid.add(userChange, 1, 1);
//******************************************
        Label newPass = new Label("Introduce your new password:");
        grid.add(newPass, 0, 2);

        PasswordField userNewPass = new PasswordField();
        grid.add(userNewPass, 1, 2);
//************************************
        Label newPassR = new Label("Confirm your new password:");
        grid.add(newPassR, 0, 3);

        PasswordField userNewPassR = new PasswordField();
        grid.add(userNewPassR, 1, 3);
//*****************************************
        Button Submit = new Button("Submit");
        HBox SuBox = new HBox(4);
        SuBox.setAlignment(Pos.BOTTOM_LEFT);
        SuBox.getChildren().add(Submit);
        grid.add(SuBox, 3, 4);
////*************************************
        Button Back = new Button("Back");
        HBox BBox = new HBox(4);
        BBox.setAlignment(Pos.BOTTOM_LEFT);
        BBox.getChildren().add(Back);
        grid.add(BBox, 0, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 4);
        stage.show();

        Submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String oldPassword = changePass.getText();
                String password1= userNewPass.getText();
                String password2= userNewPassR.getText();
                if(!password1.equals(password2)){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Passwords are not ok, check the reasons below.");
                }
                else if(Client.isValidPassw(password1)){
                    String message = formMessage(oldPassword, password1);
                    try {
                        client.sendMyMessage(message);
                        String response = client.receiveMyMessage();
                        if(response.equals("succes")){
                            actiontarget.setFill(Color.GREEN);
                            actiontarget.setText("Success");
                            wait(2000);
                            Profile pp = new Profile();
                            pp.start(stage);
                        }
                        else{
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("Old password not ok");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("New password is not valid");
                }
            }
        });

    }

    private String formMessage(String oldPassword, String password1) {
        StringBuilder message = new StringBuilder();
        message.append("changePass ");
        message.append(oldPassword);
        message.append(" ");
        message.append(password1);
        return message.toString();
    }


    public ChangePassword() {
    }

    public ChangePassword(Client client) {
        this.client = client;
    }
}
