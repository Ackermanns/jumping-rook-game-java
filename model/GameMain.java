package com.example.jumpingrookgame.model;

import java.io.IOException;

@IProjectDetails (author = "Simon Ackermann", version = 1.0, lastModified="27/04/2022")
public class GameMain {

    public static void main(String[] args) throws IOException {

        IView view = new ConsoleView();
        GameController setup = new GameController(view);
        setup.go();
    }

}
