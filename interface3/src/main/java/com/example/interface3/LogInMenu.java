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


import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInMenu extends Application {

    private static Client client;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Log In");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 255);
        stage.setScene(scene);

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Email:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);


        Button sgUpBtn = new Button("Sign up");
        HBox SBox = new HBox(10);
        SBox.setAlignment(Pos.BOTTOM_LEFT);
        SBox.getChildren().add(sgUpBtn);
        grid.add(SBox, 0, 4);

        Button lgInBtn = new Button("Log in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(lgInBtn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        // Apasare buton Log In
        lgInBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {


                StringBuilder message = new StringBuilder();
                message.append("login ");
                message.append(userTextField.getText());
                message.append(" ");
                message.append(pwBox.getText());


                if(!userTextField.getText().isEmpty() && !pwBox.getText().isEmpty()){
                    if(Client.isValidEmail(userTextField.getText())){
                    try {
                        client.getBufferedWriter().write(String.valueOf(message));
                        client.getBufferedWriter().newLine();
                        client.getBufferedWriter().flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }}
                    else{
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Email adress is not valid");
                    }
                }
                else{
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Do it again");
                }
            }
        });

        sgUpBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                System.out.println("Text: " + userTextField.getText() + pwBox.getText());
                SignUpMenu sum = new SignUpMenu();
                try {
                    sum.start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        stage.show();
    }


    public LogInMenu() {
    }

    public LogInMenu(Client client){
        this.client=client;
    }

    public LogInMenu(PrintWriter out, BufferedReader in) {
     //   this.out = out;
      //  this.in = in;
    }

    public LogInMenu(Socket socket) throws IOException {
       // this.mySocket = socket;
        //System.out.println(mySocket.toString());
        //out = new PrintWriter(mySocket.getOutputStream(), true);
        //in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    public void dewIt() throws Exception {
        launch();
        //start();
    }
}
