package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.Player;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;

import java.util.List;
import java.util.stream.Collectors;

public class ValidatingView implements View {

    private boolean isValidGame = false;

    public ValidatingView(Game game) {
        if (game == null)
            throw new IllegalArgumentException("Arguments cannot be null.");

        game.register(this);
    }

    @Override
    public void update(Board board, Game game, Player player1, Player player2) {
        if (game.getActivePlayer() == PlayerID.NONE) {
            validateGame(board, game);
        }
    }

    @Override
    public void shut() {
        View.super.shut();
    }

    public boolean isValidGame() {
        return isValidGame;
    }

    private void validateGame(Board board, Game game) {
        final PlayerID wantPlayerID;
        final boolean winnerPlayer1 = validateWinner(board, PlayerID.PLAYER_1);
        final boolean winnerPlayer2 = validateWinner(board, PlayerID.PLAYER_2);
        final List<Field> fields = board.getFields();
        final int fieldSize = 8;
        final boolean boardFull = fields.size() == fieldSize * (fieldSize - 1);
        if (winnerPlayer1 ^ winnerPlayer2) // ^ = exclusive or
            wantPlayerID = winnerPlayer1 ? PlayerID.PLAYER_1 : PlayerID.PLAYER_2;
        else
            wantPlayerID = PlayerID.NONE;

        if (game.getWinner() == wantPlayerID) {
            isValidGame = true;
        }

        if (boardFull) {
            isValidGame = game.getActivePlayer() == PlayerID.NONE;
        }
    }

    private boolean validateWinner(Board board, PlayerID wantPlayerID) {
        final List<Field> fields = board.getFields();

        final List<Field> playerFields = fields.stream()
                .filter(field -> field.owner() == wantPlayerID)
                .collect(Collectors.toList());

        return containsWinningSequence(playerFields);
    }

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


    private static boolean winningSequenceHorizontal(List<Field> fields) {
        final int maxAdderValue = 3;
        final int fieldSize = 8;

        return fields.stream()
                .filter(field -> field.xCoordinate() < fieldSize - maxAdderValue)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 1, field.yCoordinate(), field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() + 2, field.yCoordinate(), field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() + maxAdderValue, field.yCoordinate(), field.owner())));
    }

    private static boolean winningSequenceVertical(List<Field> fields) {
        final int maxAdderValue = 3;
        final int fieldSize = 8;

        return fields.stream()
                .filter(field -> field.yCoordinate() < fieldSize - maxAdderValue)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 1, field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + 2, field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate(), field.yCoordinate() + maxAdderValue, field.owner())));
    }

    private static boolean winningSequenceDiagonalDownward(List<Field> fields) {
        final int minAdderValue = 2;
        final int maxAdderValue = 3;

        return fields.stream()
                .filter(field -> field.xCoordinate() > minAdderValue)
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 1, field.yCoordinate() - 1, field.owner())))
                .filter(field -> fields.contains(Factory.makeField(field.xCoordinate() - 2, field.yCoordinate() - 2, field.owner())))
                .anyMatch(field -> fields.contains(Factory.makeField(field.xCoordinate() - maxAdderValue, field.yCoordinate() - maxAdderValue, field.owner())));
    }

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
