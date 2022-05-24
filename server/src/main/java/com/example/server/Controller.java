package com.example.server;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Controller implements Initializable {

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BlockingQueue<Socket> socketsQueue = new ArrayBlockingQueue<>(10);

        Server server = new Server(socketsQueue, textArea);
        Consumer consumer = new Consumer(socketsQueue, textArea);

        new Thread(server).start();
        new Thread(consumer).start();
    }
}