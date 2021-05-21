package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.*;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectFourManager implements GameManager {

    private final FullGame game;
    private final FullPlayer player1;
    private final FullPlayer player2;

    public ConnectFourManager(FullGame game, FullPlayer player1, FullPlayer player2) {

        if(game == null){
            throw new NullPointerException("Game cant be null");
        }

        if(player1 == null || player2 == null){
            throw new NullPointerException("Player cant be null");
        }

        if (player1.getIdentifier() == player2.getIdentifier()) {
            throw new IllegalArgumentException("Player must have different IDs.");
        }

        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
    }


    @Override
    public List<Move> getMoves(PlayerID playerID) {
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
    public boolean executeMove(Move move, PlayerID playerID) {

        final boolean result;

        final List<Move> allowedMoves = this.getMoves(playerID);
        final boolean allowed = allowedMoves.stream()
                .anyMatch(allowedMoves::equals);

        if(allowed){
            final List<Field> currentHighlight = game.getBoard().getHighlight();
            ExecuteMoveHandler.onEcexute(move, currentHighlight, game);
            setGameState(); // Checks if the game is won
            //TODO extend it until the next player is ready to play

            result = true;
        }else{
            result = false;
        }


        return result;
    }

    private void setGameState() {
        final List<Field> fields = board.getFields();

        //Player 1 Stone List
        final List<Field> player1Fields = fields.stream()
                .filter(field -> field.owner() == PlayerID.PLAYER_1)
                .collect(Collectors.toList());

        //Player 2 Stone List
        final List<Field> player2Fields = fields.stream()
                .filter(field -> field.owner() == PlayerID.PLAYER_2)
                .collect(Collectors.toList());


        final boolean player1Win = containsWinningSequence(player1Fields);
        final boolean player2Win = containsWinningSequence(player2Fields);

        if (player1Win) {
            game.setWinner(PlayerID.PLAYER_1);
            game.setActivePlayer(PlayerID.NONE);
        }

        if (player2Win) {
            game.setWinner(PlayerID.PLAYER_2);

        if (player1Win && player2Win) {
            game.setWinner(PlayerID.NONE);
            game.setActivePlayer(PlayerID.NONE);
        }
    }

    private boolean containsWinningSequence(List<Field> fields) {
        final boolean result;

        if (winningSequenceRight(fields)) {
            result = true;
        } else if (winningSequenceUp(fields)) {
            result = true;
        } else if (winningSequenceDiagonalUpLeftDownright(fields)) {
            result = true;
        } else if (winningSequenceDiagonalRightUp(fields)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private boolean winningSequenceRight(List<Field> fields) {
        return fields.stream()
                .filter(field -> field.xCoordinate() < Settings.fieldSize - 3)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate(), field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate(), field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + 3, field.yCoordinate(), field.owner())));
    }

    private boolean winningSequenceUp(List<Field> fields) {
        return fields.stream()
                .filter(field -> field.yCoordinate() < Settings.fieldSize - 4)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 1, field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 2, field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 3, field.owner())));
    }

    private boolean winningSequenceDiagonalUpLeftDownright(List<Field> fields) {
        return fields.stream()
                .filter(field -> field.xCoordinate() > 3)
                .filter(field -> field.yCoordinate() < Settings.fieldSize - 4)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 1, field.yCoordinate() + 1, field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 2, field.yCoordinate() + 2, field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() - 3, field.yCoordinate() + 3, field.owner())));
    }

    private boolean winningSequenceDiagonalRightUp(List<Field> fields) {
        return fields.stream()
                .filter(field -> field.xCoordinate() < Settings.fieldSize - 3)
                .filter(field -> field.yCoordinate() > 3)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate() - 1, field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate() - 2, field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + 3, field.yCoordinate() - 3, field.owner())));
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

        if (player == PlayerID.PLAYER_1) {
            //Player1
            if (targetXCord < 2) {
                //Player1 in the Joker Menu
                possibleMoves.add(Move.CONFIRM);
            }
        } else {
            //Player2
            if (targetXCord > 5) {
                //Player2 in the Joker Menu
                possibleMoves.add(Move.CONFIRM);
            }
        }

        if (targetXCord < 5 && targetXCord > 2) {
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