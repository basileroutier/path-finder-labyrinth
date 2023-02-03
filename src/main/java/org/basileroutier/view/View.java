package org.basileroutier.view;

import org.basileroutier.model.Case;
import org.basileroutier.model.Game;
import org.basileroutier.model.Position;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class View implements PropertyChangeListener {

    private Game game;

    private final static String VERSION = "1.0.1";
    private final static String INFORMATION = "Basile Routier";

    public View(Game game) {
        this.game = game;
    }


    public void displayBoard(List<Position> positionList) {
        Case[][] cases = game.getBoard().getCases();
        int maxLength = maxLength(cases);

        for(int i = 0; i < cases.length; i++) {
            for(int j = 0; j < cases[i].length; j++) {
                String displayString = "";
                if(positionList != null && positionList.contains(new Position(j, i))) {
                    displayString = "X";
                }else{
                    displayString = String.valueOf(cases[i][j].getTile());
                }
                System.out.print(String.format("|%-" + (maxLength + 3) + "s", displayString));
            }
            System.out.println("|");
        }

        System.out.println("\n");
    }


    private int maxLength(Case[][] cases) {
        int maxLength = 0;
        for(int i = 0; i < cases.length; i++) {
            for(int j = 0; j < cases[i].length; j++) {
                maxLength = Math.max(maxLength, cases[i][j].getTile().toString().length());
            }
        }
        return maxLength;
    }

    public void displayInformation(){
        System.out.println("+---------------------------------------------------+");
        System.out.println("| Labirynth solver by : " + INFORMATION + "   \t        |");
        System.out.println("|\tVersion " + VERSION + "\t\t\t\t\t\t\t\t\t|");
        System.out.println("|\tALL RIGHTS RESERVED.\t\t\t\t\t\t\t|");
        System.out.println("+---------------------------------------------------+");
        System.out.println("| For the moment, the program generate an EMPTY     |");
        System.out.println("| board with a START and an END tile.               |");
        System.out.println("| If you want to personalize the board, you can     |");
        System.out.println("| change the code in the method initBoard() in the  |");
        System.out.println("| class Game.java                                   |");
        System.out.println("|                                                   |");
        System.out.println("| Have fun ! Feel free to contact me if you have    |");
        System.out.println("| any questions or suggestions.                     |");
        System.out.println("+---------------------------------------------------+");
        System.out.println("\n\n\n");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Game.PROPERTY_START:
                System.out.println("Start");
                break;
            case Game.PROPERTY_FIND_PATH:
                System.out.println("\nPATH FOUND : \n");
                displayBoard((List<Position>) evt.getNewValue());
                System.out.println("--------------------");
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
