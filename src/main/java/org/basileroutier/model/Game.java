package org.basileroutier.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Game {

    public final static String PROPERTY_START = "model.Game.start";
    public final static String PROPERTY_FIND_PATH = "model.Game.findPath";
    public final static String PROPERTY_END = "model.Game.end";

    public final static String PROPERTY_PATH_ERROR = "model.Game.error";


    private PropertyChangeSupport pcs;

    private Board board;

    public Game(int numberOfCases) {
        pcs = new PropertyChangeSupport(this);
        initBoard(numberOfCases);
    }

    public Game() {
        this(10);
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
    }

    private void findPath(List<Position> findedPath, List<Position> currentPath, Position position) {
        int numberOfMovement = 4;
        if(isCurrentPathSizeGreaterThanFindedPath(currentPath, findedPath) || isPathEnd(board.getTile(position))){
            return;
        }
        currentPath.add(position);
        for(int i = 0; i < numberOfMovement; i++){
            Position nextPos = movePosition(i, position);
            if(isValid(currentPath, board.getTile(nextPos), nextPos)){
                if(isPathEnd(board.getTile(nextPos))){
                    findedPath.addAll(currentPath);
                    findedPath.add(nextPos);
                    System.out.println("finded solution : " + findedPath);
                    return;
                }
                findPath(findedPath, currentPath, nextPos);
            }
        }
        currentPath.remove(currentPath.size() - 1);
    }

    private Position movePosition(int pos, Position prevoiusPosition){
        switch(pos){
            case 0:
                return new Position(prevoiusPosition.getX(), prevoiusPosition.getY() - 1); // up
            case 1:
                return new Position(prevoiusPosition.getX() + 1, prevoiusPosition.getY()); // right
            case 2:
                return new Position(prevoiusPosition.getX(), prevoiusPosition.getY() + 1); // down
            case 3:
                return new Position(prevoiusPosition.getX() - 1, prevoiusPosition.getY()); // left
        }
        throw new IllegalArgumentException("Invalid position");
    }

    private boolean isValid(List<Position> currentPath, Tile tile, Position position){
        return isValidPosition(position) && isValidTile(tile) && !isAlreadyVisited(currentPath, position);
    }

    private boolean isPathEnd(Tile tile){
        return tile == Tile.END;
    }

    private boolean isAlreadyVisited(List<Position> currentPath, Position position){
        return currentPath.contains(position);
    }

    private boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getX() < board.getCases().length && position.getY() >= 0 && position.getY() < board.getCases().length;
    }

    private boolean isValidTile(Tile tile){
        return tile == Tile.EMPTY || tile == Tile.END;
    }

    private boolean isCurrentPathSizeGreaterThanFindedPath(List<Position> currentPath, List<Position> findedPath){
        if(findedPath.isEmpty()){
            return false;
        }
        return (currentPath.size() + 1) >= findedPath.size();
    }

    public Board getBoard() {
        return board;
    }



    /**
     * Add listener to the observator
     * @param listener PropertyChangeListener: the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Remove listener to the observator
     * @param listener PropertyChangeListener: the listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Notify all the observator with a specific message and content
     * @param message String: message
     * @param objectSend Object: content to send
     */
    public void change(String message, Object objectSend) {
        pcs.firePropertyChange(message, null, objectSend);
    }

    /**
     * Notify the observator when an error appear
     * @param error String[]: all the error appear
     */
    public void notifyHelp(String... error) {
        change(PROPERTY_PATH_ERROR, error);
    }
}
