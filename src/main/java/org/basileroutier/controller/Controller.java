package org.basileroutier.controller;

import org.basileroutier.model.Game;
import org.basileroutier.view.View;

public class Controller {

    private Game game;
    private View view;

    public Controller(Game game, View view) {
        this.game = game;
        this.view = view;
    }

    public void start() {
        view.displayBoard();
    }


}
