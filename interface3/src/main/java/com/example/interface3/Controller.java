package com.example.interface3;

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

    private static Client client;

    public Controller(Client client) {
        this.client = client;
    }

    public Controller(){}

    @Override
    public void initialize(URL Location, ResourceBundle resources){
//        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//                sp_main.setVvalue((Double) t1);
//            }
//        });

        //button_send.setOnAction(new EventHandler<ActionEvent>() {
        //    @Override
       //     public void handle(ActionEvent actionEvent) {
        for(int i=0;i<10;i++)
        { HBox hbox =  new HBox();
            hbox.setId(String.valueOf(i));
                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.setPadding(new Insets(5,5,5,10));

//                Text text = new Text("DAA");
//                TextFlow textFlow = new TextFlow(text);
//                textFlow.setStyle("-fx-color: rgb(239,242,255); " +
//                        "-fx-background-color: rgb(15,125,242); "+
//                        "-fx-background-radius: 20px;n"
//                );
//                textFlow.setPadding(new Insets(5, 10, 5, 10));
//                text.setFill(Color.color(0.934, 0.945, 0.996));

                Button red = new Button("red");



                hbox.getChildren().add(red);
                vbox_messages.getChildren().add(hbox);
            int start=0;
            red.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    System.out.println("Touch me, i m ");
                    //red.setVisible(false);
                            //vbox_messages.getChildren().add(hbox);
                    if(vbox_messages.getChildren().contains(hbox))
                            vbox_messages2.getChildren().add(hbox);
                    else
                        vbox_messages.getChildren().add(hbox);

                }
            });

        }
        //    }
        //});
        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ListIterator<Node> nodeListIterator = vbox_messages2.getChildren().listIterator();
                while(nodeListIterator.hasNext()){
                    System.out.println(nodeListIterator.next().getId());
                }
                //System.out.println( vbox_messages2.getChildren().toString() );
               // System.out.println(  vbox_messages2.getChildren().get(0).getId() );
               // for(var s:vbox_messages2.getChildren()){
                   // System.out.println(s.co);
                //}
//                Object[] elem;
//                elem = vbox_messages2.getChildren().toArray();
//                for (var e: elem
//                     ) {
//                    System.out.println(e.));
                try {
                    client.sendMyMessage("DAA");
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                }
            }
        });



    }


}