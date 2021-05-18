package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.*;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ConnectFourManager implements GameManager {

    private final FullGame game;
    private final FullPlayer player1;
    private final FullPlayer player2;

    public ConnectFourManager(FullGame game, FullPlayer player1, FullPlayer player2) {
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
    }


    @Override
    public List<Move> getMoves() {
        final List<Move> possibleMoves;
        if (game.isStarted()) {
            if (game.getWinner() == PlayerID.NONE) {
                possibleMoves = getMovesInRegularGame();
            } else {
                possibleMoves = getMovesInEndScreen();
            }
        } else {
            possibleMoves = getMovesInPlayerSelect();
        }
        return possibleMoves;
    }

    @Override
    public void executeMove(Move move) {
        final List<Field> currentHighlight = game.getBoard().getHighlight();
        final Field target = currentHighlight.get(0);


    }

    private void createHighlight(Move move, Field targetHighlight) {

    }

    private void createMenuHighlight(Move move, Field targetHighlight) {

    }

    private void createBombJokerHighlight(Move move, Field targetHighlight) {

    }

    private void createDeleteJokerHighlight(Move move, Field targetHighlight) {

    }

    private void createBombJoker(Move move, Field targetHighlight) {

    }

    private void createDeleteJoker(Move move, Field targetHighlight) {

    }

    private void executeBombJoker(Move move, Field targetHighlight) {

    }

    private void executeDeleteJoker(Move move, Field targetHighlight) {

    }

    private void createStone(Move move, Field targetHighlight) {

    }

    private void restart() {

    }

    private void end() {

    }

    /**
     * All available Moves in the player select screen.
     *
     * @return All valid moves (Confirm, Right, Left).
     */
    private List<Move> getMovesInPlayerSelect() {
        return new ArrayList<>(Arrays.asList(Move.CONFIRM, Move.RIGHT, Move.LEFT));
    }

    /**
     * All available Moves during the Game.
     *
     * @return All valid moves.
     */
    private List<Move> getMovesInRegularGame() {
        final List<Move> possibleMoves;
        final List<Field> highlight = game.getBoard().getHighlight();
        final Field target = highlight.get(0);

        if (target.yCoordinate() == 0) {
            possibleMoves = menuSelection(target, game.getActivePlayer());
        } else if (target.yCoordinate() == 1) {
            possibleMoves = playgroundSelection(target);
        } else {
            throw new UnsupportedOperationException("Not yet Implemented");
        }
        return possibleMoves;
    }

    /**
     * Finds the accepted Moves in the playground.
     *
     * @param target The first Field of the highlight.
     * @return All possible Moves
     */
    private List<Move> playgroundSelection(Field target) {
        final List<Move> possibleMoves;
        final Stream<Field> occupiedFields = game.getBoard().getFields().stream();

        if (occupiedFields.anyMatch(field -> field.equals(target))) {
            possibleMoves = new ArrayList<>(Arrays.asList(Move.UP, Move.RIGHT, Move.LEFT));
        } else {
            possibleMoves = new ArrayList<>(Arrays.asList(Move.CONFIRM, Move.UP, Move.RIGHT, Move.LEFT));
        }

        return possibleMoves;
    }

    /**
     * Finds the accepted Moves in the menu
     *
     * @param target The first Field of the highlight.
     * @return All possible Moves
     */
    private List<Move> menuSelection(Field target, PlayerID player) {
        final List<Move> possibleMoves = new ArrayList<>(Arrays.asList(Move.RIGHT, Move.LEFT, Move.DOWN));
        final int targetXCord = target.xCoordinate();

        if(player == PlayerID.PLAYER_1) {
            //Player1
            if(targetXCord < 2){
                //Player1 in the Joker Menu
                possibleMoves.add(Move.CONFIRM);
            }
        } else {
            //Player2
            if(targetXCord > 5){
                //Player2 in the Joker Menu
                possibleMoves.add(Move.CONFIRM);
            }
        }

        if(targetXCord < 5 && targetXCord > 2){
            //One of the Player in the Game-Menu
            possibleMoves.add(Move.CONFIRM);
        }
        return possibleMoves;
    }

    /**
     * All available moves in the end screen.
     *
     * @return All valid moves (Confirm).
     */
    private List<Move> getMovesInEndScreen() {
        return new ArrayList<>(Arrays.asList(Move.CONFIRM));
    }
}