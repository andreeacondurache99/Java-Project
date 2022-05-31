package com.example.interface3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

        TextField userChange = new TextField();
        grid.add(userChange, 1, 1);
//******************************************
        Label newPass = new Label("Introduce your new password:");
        grid.add(newPass, 0, 2);

        TextField userNewPass = new TextField();
        grid.add(userNewPass, 1, 2);
//************************************
        Label newPassR = new Label("Confirm your new password:");
        grid.add(newPassR, 0, 3);

        TextField userNewPassR = new TextField();
        grid.add(userNewPassR, 1, 3);
//*****************************************
        Button Submit = new Button("Submit");
        HBox SuBox = new HBox(4);
        SuBox.setAlignment(Pos.BOTTOM_LEFT);
        SuBox.getChildren().add(Submit);
        grid.add(SuBox, 3, 4);
////************************************* |

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 4);
        stage.show();

    }

    public ChangePassword() {
    }

    public ChangePassword(Client client) {
        this.client = client;
    }
}
