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
        if(position.getX() < cases.length && position.getX()>=0  && position.getY() < cases[position.getX()].length && position.getY()>=0){
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
