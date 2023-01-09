package com.example.jumpingrookgame.model;

import java.util.Scanner;

public class ConsoleView implements IView {
    private Scanner in = new Scanner(System.in);

    @Override
    public String get(String prompt) {
        this.say(prompt);
        String input;
        input = in.nextLine();
        return input;
    }

    @Override
    public String get() {
        return this.get("> ");
    }

    @Override
    public <T> void say(T message) {
        System.out.println(message);
    }

    @Override
    public void start() {
        this.say("STARTING");
    }

    @Override
    public void stop() {
        this.say("STOPPED");
    }
}
