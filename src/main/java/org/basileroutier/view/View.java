package org.basileroutier.view;

import org.basileroutier.model.Case;
import org.basileroutier.model.Game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {

    private Game game;

    private final static String VERSION = "1.0.0";
    private final static String INFORMATION = "Basile Routier";

    public View(Game game) {
        this.game = game;
    }


    public void displayBoard() {
        Case[][] cases = game.getBoard().getCases();
        for(int i = 0; i < cases.length; i++) {
            System.out.print("|");
            for(int j = 0; j < cases[i].length; j++) {
                System.out.print(cases[i][j].getTile()+"|");
            }
            System.out.println();
        }

    }

    public void displayInformation(){
        System.out.println("Labirynth solver by : "+INFORMATION +  ".\n\tVersion "+ VERSION+"\n\tALL RIGHTS RESERVED.");
        System.out.println("For the moment, the program generate an EMPTY board with a START and an END tile.");
        System.out.println("If you want to personalize the board, you can change the code in the method initBoard() in the class Game.java");
        System.out.println("Have fun ! Feal free to contact me if you have any questions or suggestions.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Game.PROPERTY_START:
                System.out.println("Start");
                break;
            case Game.PROPERTY_FIND_PATH:
                System.out.println("Find path");
                break;
            case Game.PROPERTY_END:
                System.out.println("End");
                break;
            case Game.PROPERTY_PATH_ERROR:
                System.out.println("Error");
                break;
        }
    }
}
