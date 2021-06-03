package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.*;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

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
     * Constructs a connect four manager.
     *
     * @param board   The board of the game.
     * @param game    The game.
     * @param player1 The first player.
     * @param player2 The second player.
     * @throws IllegalArgumentException If the players have the same IDs or board, game, player1 or player2 is null.
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
     *
     * @param object  Object to check.
     * @param message Message to pass to Exception.
     */
    private void throwExceptionOnNull(Object object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
    }

    @Override
    public List<Move> getMoves(PlayerID playerID) {
        final List<Move> possibleMoves;
        if (game.isStarted()) {
            if (game.getWinner() == PlayerID.NONE && game.getActivePlayer() != PlayerID.NONE)
                possibleMoves = MoveHelper.getMovesInRegularGame(board, game);
            else
                possibleMoves = List.of(Move.CONFIRM);
        } else
            possibleMoves = MoveHelper.getMovesInPlayerSelect();
        return possibleMoves;
    }

    @Override
    public boolean executeMove(Move move) {
        final boolean result;
        if (game.isStarted()) {
            if (game.getActivePlayer() != PlayerID.NONE) {
                result = executeMoveInActiveGame(move);
            } else {
                if (move == Move.CONFIRM) {
                    //Restarts the game in the End screen
                    restart();
                    result = true;
                } else
                    result = false;
            }
        } else
            result = executePlayerSelectScreen(move);

        game.notifyObservers(board, game, player1, player2);

        return result;
    }

    /**
     * Executes a move in a started game that is not won.
     *
     * @param move The move to execute.
     * @return If the move was successful.
     */
    private boolean executeMoveInActiveGame(Move move) {
        final boolean result;
        final FullPlayer activePlayer = game.getActivePlayer() == PlayerID.PLAYER_1 ? player1 : player2;
        final List<Move> allowedMoves = this.getMoves(activePlayer.getIdentifier());
        final boolean allowed = allowedMoves.stream()
                .anyMatch(allowedMove -> allowedMove.equals(move));

        if (allowed) {
            executeAllowedMove(move, activePlayer);
            WinHelper.setGameState(board, game); // Checks if the game is won
            result = true;
        } else
            result = false;

        return result;
    }

    /**
     * Execute an allowed move.
     *
     * @param move         The current taken move.
     * @param activePlayer The player that is taking the move.
     */
    private void executeAllowedMove(Move move, FullPlayer activePlayer) {
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
                ExecuteMoveHandler.onExecute(move, currentHighlight, game, board, activePlayer);
        } else
            ExecuteMoveHandler.onExecute(move, currentHighlight, game, board, activePlayer);
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
     * Executes a move in the player select screen.
     * If the player confirms his human player amount it also creates the initial highlight.
     *
     * @param move The triggered move.
     * @return if the move returns successfully.
     */
    private boolean executePlayerSelectScreen(Move move) {
        final boolean result;

        final boolean allowedInPlayerSelect = MoveHelper.getMovesInPlayerSelect().stream()
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

    private static class MoveHelper {
        /**
         * All available Moves during the Game.
         *
         * @return All valid moves.
         */
        private static List<Move> getMovesInRegularGame(Board board, Game game) {
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
                possibleMoves = boardSelection(board, target);
            else
                throw new UnsupportedOperationException("Not yet Implemented");

            return possibleMoves;
        }

        /**
         * Finds the accepted Moves in the menu.
         *
         * @param target The first Field of the highlight.
         * @param player The ID of the player which selects the menu
         * @return All possible Moves.
         */
        private static List<Move> menuSelection(Field target, PlayerID player) {
            final List<Move> possibleMoves;
            final int targetXCord = target.xCoordinate();

            final int resetButton = 3;
            final int endButton = 4;

            if (isSelectionInPlayersJokers(targetXCord, player) ||
                    targetXCord == resetButton ||
                    targetXCord == endButton)
                possibleMoves = List.of(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM);
            else
                possibleMoves = List.of(Move.RIGHT, Move.LEFT, Move.DOWN);

            return possibleMoves;
        }

        /**
         * Checks if the provided xCoordinate is in the provided players joker fields (1,2 or 6,7 respectively)
         *
         * @param xCoordinate Selected Coordinate.
         * @param playerID    Selecting Player.
         * @return Whether selection is in provided players jokers or not.
         */
        private static boolean isSelectionInPlayersJokers(int xCoordinate, PlayerID playerID) {
            final int player1HighestJokerIndex = 1;
            final int player2LowestJokerIndex = 6;
            return (xCoordinate <= player1HighestJokerIndex && playerID == PlayerID.PLAYER_1)
                    || (xCoordinate >= player2LowestJokerIndex && playerID == PlayerID.PLAYER_2);
        }

        /**
         * Finds the accepted Moves on the regular board.
         *
         * @param target The first Field of the highlight.
         * @return All possible Moves.
         */
        private static List<Move> boardSelection(Board board, Field target) {
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
                possibleMoves = List.of(Move.UP, Move.RIGHT, Move.LEFT);
            else
                possibleMoves = List.of(Move.CONFIRM, Move.UP, Move.RIGHT, Move.LEFT);

            return possibleMoves;
        }

        /**
         * All available Moves in the player select screen.
         *
         * @return All valid moves (Confirm, Right, Left).
         */
        private static List<Move> getMovesInPlayerSelect() {
            return List.of(Move.CONFIRM, Move.RIGHT, Move.LEFT);
        }
    }

    private static class WinHelper {
        /**
         * Sets the game state by checking if somebody won the game.
         */
        private static void setGameState(Board board, FullGame game) {
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

            if (player1Win || player2Win || boardFull) {
                game.setWinner(getWinner(player1Win, player2Win));
                game.setActivePlayer(PlayerID.NONE);
            }
        }

        /**
         * Returns the winner. None if both or none are winning.
         *
         * @param player1Win Whether Player1 has a winning sequence on board.
         * @param player2Win Whether Player2 has a winning sequence on board.
         * @return The PlayerID of the winner or NONE for a tie.
         */
        private static PlayerID getWinner(boolean player1Win, boolean player2Win) {
            final PlayerID winner;
            if (player1Win ^ player2Win)
                winner = player1Win ? PlayerID.PLAYER_1 : PlayerID.PLAYER_2;
            else
                winner = PlayerID.NONE;
            return winner;
        }

        /**
         * Proofs if the matrix contains a winning sequence.
         *
         * @param fields all fields that need to be proved.
         * @return if the fields contain a winning sequence.
         */
        private static boolean containsWinningSequence(List<Field> fields) {
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

        private static boolean winningSequenceRight(List<Field> fields) {
            final int maxAdderValue = 3;
            final int fieldSize = 8;

            return fields.stream()
                    .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate(), field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate(), field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + 3, field.yCoordinate(), field.owner())));
        }

        private static boolean winningSequenceUp(List<Field> fields) {
            final int maxAdderValue = 3;
            final int fieldSize = 8;

            return fields.stream()
                    .filter(field -> field.yCoordinate() < fieldSize - maxAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 1, field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 2, field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 3, field.owner())));
        }

        private static boolean winningSequenceDiagonalUpLeftDownright(List<Field> fields) {
            final int minAdderValue = 2;

            return fields.stream()
                    .filter(field -> field.xCoordinate() > minAdderValue)
                    .filter(field -> field.yCoordinate() > minAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 1, field.yCoordinate() - 1, field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 2, field.yCoordinate() - 2, field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() - 3, field.yCoordinate() - 3, field.owner())));
        }

        private static boolean winningSequenceDiagonalRightUp(List<Field> fields) {
            final boolean result;

            final int maxAdderValue = 3;
            final int minAdderValue = 2;
            final int fieldSize = 8;

            return fields.stream()
                    .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                    .filter(field -> field.yCoordinate() > minAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate() - 1, field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate() - 2, field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + 3, field.yCoordinate() - 3, field.owner())));
//            result = fields.stream()
//                .filter(field -> field.xCoordinate() > minAdderValue)
//                .filter(field -> field.yCoordinate() < fieldSize - maxAdderValue)
//                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 1, field.yCoordinate() + 1, field.owner())))
//                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 2, field.yCoordinate() + 2, field.owner())))
//                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() - 3, field.yCoordinate() + 3, field.owner())));
        }
    }
}
