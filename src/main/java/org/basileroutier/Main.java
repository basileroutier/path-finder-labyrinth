package org.basileroutier;

import org.basileroutier.controller.Controller;
import org.basileroutier.model.Game;
import org.basileroutier.view.View;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(3);
        View view = new View(game);
        game.addPropertyChangeListener(view);
        Controller controller = new Controller(game, view);
        controller.start();
    }
}