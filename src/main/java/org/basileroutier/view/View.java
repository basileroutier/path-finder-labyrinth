package org.basileroutier.view;

import org.basileroutier.model.Case;
import org.basileroutier.model.Game;

public class View {

    private Game game;

    public View(Game game) {
        this.game = game;
    }

    public void display() {

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

}
