package com.example.server;

public class Answer {
    private final String nick;
    private final String answerText;

    public Answer(String nick, String answerText) {
        this.nick = nick;
        this.answerText = answerText;
    }

    public String getNick() {
        return nick;
    }

    public String getAnswerText() {
        return answerText;
    }
}
