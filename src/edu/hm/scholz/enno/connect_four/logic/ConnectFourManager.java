package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Logic of the connect four game.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-20-2021
 */
public class ConnectFourManager implements GameManager {

    private FullBoard board;
    private FullGame game;
    private FullPlayer player1;
    private FullPlayer player2;

    /**
     * Construcs a connect four manager.
     *
     * @param board   the board of the game
     * @param game    the game
     * @param player1 the first player
     * @param player2 the second player
     * @throws IllegalArgumentException if the players have similar IDs or board, game, player1 or player2 is null
     */
    public ConnectFourManager(FullBoard board, FullGame game, FullPlayer player1, FullPlayer player2) {
        throwExceptionOnNull(board, "Board can't be null.");
        throwExceptionOnNull(game, "Game can't be null.");
        throwExceptionOnNull(player1, "Player1 can't be null.");
        throwExceptionOnNull(player2, "Player2 can't be null.");

        if (player1.getIdentifier() == player2.getIdentifier())
            throw new IllegalArgumentException("Player must have different IDs.");

        this.board = board;
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Throws an IllegalArgumentException with the provided message if the provided object is null.
     * @param object  Object to check.
     * @param message Message to pass to Exception.
     */
    private void throwExceptionOnNull(Object object, String message) {
        if(object == null)
            throw new IllegalArgumentException(message);
    }

    @Override
    public List<Move> getMoves(PlayerID playerID) {
        final List<Move> possibleMoves;
        if (game.isStarted()) {
            if (game.getWinner() == PlayerID.NONE && game.getActivePlayer() != PlayerID.NONE)
                possibleMoves = getMovesInRegularGame();
            else
                possibleMoves = getMovesInEndScreen();
        } else
            possibleMoves = getMovesInPlayerSelect();
        return possibleMoves;
    }

    @Override
    public boolean executeMove(Move move) {
        final boolean result;
        if (game.isStarted()) {
            if (game.getWinner() == PlayerID.NONE && game.getActivePlayer() != PlayerID.NONE) {
                result = movesInActiveGame(move);
            } else {
                if (move == Move.CONFIRM) {
                    //Restarts the game in the End screen
                    restart();
                    result = true;
                } else
                    result = false;
            }
        } else
            result = playerSelectScreen(move);

        game.notifyObservers(board, game, player1, player2);

        return result;
    }

    /**
     * If the Game hast started, no Winner and continues
     * @param move the move
     * @return if the move was successfully
     */
    private boolean movesInActiveGame(Move move){
        final boolean result;
        final FullPlayer activePlayer = game.getActivePlayer() == PlayerID.PLAYER_1 ? player1 : player2;
        final List<Move> allowedMoves = this.getMoves(activePlayer.getIdentifier());
        final boolean allowed = allowedMoves.stream()
                .anyMatch(allowedMove -> allowedMove.equals(move));

        if (allowed) {
            manageAllowedMoves(move, activePlayer);
            setGameState(); // Checks if the game is won
            result = true;
        } else
            result = false;

        return result;
    }

    /**
     * Manage the Move if its allowed.
     *
     * @param move the current taken move.
     */
    private void manageAllowedMoves(Move move, FullPlayer activePlayer) {
        final List<Field> currentHighlight = board.getHighlight();
        final int targetFieldXCoordinate = currentHighlight.get(0).xCoordinate();
        final int targetFieldYCoordinate = currentHighlight.get(0).yCoordinate();
        if (targetFieldYCoordinate == 0 && move == Move.CONFIRM) {

            final int menuButtonEnd = 3;
            final int menuButtonRestart = 4;

            if (targetFieldXCoordinate == menuButtonEnd)
                end();
            else if (targetFieldXCoordinate == menuButtonRestart)
                restart();
            else
                ExecuteMoveHandler.onEcexute(move, currentHighlight, game, board, activePlayer);
        } else
            ExecuteMoveHandler.onEcexute(move, currentHighlight, game, board, activePlayer);

    }

    /**
     * Restarts the game.
     */
    private void restart() {
        final FullGame oldGame = this.game;

        final FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        final FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        this.board = Factory.makeBoard();
        this.game = Factory.makeGame(oldGame.getActivePlayer(), board);
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Ends the game immediately.
     */
    private void end() {
        this.game.setActivePlayer(PlayerID.NONE);
    }

    /**
     * Creates the Playerselectscreen.
     * If the player confirms his human player amount it also creates the intial highlight.
     *
     * @param move the move that's taken.
     * @return if the move returns successfully.
     */
    private boolean playerSelectScreen(Move move) {
        final boolean result;

        final boolean allowedInPlayerSelect = getMovesInPlayerSelect().stream()
                .anyMatch(allowedMove -> allowedMove.equals(move));

        if (allowedInPlayerSelect) {
            if (move == Move.RIGHT)
                game.setPlayerCount(2);
            else if (move == Move.LEFT)
                game.setPlayerCount(1);
            else {
                game.setIsStarted(true);
                board.setHighlight(List.of(Factory.makeField(2, 0, PlayerID.NONE)));
            }
            result = true;
        } else
            result = false;

        return result;
    }

    /**
     * Sets the game stated by proofing if somebody won's the game.
     */
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
        final boolean boardFull = fields.size() == 8 * 7;

        if (player1Win) {
            game.setWinner(PlayerID.PLAYER_1);
            game.setActivePlayer(PlayerID.NONE);
        }

        if (player2Win) {
            game.setWinner(PlayerID.PLAYER_2);
            game.setActivePlayer(PlayerID.NONE);
        }

        if ((player1Win && player2Win) || boardFull) {
            game.setWinner(PlayerID.NONE);
            game.setActivePlayer(PlayerID.NONE);
        }
    }

    /**
     * Proofs if the matrix contains a winning sequence.
     *
     * @param fields all fields that need to be proved.
     * @return if the fields contain a winning sequence.
     */
    private boolean containsWinningSequence(List<Field> fields) {
        final boolean result;

        if (winningSequenceRight(fields))
            result = true;
        else if (winningSequenceUp(fields))
            result = true;
        else if (winningSequenceDiagonalUpLeftDownright(fields))
            result = true;
        else result = winningSequenceDiagonalRightUp(fields);

        return result;
    }

    private boolean winningSequenceRight(List<Field> fields) {

        final int maxAdderValue = 3;
        final int fieldSize = 8;

        return fields.stream()
                .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate(), field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate(), field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + 3, field.yCoordinate(), field.owner())));
    }

    private boolean winningSequenceUp(List<Field> fields) {

        final int maxAdderValue = 3;
        final int fieldSize = 8;

        return fields.stream()
                .filter(field -> field.yCoordinate() < fieldSize - maxAdderValue)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 1, field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 2, field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 3, field.owner())));
    }

    private boolean winningSequenceDiagonalUpLeftDownright(List<Field> fields) {

        final int minAdderValue = 2;

        return fields.stream()
                .filter(field -> field.xCoordinate() > minAdderValue)
                .filter(field -> field.yCoordinate() > minAdderValue)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 1, field.yCoordinate() - 1, field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 2, field.yCoordinate() - 2, field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() - 3, field.yCoordinate() - 3, field.owner())));
    }

    private boolean winningSequenceDiagonalRightUp(List<Field> fields) {
        boolean result;

        final int maxAdderValue = 3;
        final int minAdderValue = 2;
        final int fieldSize = 8;

        result = fields.stream()
                .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                .filter(field -> field.yCoordinate() > minAdderValue)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate() - 1, field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate() - 2, field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + 3, field.yCoordinate() - 3, field.owner())));
        if (!result)
            result = fields.stream()
                    .filter(field -> field.xCoordinate() > minAdderValue)
                    .filter(field -> field.yCoordinate() < fieldSize - maxAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 1, field.yCoordinate() + 1, field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 2, field.yCoordinate() + 2, field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() - 3, field.yCoordinate() + 3, field.owner())));

        return result;
    }

    /**
     * All available Moves in the player select screen.
     *
     * @return All valid moves (Confirm, Right, Left).
     */
    private List<Move> getMovesInPlayerSelect() {
        return new ArrayList<>(List.of(Move.CONFIRM, Move.RIGHT, Move.LEFT));
    }

    /**
     * All available Moves during the Game.
     *
     * @return All valid moves.
     */
    private List<Move> getMovesInRegularGame() {
        final List<Move> possibleMoves;
        final List<Field> highlight = board.getHighlight();
        final Field target = highlight.get(0);

        final int menuYCoordinate = 0;
        final int firstMatrixYCoordinate = 1;

        if (game.getActiveJoker() == PlayerActiveJoker.BOMB)
            possibleMoves = List.of(Move.CONFIRM, Move.RIGHT, Move.LEFT);
        else if (game.getActiveJoker() == PlayerActiveJoker.DELETE)
            possibleMoves = List.of(Move.CONFIRM, Move.UP, Move.DOWN, Move.RIGHT, Move.LEFT);
        else if (target.yCoordinate() == menuYCoordinate)
            possibleMoves = menuSelection(target, game.getActivePlayer());
        else if (target.yCoordinate() == firstMatrixYCoordinate)
            possibleMoves = playgroundSelection(target);
        else
            throw new UnsupportedOperationException("Not yet Implemented");

        return possibleMoves;
    }

    /**
     * Finds the accepted Moves in the playground.
     *
     * @param target The first Field of the highlight.
     * @return All possible Moves.
     */
    private List<Move> playgroundSelection(Field target) {
        final List<Move> possibleMoves;
        final boolean isFull;

        final int firstMatrixYCoordinate = 1;

        if (board.getFields().isEmpty())
            isFull = false;
        else
            isFull = board.getFields().stream()
                    .filter(field -> field.xCoordinate() == target.xCoordinate())
                    .anyMatch(field -> field.yCoordinate() == firstMatrixYCoordinate);



        if (isFull)
            possibleMoves = new ArrayList<>(List.of(Move.UP, Move.RIGHT, Move.LEFT));
        else
            possibleMoves = new ArrayList<>(List.of(Move.CONFIRM, Move.UP, Move.RIGHT, Move.LEFT));

        return possibleMoves;
    }

    /**
     * Finds the accepted Moves in the menu.
     *
     * @param target The first Field of the highlight.
     * @param player The ID of the player which selects the menu
     * @return All possible Moves.
     */
    private List<Move> menuSelection(Field target, PlayerID player) {
        final List<Move> possibleMoves = new ArrayList<>(List.of(Move.RIGHT, Move.LEFT, Move.DOWN));
        final int targetXCord = target.xCoordinate();

        final int spareField1Menu = 2;
        final int spareField2Menu = 5;

        if (player == PlayerID.PLAYER_1) {
            //Player1
            if (targetXCord < spareField1Menu)
                //Player1 in the Joker Menu
                possibleMoves.add(Move.CONFIRM);
        } else {
            //Player2
            if (targetXCord > spareField2Menu)
                //Player2 in the Joker Menu
                possibleMoves.add(Move.CONFIRM);
        }

        if (targetXCord < spareField2Menu && targetXCord > spareField1Menu)
            //One of the Player in the Game-Menu
            possibleMoves.add(Move.CONFIRM);

        return possibleMoves;
    }

    /**
     * All available moves in the end screen.
     *
     * @return All valid moves (Confirm).
     */
    private List<Move> getMovesInEndScreen() {
        return new ArrayList<>(List.of(Move.CONFIRM));
    }
}
