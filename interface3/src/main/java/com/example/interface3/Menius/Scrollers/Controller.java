package com.example.interface3.Menius.Scrollers;

import com.example.interface3.Client;
import com.example.interface3.Menius.Profile;
import com.example.interface3.Menius.Scrollers.ChooseRoommateList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox vbox_messages;

    @FXML
    private VBox vbox_messages2;

    @FXML
    private ScrollPane sp_main;

    @FXML
    private Button button_send;

    @FXML
    private Button button_back;

    private static Client client;
    private static int userID;
    private static Stage stage;

    public Controller(Client client) {
        this.client = client;
    }

    public Controller(int id){userID = id;}

    public Controller(Stage stage){this.stage = stage;}

    public Controller(){}

    @Override
    public void initialize(URL Location, ResourceBundle resources){
        String chosen = "";
        String toBeChosen = "";
        boolean succes = false;
        try {
            client.sendMyMessage("giveMeToChose");
            String response = client.receiveMyMessage();
            if(response.equals("succes")){
                succes=true;
            }
            if(succes){
                chosen = client.receiveMyMessage();
                toBeChosen = client.receiveMyMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(succes){
           // System.out.println("A VENIT 1: " + toBeChosen);
           // System.out.println("A venit 2: " + chosen);


            if(!toBeChosen.isEmpty()){
                String[] toChoseFrom = toBeChosen.split(" ");
            for(int i = 0; i < toChoseFrom.length; i++){
                String personName = "";
                try {
                    client.sendMyMessage("GiveNameOf " + toChoseFrom[i]);
                    personName = client.receiveMyMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HBox hbox =  new HBox();
                String boxId =String.valueOf(toChoseFrom[i]);
                hbox.setId(boxId);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.setPadding(new Insets(5,5,5,10));

                Button red = new Button("#"+boxId+" "+personName);

                hbox.getChildren().add(red);
                vbox_messages.getChildren().add(hbox);
                int start=0;
                red.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        //System.out.println("Touch me, i m ");

                        if(vbox_messages.getChildren().contains(hbox))
                            vbox_messages2.getChildren().add(hbox);
                        else
                            vbox_messages.getChildren().add(hbox);
            }});} }


            if(!chosen.isEmpty()){
                String[] alreadyChosen = chosen.split(" ");
            for(int i=0;i<alreadyChosen.length;i++){
                String personName = "";
                try {
                    client.sendMyMessage("GiveNameOf " + alreadyChosen[i]);
                    personName = client.receiveMyMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HBox hbox =  new HBox();
                String boxId =String.valueOf(alreadyChosen[i]);
                hbox.setId(boxId);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.setPadding(new Insets(5,5,5,10));

                Button red = new Button("#"+boxId+" "+personName);

                hbox.getChildren().add(red);
                vbox_messages2.getChildren().add(hbox);
                int start=0;
                red.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        //System.out.println("Touch me, i m ");

                        if(vbox_messages.getChildren().contains(hbox))
                            vbox_messages2.getChildren().add(hbox);
                        else
                            vbox_messages.getChildren().add(hbox);
                    }});}

        }}


        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ListIterator<Node> nodeListIterator = vbox_messages2.getChildren().listIterator();
                StringBuilder sbChosen = new StringBuilder();
                while(nodeListIterator.hasNext()){
                    String id = nodeListIterator.next().getId();
                    sbChosen.append(id);
                    sbChosen.append(" ");
                }
                String chosenIds = "";
                if(!sbChosen.isEmpty()) {
                    sbChosen.deleteCharAt(sbChosen.length() - 1);
                    chosenIds = sbChosen.toString();
                }
                //System.out.println("Chose IDS " + chosenIds);

                //Partea a2a
                ListIterator<Node> nodeListIterator2 = vbox_messages.getChildren().listIterator();
                StringBuilder sbNotChosen = new StringBuilder();
                while(nodeListIterator2.hasNext()){
                    String id = nodeListIterator2.next().getId();
                    sbNotChosen.append(id);
                    sbNotChosen.append(" ");
                }
                String notChosenIds ="";
                if(!sbNotChosen.isEmpty()) {
                    sbNotChosen.deleteCharAt(sbNotChosen.length() - 1);
                    notChosenIds = sbNotChosen.toString();
                }
                //System.out.println("Not chosen IDS " + notChosenIds);

                //Trimitere catre server
                StringBuilder message1 = new StringBuilder();
                message1.append("chosen,");
                message1.append(chosenIds);
                String firstMessage = message1.toString();

                StringBuilder message2 = new StringBuilder();
                message2.append("tobechosen,");
                message2.append(notChosenIds);
                String secondMessage = message2.toString();

                //System.out.println("!!!!!!!!!!!!!! "+ firstMessage + " !!!!!! " + secondMessage);

                try {
                    client.sendMyMessage(firstMessage);
                    client.sendMyMessage(secondMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                ChooseRoommateList crl = new ChooseRoommateList();
                try {
                    crl.start(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        button_back.setOnAction(new EventHandler<ActionEvent>() {
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



    }


}