package com.company.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField tf_nick;

    @FXML
    private TextField tf_answer;

    @FXML
    private Button button_send;


    EventHandler<ActionEvent> sendButtonHandler = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (tf_nick.getText().isEmpty() || tf_answer.getText().isEmpty()) {
                return;
            }
            try {
                Socket socket = new Socket("127.0.0.1", 9997);
                PrintWriter data = new PrintWriter(socket.getOutputStream(), true);
                String messageToSend = tf_nick.getText() + "," + tf_answer.getText();
                data.println(messageToSend);
                data.flush();
                tf_answer.clear();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_send.setOnAction(sendButtonHandler);
    }
}