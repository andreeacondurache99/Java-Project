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

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
//*************************************
        Label lastName = new Label("Last name:");
        grid.add(lastName, 0, 2);

        TextField userTextField2 = new TextField();
        grid.add(userTextField2, 1, 2);
//***************************
        Label email = new Label("Email:");
        grid.add(email, 0, 3);

        TextField userTextField3 = new TextField();
        grid.add(userTextField3, 1, 3);
//***************************
        Label pw = new Label("Password:");
        grid.add(pw, 0, 4);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 4);

        Label pw2 = new Label("Password:");
        grid.add(pw2, 0, 5);

        PasswordField pwBox2 = new PasswordField();
        grid.add(pwBox2, 1, 5);
//*******************

        Text Rule1 = new Text("Password must contain:\n" +
                "at least 8 characters and at most 20 characters.\n" +
                "at least one digit.\n" +
                "at least one upper case alphabet.\n" +
                "at least one lower case alphabet.\n" +
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
                // actiontarget.setText("Sign UP button pressed");

                StringBuilder message = new StringBuilder();
                message.append("register ");
                message.append(userTextField.getText());
                message.append(" ");
                message.append(pwBox.getText());

                System.out.println("Text: " + userTextField.getText() + pwBox.getText() + pwBox2.getText());

                if (!userTextField.getText().isEmpty() && !userTextField2.getText().isEmpty()
                        && !userTextField3.getText().isEmpty() && !pwBox.getText().isEmpty()
                        && !pwBox2.getText().isEmpty() && !Rule1.getText().isEmpty()) {
                    if (!(pwBox.getText().isEmpty()) || !(pwBox.getText().length() < 8) || (pwBox.getText().length() > 20)
                            || (pwBox.getText().contains(" ")) || (!pwBox.getText().contains("@")
                            || !pwBox.getText().contains("#")
                            || !pwBox.getText().contains("!") || !pwBox.getText().contains("~")
                            || !pwBox.getText().contains("$") || !pwBox.getText().contains("%")
                            || !pwBox.getText().contains("^") || !pwBox.getText().contains("&")
                            || !pwBox.getText().contains("*") || !pwBox.getText().contains("(")
                            || !pwBox.getText().contains(")") || !pwBox.getText().contains("-")
                            || !pwBox.getText().contains("+") || !pwBox.getText().contains("/")
                            || !pwBox.getText().contains(":") || !pwBox.getText().contains(".")
                            || !pwBox.getText().contains(", ") || !pwBox.getText().contains("<")
                            || !pwBox.getText().contains(">") || !pwBox.getText().contains("?")
                            || !pwBox.getText().contains("|"))
                            || !pwBox.getText().contains("0") || !pwBox.getText().contains("2")
                            || !pwBox.getText().contains("3") || !pwBox.getText().contains("4")
                            || !pwBox.getText().contains("5") || !pwBox.getText().contains("6")
                            || !pwBox.getText().contains("7") || !pwBox.getText().contains("8")
                            || !pwBox.getText().contains("9")) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("Password is not valid!");

                        if (!pwBox.getText().equals(pwBox2.getText())) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("Passwords are not alike");
                        } else if (Client.isValidEmail(userTextField3.getText())) {

                            System.out.println("Verificam in baza de date daca e ok");
                            try {
                                client.getBufferedWriter().write(String.valueOf(message));
                                client.getBufferedWriter().newLine();
                                client.getBufferedWriter().flush();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("Email adress is not valid");
                        }
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

}