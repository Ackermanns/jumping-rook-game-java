package com.example.jumpingrookgame.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//This class is going to be responsible for getting the user settings and passing them through to the GameSetup class so
//it can be used to generate the game. At this stage the controller will pass through variables but this class will be
//used to talk to the user once the game is completed.
public class GameSetup {
    int xAxis;
    int yAxis;
    int maxPath;
    String[][] grid;
    String solutionString = "";
    //game start and normal pointers are different because the normal pointers are used to create a path solution.
    //The starting pointers should not change
    int xPointer = 0;
    int yPointer = 0;
    int startxPointer = 0;
    int startyPointer = 0;
    //the x and y solution path will be stored in separate arrays, but the indexes will be the same
    List<Integer> xSolution = new ArrayList<Integer>();
    List<Integer> ySolution = new ArrayList<Integer>();
    //These two are used for testing purposes to make it easier to follow solution path logic
    int[] jumpList;
    String[] pathDirection;

    //Constructor
    public GameSetup(int userxAxis, int useryAxis, int usermaxPath, int startxPointer, int startyPointer) {
        this.xAxis = userxAxis;
        this.yAxis = useryAxis;
        this.maxPath = usermaxPath +1; //add 2 because of indexes
        this.xPointer = this.startxPointer = startxPointer;
        this.yPointer = this.startyPointer = startyPointer;
        //solution path, movement is tracked in these arrays

        //this.xSolution = new int[maxPath];
        //this.ySolution = new int[maxPath];
        this.jumpList = new int[maxPath];
        this.pathDirection = new String[maxPath];
    }

    //Gets starting position xPointer
    public int getStartingxPointer() {
        return this.startxPointer;
    }
    //Gets starting position yPointer
    public int getStartingyPointer() {
        return this.startyPointer;
    }

    //Gets path solution in text form
    public String getSolution() {
        return this.solutionString;
    }

    //Gets x list solution path
    public List<Integer> getxSolution() {
        return this.xSolution;
    }

    //Gets y list solution path
    public List<Integer> getySolution() {
        return this.ySolution;
    }

    //Gets the grid 2D array
    public String[][] getGrid() {
        return this.grid;
    }

    //Creates grid
    public void createGrid() {
        this.grid = new String[this.xAxis][this.yAxis];
    }

    //Returns a random number within up to the upper bound, used for creating grid jump positions
    private int generateRandom(int upperBound) {
        Random rand = new Random();
        int num = rand.nextInt(upperBound+1);
        return num;
    }

    //Sets the starting position of the game
    public void setStart(int x, int y) {
        this.xPointer = x;
        this.yPointer = y;
    }

    public void setFinish() {

        int lastXIdx = xSolution.size() - 1;
        int lastYIdx = ySolution.size() - 1; //these should be the same but keep separate to avoid unexpected errors
        int lastx = xSolution.get(lastXIdx);
        int lasty = ySolution.get(lastYIdx);
        grid[lasty][lastx] = "G";
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


    public void generateSolutionPath() {
        //Randomly creates a solution path within the grid
        //NOTE:	this is so that there will always be at least one solution generated every time
        //		Any solutions that use a path by the 'populateGrid' method are coincidental but will also be
        //		valid solutions so there MIGHT be more than one way to solve the puzzle. The nature of randomization.
        int currentPathCount = 1;//can also be used as an index to insert solutions
        int nextJump;
        int xPointer = 0;
        int yPointer = 0;

        jumpList = new int[maxPath];
        int direction = generateRandom(2) +1; //0-1 (can only start moving in direction 1 (right) or 2 (down) so +1)
        int positionJump = generateRandom(3) +1; //1-3
        xSolution.add(0);
        ySolution.add(0);
        jumpList[0] = positionJump;

        while (currentPathCount < maxPath) {
            //Pick a random direction (0: up, 1: right, 2:down, 3:left)

            if (direction == 0) {
                //check if valid position
                nextJump = yPointer - positionJump;
                //check if it is within the index
                if (nextJump > 0 && nextJump < yAxis) {
                    if (grid[nextJump][xPointer] == null) {
                        solutionString = solutionString + "UP\n";
                        //valid position with no grid index error AND not already occupied. Commit
                        grid[yPointer][xPointer] = Integer.toString(positionJump);
                        yPointer = yPointer-positionJump;

                        xSolution.add(xPointer);
                        ySolution.add(yPointer);
                        jumpList[currentPathCount] = positionJump;
                        pathDirection[currentPathCount] = "UP";
                        currentPathCount += 1;
                    }
                }
            }
            else if (direction == 1) {
                //check if valid position
                nextJump = xPointer + positionJump;
                if (nextJump > 0 && nextJump < xAxis) {
                    if (grid[yPointer][nextJump] == null) {
                        solutionString = solutionString + "RIGHT\n";
                        //valid position with no grid index error AND not already occupied. Commit
                        grid[yPointer][xPointer] = Integer.toString(positionJump);
                        xPointer = xPointer+positionJump;

                        xSolution.add(xPointer);
                        ySolution.add(yPointer);
                        jumpList[currentPathCount] = positionJump;
                        pathDirection[currentPathCount] = "RIGHT";
                        currentPathCount += 1;
                    }
                }
            }
            else if (direction == 2) {
                //check if valid position
                nextJump = yPointer + positionJump;
                if (nextJump > 0 && nextJump < yAxis) {
                    if (grid[nextJump][xPointer] == null) {
                        solutionString = solutionString + "DOWN\n";
                        //valid position with no grid index error AND not already occupied. Commit
                        grid[yPointer][xPointer] = Integer.toString(positionJump);
                        yPointer = yPointer+positionJump;

                        xSolution.add(xPointer);
                        ySolution.add(yPointer);
                        jumpList[currentPathCount] = positionJump;
                        pathDirection[currentPathCount] = "DOWN";
                        currentPathCount += 1;
                    }
                }
            }
            else if (direction == 3) {
                //check if valid position
                nextJump = xPointer - positionJump;
                if (nextJump > 0 && nextJump < xAxis) {
                    if (grid[yPointer][nextJump] == null) {
                        solutionString = solutionString +"LEFT\n";
                        //valid position with no grid index error AND not already occupied. Commit
                        grid[yPointer][xPointer] = Integer.toString(positionJump);
                        xPointer = xPointer-positionJump;

                        xSolution.add(xPointer);
                        ySolution.add(yPointer);
                        jumpList[currentPathCount] = positionJump;
                        pathDirection[currentPathCount] = "LEFT";
                        currentPathCount += 1;
                    }
                }
            }
            //Reset random's after every attempt
            direction = generateRandom(4);
            positionJump = generateRandom(4) +1;
        }
    }

    public void populateGrid() {
        //Go through each Square on the grid and assign a random number if the square is a 0 (default value)
        int upperBound = Math.min(xAxis, maxPath) -1;
        int value = 0;
        for (int i=0; i < grid.length; i++) {
            for (int j=0; j < grid[i].length; j++) {
                if(grid[i][j] == null) {
                    //+1 because we do not want the random to be 0
                    while (value == 0) {
                        value = generateRandom(upperBound);
                    }
                    grid[i][j]  = Integer.toString(value);
                    value = 0;
                }
            }
        }
    }

    public void reshuffleGrid() {
        //This class resets the grid
        createGrid();
        setStart(0,0);
        generateSolutionPath();
        setFinish();
        populateGrid();
    }


}
