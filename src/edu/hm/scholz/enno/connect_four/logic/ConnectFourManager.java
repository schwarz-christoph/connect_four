package edu.hm.scholz.enno.connect_four.logic;

import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Game;
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

    /**
     * the board.
     */
    private FullBoard board;

    /**
     * the game.
     */
    private FullGame game;

    /**
     * the player1.
     */
    private FullPlayer player1;

    /**
     * the player2.
     */
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
                possibleMoves = MoveHelper.getMovesInRegularGame(board, game, getActiveFullPLayer());
            else
                possibleMoves = List.of(Move.CONFIRM);
        } else
            possibleMoves = MoveHelper.getMovesInPlayerSelect();
        return possibleMoves;
    }

    @Override
    public PlayerID executeMove(Move move) {
        if (game.isStarted()) {
            if (game.getActivePlayer() == PlayerID.NONE) {
                if (move == Move.CONFIRM) {
                    //Restarts the game in the End screen
                    restart();
                }
            } else {
                executeMoveInActiveGame(move);
            }
        } else
            executePlayerSelectScreen(move);

        game.notifyObservers(board, game, player1, player2);

        return game.getActivePlayer();
    }

    /**
     * Executes a move in a started game that is not won.
     *
     * @param move The move to execute.
     * @return If the move was successful.
     */
    private boolean executeMoveInActiveGame(Move move) {
        final boolean result;
        final FullPlayer activePlayer = getActiveFullPLayer();
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
     * Resets the board, the players and the game state.
     */
    private void restart() {
        final FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        final FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);

        new ArrayList<>(board.getFields()).forEach(board::removeStone);
        board.setHighlight(List.of(Factory.makeField(2, 0, PlayerID.NONE)));
        game.setActivePlayer(PlayerID.PLAYER_1);
        game.setWinner(PlayerID.NONE);

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

    /**
     * The Active FullPlayer of the game.
     * @return The active player as a FullPlayer.
     */
    private FullPlayer getActiveFullPLayer() {
        return game.getActivePlayer() == PlayerID.PLAYER_1 ? player1 : player2;
    }

    /**
     * Nested class that contains methods for getting Moves.
     */
    private static class MoveHelper {
        /**
         * All available Moves during the Game.
         *
         * @param board        The board.
         * @param game         The game.
         * @param activePlayer The active player.
         * @return All valid moves.
         */
        private static List<Move> getMovesInRegularGame(Board board, Game game, FullPlayer activePlayer) {
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
                possibleMoves = menuSelection(target, activePlayer);
            else if (target.yCoordinate() == firstMatrixYCoordinate)
                possibleMoves = boardSelection(board, target);
            else
                throw new UnsupportedOperationException("Not yet Implemented");

            return possibleMoves;
        }

        /**
         * Finds the accepted Moves in the menu.
         *
         * @param target       The first Field of the highlight.
         * @param activePlayer The active player.
         * @return All possible Moves.
         */
        private static List<Move> menuSelection(Field target, FullPlayer activePlayer) {
            final int xCoordinate = target.xCoordinate();
            final int resetButton = 3;
            final int endButton = 4;

            final List<Move> possibleMoves;

            if (xCoordinate == resetButton || xCoordinate == endButton) {
                possibleMoves = List.of(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM);

            } else {
                final int player1BombJoker = 0;
                final int player1DeleteJoker = 1;
                final int player2DeleteJoker = 6;
                final int player2BombJoker = 7;
                final int bombJoker;
                final int deleteJoker;

                if (activePlayer.getIdentifier() == PlayerID.PLAYER_1) {
                    bombJoker = player1BombJoker;
                    deleteJoker = player1DeleteJoker;
                } else {
                    bombJoker = player2BombJoker;
                    deleteJoker = player2DeleteJoker;
                }

                possibleMoves = jokerMenuMoves(xCoordinate, activePlayer, bombJoker, deleteJoker);
            }

            return possibleMoves;
        }

        /**
         * Checks if the provided xCoordinate is in the provided players joker fields (1,2 or 6,7 respectively).
         *
         * @param xCoordinate  Selected Coordinate.
         * @param targetPlayer the player who wants to select a joker.
         * @param bombJoker    The bombJokerJokerCoordinate from the current Player.
         * @param deleteJoker  The bombDeleteJokerCoordinate from the current Player.
         * @return All possible moves.
         */
        private static List<Move> jokerMenuMoves(int xCoordinate, FullPlayer targetPlayer, int bombJoker, int deleteJoker) {
            final List<Move> movesWithConfirm = List.of(Move.RIGHT, Move.LEFT, Move.DOWN, Move.CONFIRM);
            final List<Move> movesWithoutConfirm = List.of(Move.RIGHT, Move.LEFT, Move.DOWN);
            final List<Move> possibleMoves;

            if (xCoordinate == bombJoker)
                //BombJoker
                if (targetPlayer.isBombJokerUsed())
                    possibleMoves = movesWithoutConfirm;
                else
                    possibleMoves = movesWithConfirm;

            else if (xCoordinate == deleteJoker)
                //DeleteJoker
                if (targetPlayer.isDeleteJokerUsed())
                    possibleMoves = movesWithoutConfirm;
                else
                    possibleMoves = movesWithConfirm;

            else
                possibleMoves = movesWithoutConfirm;

            return possibleMoves;
        }

        /**
         * Finds the accepted Moves on the regular board.
         *
         * @param board  The board.
         * @param target The first Field of the highlight.
         * @return All possible Moves.
         */
        private static List<Move> boardSelection(Board board, Field target) {
            final List<Move> possibleMoves;
            final int firstMatrixYCoordinate = 1;

            final boolean isFull = board.getFields().stream()
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

    /**
     * Nested class that contains methods for determining the winner of the game.
     */
    private static class WinHelper {
        /**
         * Sets the game state by checking if somebody won the game.
         *
         * @param board The board.
         * @param game  The game.
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

            final int fieldSize = 8;
            final boolean player1Win = containsWinningSequence(player1Fields);
            final boolean player2Win = containsWinningSequence(player2Fields);
            final boolean boardFull = fields.size() == fieldSize * (fieldSize - 1);

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
         * Checks if the matrix contains a winning sequence.
         *
         * @param fields all fields that need to be proved.
         * @return if the fields contain a winning sequence.
         */
        private static boolean containsWinningSequence(List<Field> fields) {
            final boolean result;

            if (winningSequenceHorizontal(fields))
                result = true;
            else if (winningSequenceVertical(fields))
                result = true;
            else if (winningSequenceDiagonalDownward(fields))
                result = true;
            else result = winningSequenceDiagonalUpward(fields);

            return result;
        }

        /**
         * Checks if the provided list contains a winning sequence in horizontal direction.
         *
         * @param fields List to check for winning sequence.
         * @return Whether the list contains a winning sequence or not.
         */
        private static boolean winningSequenceHorizontal(List<Field> fields) {
            final int maxAdderValue = 3;
            final int fieldSize = 8;

            return fields.stream()
                    .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate(), field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate(), field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + maxAdderValue, field.yCoordinate(), field.owner())));
        }

        /**
         * Checks if the provided list contains a winning sequence in vertical direction.
         *
         * @param fields List to check for winning sequence.
         * @return Whether the list contains a winning sequence or not.
         */
        private static boolean winningSequenceVertical(List<Field> fields) {
            final int maxAdderValue = 3;
            final int fieldSize = 8;

            return fields.stream()
                    .filter(field -> field.yCoordinate() < fieldSize - maxAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 1, field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 2, field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + maxAdderValue, field.owner())));
        }

        /**
         * Checks if the provided list contains a winning sequence in diagonal downward direction.
         *
         * @param fields List to check for winning sequence.
         * @return Whether the list contains a winning sequence or not.
         */
        private static boolean winningSequenceDiagonalDownward(List<Field> fields) {
            final int minAdderValue = 2;
            final int maxAdderValue = 3;

            return fields.stream()
                    .filter(field -> field.xCoordinate() > minAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 1, field.yCoordinate() - 1, field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 2, field.yCoordinate() - 2, field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() - maxAdderValue, field.yCoordinate() - maxAdderValue, field.owner())));
        }

        /**
         * Checks if the provided list contains a winning sequence in diagonal upward direction.
         *
         * @param fields List to check for winning sequence.
         * @return Whether the list contains a winning sequence or not.
         */
        private static boolean winningSequenceDiagonalUpward(List<Field> fields) {
            final int maxAdderValue = 3;
            final int fieldSize = 8;

            return fields.stream()
                    .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate() - 1, field.owner())))
                    .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate() - 2, field.owner())))
                    .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + maxAdderValue, field.yCoordinate() - maxAdderValue, field.owner())));
        }
    }
}
