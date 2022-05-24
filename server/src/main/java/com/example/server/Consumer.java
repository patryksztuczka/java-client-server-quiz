package com.example.server;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    final BlockingQueue<Socket> socketsQueue;
    final TextArea textArea;
    private int correctAnswers = 0;

    public Consumer(BlockingQueue<Socket> socketsQueue, TextArea textArea) {
        this.socketsQueue = socketsQueue;
        this.textArea = textArea;
    }

    public HashMap<String, String> loadQuestions() throws IOException {
        String filePath = "C:\\Users\\Patryk Sztuczka\\Documents\\PBS\\Semestr IV\\Zaawansowane programowanie obiektowe\\lab3-v4\\server\\src\\main\\resources\\com\\example\\server\\questions.txt";
        HashMap<String, String> questions = new HashMap<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|", 2);
            if (parts.length >= 2) {
                String key = parts[0];
                String value = parts[1];
                questions.put(key, value);
            }
        }
        return questions;
    }

    public void showQuestion(HashMap<String, String> questions) {
        String currentKey = (String) questions.keySet().toArray()[correctAnswers];
        textArea.appendText("\n" + (correctAnswers + 1) + ") " + currentKey);
    }

    @Override
    public void run() {
        try {
            HashMap<String, String> questions = loadQuestions();

            while (questions.size() != correctAnswers) {
                showQuestion(questions);
                Socket socket = socketsQueue.take();
                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String received = bufferedReader.readLine();
                String[] parts = received.split(",");
                Answer answer = new Answer(parts[0], parts[1]);

                String currentKey = (String) questions.keySet().toArray()[correctAnswers];
                if (answer.getAnswerText().equals(questions.get(currentKey))) {
                    correctAnswers++;
                    textArea.appendText("\nGracz " + answer.getNick() + "(" + socket.getInetAddress() + ") podał/a poprawną odpowiedź. ");
                    socketsQueue.clear();
                } else {
                    textArea.appendText("\nPodano niepoprawną odpowiedź. ");
                }
            }
            textArea.appendText("\nKoniec gry.");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
