package com.example.jumpingrookgame.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameUserActions implements IUserActions {
    String feedback = "";
    String[][] grid;
    String[] directionList;
    ArrayList<String> totalLog = new ArrayList<String>();
    Map<Integer, String> map;
    int thisPosition;
    int moveCount = 0;
    int xAxis;
    int yAxis;
    int xPointer = 0;
    int yPointer = 0;
    int[] xMoveSet;
    int[] yMoveSet;
    int[] jumpList;
    String move;
    String playerchar = "#";
    boolean hasWon = false;


    public void setPlayer() {
        //stores the player position and puts the player on that place
        thisPosition = Integer.parseInt(grid[yPointer][xPointer]);
        grid[yPointer][xPointer] = playerchar;

    }

    //Undoes the last user action move
    public void undoLastMove() {
        if (moveCount == 0) {
            feedback = "You are at the start";
            totalLog.add("You are at the start");
        }
        else {
            //remove player marker
            grid[yPointer][xPointer] = String.valueOf(thisPosition);
            //Undo the previous action
            //get some helpful variables
            int prevJump = jumpList[moveCount-1];
            String prevDirection = directionList[moveCount-1];
            //No index testing needed because we know all previous jumps were valid
            switch (prevDirection) {
                //Previous was up jump, so go back down
                case "up":
                    yPointer = yPointer + prevJump;
                    break;
                //Previous jump was right, so go back left
                case "right":
                    xPointer = xPointer - prevJump;
                    break;
                //Previous jump was down, so go back up
                case "down":
                    yPointer = yPointer - prevJump;
                    break;
                //Previous jump was left, so go back right
                case "left":
                    xPointer = xPointer + prevJump;
                    break;
            }
            //Delete the last action from record
            directionList[moveCount] = null;
            jumpList[moveCount] = 0;
            //Apply appropriate changes
            feedback = "Undone last move";
            moveCount = moveCount - 1;

            //Set new player marker
            thisPosition = Integer.parseInt(grid[yPointer][xPointer]);
            grid[yPointer][xPointer] = playerchar;
        }
    }

    public enum moveSet {W, A, S, D}

    public void createIntegerMoves() {
        map = new HashMap<Integer, String>();
        map.put(5, "W");
        map.put(1, "A");
        map.put(2, "S");
        map.put(3, "D");
    }

    //This section handles the player direction move
    //list of valid actions the user can take for directions
    //Movement controls can come as Integers (5123) or string (wasd)
    public <T> void moveControl(T playerMove) {
        if (playerMove instanceof Integer) {
            move = map.get(playerMove); //check if the integer direction is in the hashmap
        }
        else {
            move = (String) playerMove;
        }
        try {
            switch (moveSet.valueOf(move)) {
                case W:
                    moveUp();
                    break;
                case D:
                    moveRight();
                    break;
                case S:
                    moveDown();
                    break;
                case A:
                    moveLeft();
                    break;
            }
        }
        catch(Exception IllegalArgumentException) {
            feedback = "Not a valid move";
            totalLog.add("Not a valid move");
        }
    }

    public void moveUp() {
        //check if there may be any index errors
        int nextJump = yPointer - thisPosition;
        if (nextJump >= 0 && nextJump < yAxis) {
            //Swap out player marker
            grid[yPointer][xPointer] = String.valueOf(thisPosition);
            //valid up jump
            xMoveSet[moveCount] = xPointer;
            yMoveSet[moveCount] = nextJump;
            jumpList[moveCount] = thisPosition;
            directionList[moveCount] = "up";
            yPointer = nextJump;
            feedback = "valid up jump";
            totalLog.add("valid up jump");
            moveCount += 1;
            //set new new thisPosition and player marker
            thisPosition = Integer.parseInt(grid[yPointer][xPointer]);
            grid[yPointer][xPointer] = playerchar;
        }
        else {
            //invalid up jump, do not commit
            feedback = "invalid up jump";
            totalLog.add("invalid up jump");
        }
    }

    public void moveRight() {
        //check if there may be any index errors
        int nextJump = xPointer + thisPosition;
        if (nextJump >= 0 && nextJump < xAxis) {
            //Swap out player marker
            grid[yPointer][xPointer] = String.valueOf(thisPosition);
            //valid right jump
            xMoveSet[moveCount] = nextJump;
            yMoveSet[moveCount] = yPointer;
            jumpList[moveCount] = thisPosition;
            directionList[moveCount] = "right";
            xPointer = nextJump;
            feedback = "valid right jump";
            totalLog.add("valid right jump");
            moveCount += 1;
            //set new new thisPosition and player marker
            thisPosition = Integer.parseInt(grid[yPointer][xPointer]);
            grid[yPointer][xPointer] = playerchar;
        }
        else {
            //invalid right jump, do not commit
            feedback = "invalid right jump";
            totalLog.add("invalid right jump");
        }
    }
    public void moveDown() {
        //check if there may be any index errors
        int nextJump = yPointer + thisPosition;
        if (nextJump >= 0 && nextJump < yAxis) {
            //Swap out player marker
            grid[yPointer][xPointer] = String.valueOf(thisPosition);

            //valid down jump
            xMoveSet[moveCount] = xPointer;
            yMoveSet[moveCount] = nextJump;
            jumpList[moveCount] = thisPosition;
            directionList[moveCount] = "down";
            yPointer = nextJump;
            feedback = "valid down jump";
            totalLog.add("valid down jump");
            moveCount += 1;

            //set new new thisPosition and player marker
            thisPosition = Integer.parseInt(grid[yPointer][xPointer]);
            grid[yPointer][xPointer] = playerchar;
        }
        else {
            //invalid down jump, do not commit
            feedback = "invalid down jump";
            totalLog.add("invalid down jump");
        }
    }
    public void moveLeft() {
        //check if there may be any index errors
        int nextJump = xPointer - thisPosition;
        if (nextJump >= 0 && nextJump < yAxis) {
            //Swap out player marker
            grid[yPointer][xPointer] = String.valueOf(thisPosition);
            //valid up jump
            xMoveSet[moveCount] = nextJump;
            yMoveSet[moveCount] = yPointer;
            jumpList[moveCount] = thisPosition;
            directionList[moveCount] = "left";
            xPointer = nextJump;
            feedback = "valid left jump";
            totalLog.add("valid left jump");
            moveCount += 1;
            //set new new thisPosition and player marker
            thisPosition = Integer.parseInt(grid[yPointer][xPointer]);
            grid[yPointer][xPointer] = playerchar;
        }
        else {
            //invalid left jump, do not commit
            feedback = "invalid left jump";
            totalLog.add("invalid left jump");
        }
    }
    public int getthisPosition() {
        return thisPosition;
    }
}
