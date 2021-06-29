package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.Player;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;

import java.util.List;
import java.util.stream.Collectors;

/**
 * View for theory tests.
 */
public record ValidatingView(Game game) implements View {

    /**
     * Validates the view and registers changes
     *
     * @param game the current game
     * @throws IllegalArgumentException if the game is null
     */
    public ValidatingView(Game game) {
        if (game == null)
            throw new IllegalArgumentException("Arguments cannot be null.");

        this.game = game;

        game.register(this);
    }

    @Override
    public void update(Board board, Game game, Player player1, Player player2) {
        //Do nothing
    }

    @Override
    public void shut() {
        View.super.shut();
    }

    /**
     * Validates the game
     * @return if the final game state is valid
     */
    public boolean validateGame() {
        boolean isValidGame = false;
        final Board board = game.getBoard();
        final PlayerID wantPlayerID;
        final boolean winnerPlayer1 = validateWinner(board, PlayerID.PLAYER_1);
        final boolean winnerPlayer2 = validateWinner(board, PlayerID.PLAYER_2);
        final List<Field> fields = board.getFields();
        final int fieldSize = 8;

        if (winnerPlayer1 ^ winnerPlayer2) // ^ = exclusive or
            wantPlayerID = winnerPlayer1 ? PlayerID.PLAYER_1 : PlayerID.PLAYER_2;
        else
            wantPlayerID = PlayerID.NONE;

        if (game.getWinner() == wantPlayerID) {
            isValidGame = true;
        }

        if (isValidGame) {
            isValidGame = game.getActivePlayer() == PlayerID.NONE;
        }
        return isValidGame;
    }

    /**
     * Validate the winner
     * @param board the board of the game
     * @param wantPlayerID the playerID witch should win
     * @return if the player has stones in a winning order
     */
    private boolean validateWinner(Board board, PlayerID wantPlayerID) {
        final List<Field> fields = board.getFields();

        final List<Field> playerFields = fields.stream()
                .filter(field -> field.owner() == wantPlayerID)
                .collect(Collectors.toList());

        return containsWinningSequence(playerFields);
    }

    /**
     * Winning sequence test
     * @param fields the fields of one player
     * @return if the winning sequence is in the field
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
     * Winning sequence horizontal
     * @param fields the fields witch should be proofed
     * @return if the winning sequence is horizontal
     */
    private static boolean winningSequenceHorizontal(List<Field> fields) {
        final int maxAdderValue = 3;
        final int fieldSize = 8;

        return fields.stream()
                .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                .filter(field -> containsFields(fields, field.xCoordinate() + 1, field.yCoordinate(), field.owner()))
                .filter(field -> containsFields(fields, field.xCoordinate() + 2, field.yCoordinate(), field.owner()))
                .anyMatch(field -> containsFields(fields, field.xCoordinate() + maxAdderValue, field.yCoordinate(), field.owner()));
    }

    /**
     * Winning sequence vertical
     * @param fields the fields witch should be proofed
     * @return if the winning sequence is vertical
     */
    private static boolean winningSequenceVertical(List<Field> fields) {
        final int maxAdderValue = 3;
        final int fieldSize = 8;

        return fields.stream()
                .filter(field -> field.yCoordinate() < fieldSize - maxAdderValue)
                .filter(field -> containsFields(fields, field.xCoordinate(), field.yCoordinate() + 1, field.owner()))
                .filter(field -> containsFields(fields, field.xCoordinate(), field.yCoordinate() + 2, field.owner()))
                .anyMatch(field -> containsFields(fields, field.xCoordinate(), field.yCoordinate() + maxAdderValue, field.owner()));
    }

    /**
     * Winning sequence diagonal downward
     * @param fields the fields witch should be proofed
     * @return if the winning sequence is diagonal downward
     */
    private static boolean winningSequenceDiagonalDownward(List<Field> fields) {
        final int minAdderValue = 2;
        final int maxAdderValue = 3;

        return fields.stream()
                .filter(field -> field.xCoordinate() > minAdderValue)
                .filter(field -> containsFields(fields, field.xCoordinate() - 1, field.yCoordinate() - 1, field.owner()))
                .filter(field -> containsFields(fields, field.xCoordinate() - 2, field.yCoordinate() - 2, field.owner()))
                .anyMatch(field -> containsFields(fields, field.xCoordinate() - maxAdderValue, field.yCoordinate() - maxAdderValue, field.owner()));
    }

    /**
     * Winning sequence diagonal upward
     * @param fields the fields witch should be proofed
     * @return if the winning sequence is diagonal upward
     */
    private static boolean winningSequenceDiagonalUpward(List<Field> fields) {
        final int maxAdderValue = 3;
        final int fieldSize = 8;

        return fields.stream()
                .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                .filter(field -> containsFields(fields, field.xCoordinate() + 1, field.yCoordinate() - 1, field.owner()))
                .filter(field -> containsFields(fields, field.xCoordinate() + 2, field.yCoordinate() - 2, field.owner()))
                .anyMatch(field -> containsFields(fields, field.xCoordinate() + maxAdderValue, field.yCoordinate() - maxAdderValue, field.owner()));
    }

    /**
     * containsFields.
     * @param fieldList the list of fields witch should contain the field
     * @param xCoordinate the xCoordinate of the searched field
     * @param yCoordinate the yCoordinate of the searched field
     * @param owner the owner of the searched field
     * @return if the field is contained in the list
     */
    private static boolean containsFields(List<Field> fieldList, int xCoordinate, int yCoordinate, PlayerID owner) {
        return fieldList.stream().anyMatch(field -> field.xCoordinate() == xCoordinate && field.yCoordinate() == yCoordinate && field.owner() == owner);
    }
}
