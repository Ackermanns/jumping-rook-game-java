package com.example.jumpingrookgame.model;

import java.io.IOException;
import java.util.List;

//Called from the GameMain
public class GameController {
    private IView view;

    GameController(IView theView) {
        this.view = theView;
    }

    public void go() throws IOException {
        //CONSTANTS, change these for testing purposes
        int XAXIS = 3;
        int YAXIS = 3;
        int PATHNUMBER = 3;

        //GameUserSettings WORK HERE
        GameUserSettings usersettings = new GameUserSettings();
        usersettings.setAxis(XAXIS, YAXIS);
        usersettings.solutionPathNumber(PATHNUMBER);

        view.say("x axis is: " + usersettings.getXaxis());
        view.say("y axis is: " + usersettings.getYaxis());
        view.say("max path is: " + usersettings.getMaxPath());

        //GameSetup WORK HERE
        GameSetup gamegrid = new GameSetup(XAXIS, YAXIS, PATHNUMBER, 0, 0);
        gamegrid.createGrid();
        gamegrid.setStart(0,0);
        gamegrid.generateSolutionPath();
        gamegrid.setFinish();
        gamegrid.populateGrid();

        view.say(gamegrid.getSolution());

        String viewgrid = gamegrid.getBoard();
        view.say(viewgrid);



        //GameActive WORK HERE
        String[][] grid = gamegrid.getGrid();
        List<Integer> xsol = gamegrid.getxSolution();
        List<Integer> ysol = gamegrid.getySolution();
        int startx = gamegrid.getStartingxPointer();
        int starty = gamegrid.getStartingyPointer();


        GameActive active = new GameActive(grid, XAXIS, YAXIS, PATHNUMBER, xsol, ysol, startx, starty);
        active.createIntegerMoves();
        active.pointerOverRide(0, 0);
        active.setPlayer();
        //start state
        System.out.println("Starting grid:");
        System.out.println(active.getBoard());
        //...

        //move down
        active.moveControl(2);
        System.out.println(active.getBoard());
        //move right

        active.moveControl("D");
        System.out.println(active.getBoard());
		/*active.moveControl("Z"); //Bad input
		System.out.println(active.getTotalLog());

		System.out.println("•");
		//Should create and get a record class here (requires sorting)
		//active.createSaveState("savefile");

		*/

    }
}
