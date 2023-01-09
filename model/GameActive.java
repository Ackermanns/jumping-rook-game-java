package com.example.jumpingrookgame.model;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameActive extends GameUserActions {

    int maxPath = 3;

    List<Integer> xSolution;
    List<Integer> ySolution;
    String playerchar = "#";
    boolean hasWon = false;

    boolean gameOver = false;

    //Active Game class constructor
    public GameActive(String[][] gameGrid, int gameXAxis, int gameYAxis, int gameMaxPath, List<Integer> xsol, List<Integer> ysol, int gameXPointer, int gameYPointer) {
        this.grid = gameGrid;
        this.xAxis = gameXAxis;
        this.yAxis = gameYAxis;
        this.xPointer = gameXPointer;
        this.yPointer = gameYPointer;
        this.maxPath = gameMaxPath;
        this.xMoveSet = new int[99];
        this.yMoveSet = new int[99];
        this.jumpList = new int[99];
        this.directionList = new String[99];
        this.xSolution = xsol;
        this.ySolution = ysol;
        this.thisPosition = Integer.parseInt(grid[yPointer][xPointer]);
    }

    //Returns xPointer, useful for testing validity
    public int getXPointer() {
        return this.xPointer;
    }
    //Returns yPointer, useful for testing validity
    public int getYPointer() {
        return this.yPointer;
    }

    //Returns the action response
    public String getFeedback() {
        return this.feedback;
    }

    //Resturns the game total log
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getTotalLog() {
        try {
            final StringBuilder listTranscript = new StringBuilder();

            totalLog.forEach((val) -> {
                listTranscript.append(val + ",\n");
            });

            String formattedTranscript = listTranscript.toString();
            //Remove the last comma
            formattedTranscript = formattedTranscript.substring(0, formattedTranscript.length()-2);
            return formattedTranscript;
        }
        catch(Exception checkBoundsBeginEnd) {
            return "No action taken";
        }
    }
    public boolean getIfWon() {
        return hasWon;
    }
    public void checkIfWon() {
        //The exact spot the grid the G is located. This includes the 0 index
        int lastXIdx = xSolution.size() - 1;
        int lastYIdx = ySolution.size() - 1;
        int xWin = xSolution.get(lastXIdx);
        int yWin = ySolution.get(lastYIdx);
        if (this.xPointer == xWin && this.yPointer == yWin) {
            gameOver = true;
            hasWon = true;
        }
        //Only want feedback if the user wins.
        //The user does not need to know if they have or have not won after every action.
    }

    public void resetMoveset() {
        //set the current position square back to how it was
        grid[yPointer][xPointer] = String.valueOf(thisPosition);
        //Resets all game variables
        xPointer = 0;
        yPointer = 0;
        xMoveSet = new int[99]; //99 moves! this should be more than enough
        yMoveSet = new int[99];
        moveCount = 0;
        totalLog = new ArrayList<String>();
        feedback = "Game Reset";
        directionList = new String[directionList.length];
        thisPosition = Integer.parseInt(grid[yPointer][xPointer]);
        //place player marker
        grid[yPointer][xPointer] = playerchar;
    }

    public void pointerOverRide(int y, int x) {
        //This method is used for testing purposes to help create scenarios where different moves can be tested
        //The user SHOULD NOT have access to this method or it will break the game
        this.xPointer = x;
        this.yPointer = y;
    }

    public int getMoveCount() {
        //Returns the user move count
        return this.moveCount;
    }

    public String getBoard() {
        //This class is used to print the game grid as is.
        //This is more useful for testing as I need to see what the
        //grid looks like so I can see if it is valid
        String board = new String("");
        for (int i=0; i < grid.length; i++) {
            for (int j=0; j < grid[i].length; j++) {
                board = board + grid[i][j] + " ";
            }
            board = board + "\n";
        }
        return board;
    }

}
