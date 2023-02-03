package org.basileroutier.model;

/**
 * The Board class is a 2D array of Case objects.
 */
public class Board {

    private Case[][] cases;

    public Board(Case[][] cases) {
        this.cases = cases;
    }

    public Case[][] getCases() {
        return cases;
    }

    public Case getCase(Position position) {

        if (position.getX() > cases.length && position.getX() < 0 && position.getY() > cases[position.getX()].length
                && position.getY() < 0) {
            throw new IllegalArgumentException("The position is out of the board");
        }
        return cases[position.getX()][position.getY()];
    }

    public Tile getTile(Position position) {
        Case caseTile = getCase(position);
        return caseTile.getTile();
    }

}
