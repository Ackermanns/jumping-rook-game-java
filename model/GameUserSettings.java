package com.example.jumpingrookgame.model;

//This class is responsible getting all pre-game user settings
//before the grid is generated. This class gets the user parameters.
public final class GameUserSettings {
    int xAxis = 3;
    int yAxis = 3;
    int maxPath = 3;

    //Sets the grid axis dimensions. Does not generate the grid
    public void setAxis(int x, int y) {
        //Sets the dimensions of the game grid
        int max = 10;
        int min = 3;
        //making sure that x axis is not too big or too small
        if (x < min) {
            this.xAxis = min;
        }
        else if (x > max) {
            this.xAxis = max;
        }
        else {
            this.xAxis = x;
        }
        //making sure that y axis is not too big or too small
        if (y < min) {
            this.yAxis = min;
        }
        else if (y > max) {
            this.yAxis = max;
        }
        else {
            this.yAxis = y;
        }
    }

    //The path number should not be bigger than the maximum of the grid length or width
    //For example in a 3x3 grid game, we cannot have a possible path of 99999!
    //or when generating a game grid the game will load forever!
    //Also the minimum path that will be set is 3. If a user sets the path as 0 then the starting
    //position is the winning position
    public void solutionPathNumber(int pathNum) {
        //check for minimum
        if (pathNum <= 3) {
            maxPath = 3;
        }
        else {
            //check for maximum
            int pathLimit = Math.max(xAxis, yAxis);
            if (pathNum <= pathLimit) {
                maxPath = pathNum;
            }
            else {
                //else default to the realistic maximum path number
                maxPath = pathLimit;
            }
        }
    }

    //Gets the grid xAxis
    public int getXaxis() {
        return this.xAxis;
    }

    //Gets the grid xAxis
    public int getYaxis() {
        return this.yAxis;
    }

    //Gets the maximum path number
    public int getMaxPath() {
        return this.maxPath;
    }
}

