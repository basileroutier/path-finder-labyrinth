package org.basileroutier.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * It's a model class that represent the labyrinth game.
 */
public class Game {

    public final static String PROPERTY_START = "model.Game.start";
    public final static String PROPERTY_FIND_PATH = "model.Game.findPath";
    public final static String PROPERTY_END = "model.Game.end";

    public final static String PROPERTY_PATH_ERROR = "model.Game.error";

    private final PropertyChangeSupport pcs;
    private final int numberOfMove = 4;
    private Board board;

    public Game(int numberOfCases) {
        pcs = new PropertyChangeSupport(this);
        initBoard(numberOfCases);
    }

    public Game() {
        this(5);
    }

    /**
     * It creates a board with a start and end tile, and fills the rest with empty
     * tiles
     * 
     * @param numberOfCases The number of cases in the board.
     */
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

    /**
     * It finds all the paths from the define start position until he find a
     * solution or not.
     */
    public void backtrackingFindPath() {
        List<List<Position>> findedPath = new ArrayList<>();
        List<Position> currentPath = new ArrayList<>();
        findPath(findedPath, currentPath, new Position(0, 0));
    }

    /**
     * If the current path is longer than the finded path, return.
     * Otherwise, add the current position to the current path,
     * and for each possible move,
     * if the move is valid, and the tile is
     * the end tile, send the finded path, otherwise,
     * call the method recursively.
     * 
     * @param findedPath  The list of paths that have been found so far.
     * @param currentPath The current path that is being searched.
     * @param position    The current position of the path.
     */
    private void findPath(List<List<Position>> findedPath, List<Position> currentPath, Position position) {
        if (isCurrentPathSizeGreaterThanFindedPath(currentPath, findedPath)) {
            return;
        }

        currentPath.add(position);

        for (int statePos = 0; statePos < numberOfMove; statePos++) {
            Position nextPosition = movePosition(statePos, position);
            if(!isValidPosition(nextPosition)) {
                continue;
            }
            Tile tileNextPosition = board.getTile(nextPosition);

            if (isValidPath(currentPath, tileNextPosition, nextPosition)) {
                if (isTileEnd(tileNextPosition)) {
                    sendFindedPath(findedPath, currentPath, nextPosition);
                } else {
                    findPath(findedPath, currentPath, nextPosition);
                }
            }
        }
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Given a position, return the position that is one step in the given
     * direction.
     * 
     * @param pos              The position of the next move.
     * @param prevoiusPosition The position of the previous node.
     * @return The new position of the evolution.
     */
    private Position movePosition(int pos, Position prevoiusPosition) {
        switch (pos) {
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

    /**
     * Return true if the position is valid, the tile is valid, and the position has
     * not already been
     * visited.
     * 
     * @param currentPath The current path that the algorithm is on.
     * @param tile        The tile that we are currently on.
     * @param position    The position of the tile we're checking
     * @return The method is returning a boolean value.
     */
    private boolean isValidPath(List<Position> currentPath, Tile tile, Position position) {
        return isValidTile(tile) && !isAlreadyVisited(currentPath, position);
    }

    /**
     * Returns true if the given tile is the end tile.
     * 
     * @param tile The tile to check
     * @return The method isTileEnd returns a boolean value.
     */
    private boolean isTileEnd(Tile tile) {
        return tile == Tile.END;
    }

    /**
     * If the current path contains the position, then the position has already been
     * visited.
     * 
     * @param currentPath The current path that we are on.
     * @param position    The current position of the knight.
     * @return The method is returning a boolean value.
     */
    private boolean isAlreadyVisited(List<Position> currentPath, Position position) {
        return currentPath.contains(position);
    }

    /**
     * Returns true if the position is within the board's bounds, false otherwise.
     * 
     * @param position The position to check
     * @return A boolean value.
     */
    private boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getX() < board.getCases().length && position.getY() >= 0
                && position.getY() < board.getCases().length;
    }

    /**
     * Returns true if the tile is empty or the end tile.
     * 
     * @param tile The tile to check
     * @return The method is returning a boolean value.
     */
    private boolean isValidTile(Tile tile) {
        return tile == Tile.EMPTY || tile == Tile.END;
    }

    /**
     * > If the current path is longer than the shortest path found so far, then
     * return true
     * 
     * @param currentPath the current path that is being searched
     * @param findedPath  the list of paths that have been found so far.
     * @return The method returns a list of lists of positions.
     */
    private boolean isCurrentPathSizeGreaterThanFindedPath(List<Position> currentPath,
            List<List<Position>> findedPath) {
        if (findedPath.isEmpty()) {
            return false;
        }
        for (List<Position> path : findedPath) {
            if (currentPath.size() + 1 > path.size()) {
                return true;
            }
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * It adds the current path to the list of finded paths and sends a message to
     * the client
     * 
     * @param findedPath  The list of paths found.
     * @param currentPath The path that has been found so far.
     * @param nextPos     The next position to be searched.
     */
    private void sendFindedPath(List<List<Position>> findedPath, List<Position> currentPath, Position nextPos) {
        List<Position> path = new ArrayList<>(currentPath);
        path.add(nextPos);
        findedPath.add(path);
        change(PROPERTY_FIND_PATH, path);
    }

    /**
     * Add listener to the observator
     * 
     * @param listener PropertyChangeListener: the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Remove listener to the observator
     * 
     * @param listener PropertyChangeListener: the listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Notify all the observator with a specific message and content
     * 
     * @param message    String: message
     * @param objectSend Object: content to send
     */
    public void change(String message, Object objectSend) {
        pcs.firePropertyChange(message, null, objectSend);
    }

    /**
     * Notify the observator when an error appear
     * 
     * @param error String[]: all the error appear
     */
    public void notifyHelp(String... error) {
        change(PROPERTY_PATH_ERROR, error);
    }
}
