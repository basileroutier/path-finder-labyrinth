package org.basileroutier;

import org.basileroutier.controller.Controller;
import org.basileroutier.model.Game;
import org.basileroutier.view.View;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        View view = new View(game);
        Controller controller = new Controller(game, view);
        controller.start();
    }
}