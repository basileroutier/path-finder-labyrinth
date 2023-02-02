package org.basileroutier.model;

public class Board {

    private Case[][] cases;

    public Board(Case[][] cases) {
        this.cases = cases;
    }

    public Case[][] getCases() {
        return cases;
    }

    public Case getCase(Position position) {
        if(position.getX() < cases.length && position.getY() < cases[position.getX()].length){
            return cases[position.getX()][position.getY()];
        }
        return null;
    }

    public Tile getTile(Position position) {
        Case caseTile = getCase(position);
        if(caseTile != null) {
            return caseTile.getTile();
        }
        return null;
    }



}
