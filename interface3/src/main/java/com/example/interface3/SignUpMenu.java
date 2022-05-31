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

public class SignUpMenu extends Application {

    private static Client client;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Sign Up");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 470, 380);
        stage.setScene(scene);

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

//****************************************
        Label firstName = new Label("First name:");
        grid.add(firstName, 0, 1);

        TextField userFirstName = new TextField();
        grid.add(userFirstName, 1, 1);
//************************************* |
        Label lastName = new Label("Last name:");
        grid.add(lastName, 0, 2);

        TextField userLastName = new TextField();
        grid.add(userLastName, 1, 2);
//***************************
        Label email = new Label("Email:");
        grid.add(email, 0, 3);

        TextField userEmail = new TextField();
        grid.add(userEmail, 1, 3);
//***************************
        Label pw = new Label("Password:");
        grid.add(pw, 0, 4);

        PasswordField password1 = new PasswordField();
        grid.add(password1, 1, 4);

        Label pw2 = new Label("Password:");
        grid.add(pw2, 0, 5);

        PasswordField password2 = new PasswordField();
        grid.add(password2, 1, 5);
//*******************
        Text Rule1 = new Text("Password must contain:\n" +
                "at least 8 characters and at most 20 characters.\n" +
                "at least one digit.\n" +
                "at least one alphabet letter.\n" +
                "at least one special character which includes !@#$%&*()-+=^.\n" +
                "and it doesn’t contain any white space.");
        Rule1.setFont(Font.font("Tahoma", FontPosture.ITALIC, 9));
        grid.add(Rule1, 0, 6);
//        actiontarget.setFill(Color.FIREBRICK);

        Button backLogIn = new Button("<- Log In");
        HBox SBox = new HBox(10);
        SBox.setAlignment(Pos.BOTTOM_LEFT);
        SBox.getChildren().add(backLogIn);
        grid.add(SBox, 0, 8);

        Button btn = new Button("Sign up");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 8);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 9);

        // SIGN UP BUTTON PRESSED
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);

                String email = userEmail.getText();
                String password = password1.getText();
                String passwordTest = password2.getText();
                String firstName = userFirstName.getText();
                String lastName = userLastName.getText();
                String message = formMessage(email, password, firstName, lastName);


                if (!email.isEmpty() && !password.isEmpty() && !firstName.isEmpty()
                        && !lastName.isEmpty() && !passwordTest.isEmpty()) {
                    if (!Client.isValidPassw(password)) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Passwords are not ok, check the reasons below.");
                    }
                    if (!password.equals(passwordTest)) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Passwords are not alike.");
                    } else if (Client.isValidEmail(email)) {

                        System.out.println("Verificam in baza de date daca e ok");
                        try {
                            client.sendMyMessage(message);
                            String response = client.receiveMyMessage();
                            if (response.equals("succes")) {
                                //MainMenu mm = new MainMenu();
                                //mm.start(stage);
                                Profile pp = new Profile();
                                pp.start(stage);
                            } else {
                                actiontarget.setFill(Color.FIREBRICK);
                                actiontarget.setText("Email already used");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Email adress is not valid");
                    }
                }
            }
        });

        backLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LogInMenu ln = new LogInMenu();
                try {
                    ln.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        stage.show();
    }

    public SignUpMenu() {
    }

    public SignUpMenu(Client client) {
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