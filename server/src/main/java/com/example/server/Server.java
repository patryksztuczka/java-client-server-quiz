package com.example.server;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Server implements Runnable {
    final BlockingQueue<Socket> socketsQueue;
    final TextArea textArea;

    public Server(BlockingQueue<Socket> socketsQueue, TextArea textArea) {
        this.socketsQueue = socketsQueue;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9997);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            Socket socket = null;
            try {
                assert serverSocket != null;
                socket = serverSocket.accept();
                socketsQueue.put(socket);

            } catch (IOException | InterruptedException e) {
                try {
                    assert socket != null;
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}
