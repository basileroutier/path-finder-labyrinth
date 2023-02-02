package org.basileroutier.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;

    public Game(int numberOfCases) {
        initBoard(numberOfCases);
    }

    public Game() {
        initBoard(10);
    }


    private void initBoard(int numberOfCases) {
        Case[][] cases = new Case[numberOfCases][numberOfCases];
        for (int i = 0; i < numberOfCases; i++) {
            for (int j = 0; j < numberOfCases; j++) {
                cases[i][j] = new Case(Tile.EMPTY);
            }
        }
        cases[0][0] = new Case(Tile.START);
        cases[numberOfCases - 1][numberOfCases - 1] = new Case(Tile.END);
        board = new Board(cases);
    }

    public void backtrackingFindPath() {
        List<Position> findedPath = new ArrayList<>();
        List<Position> currentPath = new ArrayList<>();
        findPath(findedPath, currentPath, new Position(0, 0));
        System.out.println(findedPath.size());
    }

    private void findPath(List<Position> findedPath, List<Position> currentPath, Position prevoiusPosition) {
        int numberOfMovement = 4;
        for(int i = 0; i < numberOfMovement; i++){
            Position currentPos = movePosition(i, prevoiusPosition);
            if(isValid(currentPath, board.getTile(currentPos), currentPos)){

            }
        }
    }

    private Position movePosition(int pos, Position prevoiusPosition){
        switch(pos){
            case 0:
                return new Position(prevoiusPosition.getX(), prevoiusPosition.getY() - 1);
            case 1:
                return new Position(prevoiusPosition.getX() + 1, prevoiusPosition.getY());
            case 2:
                return new Position(prevoiusPosition.getX(), prevoiusPosition.getY() + 1);
            case 3:
                return new Position(prevoiusPosition.getX() - 1, prevoiusPosition.getY());
        }
        throw new IllegalArgumentException("Invalid position");
    }

    private boolean isPathEnd(Tile tile){
        return tile == Tile.END;
    }

    private boolean isValid(List<Position> currentPath, Tile tile, Position position){
        return isValidPosition(position) && isValidTile(tile) && !isAlreadyVisited(currentPath, position);
    }

    private boolean isAlreadyVisited(List<Position> currentPath, Position position){
        return currentPath.contains(position);
    }

    private boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getX() < board.getCases().length && position.getY() >= 0 && position.getY() < board.getCases().length;
    }

    private boolean isValidTile(Tile tile){
        return tile == Tile.EMPTY;
    }

    public Board getBoard() {
        return board;
    }
}
