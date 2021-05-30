package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.datastore.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class TextDump implements Observer {

    private final String indexLine = "  0 1 2 3 4 5 6 7  \n";
    private final String frameLine = " +================+\n";

    @Override
    public void update(Board board, Game game, Player player1, Player player2) {
        final String gameText;
        if(game.isStarted()) {
            if(game.getActivePlayer() == PlayerID.NONE) {
                // end screen
                gameText = getEndScreenString(game);
            } else {
                // regular game
                gameText = getGameString(board, player1, player2);
            }
        } else {
            // player select screen
            gameText = getPlayerSelectString(game);
        }
        System.out.println(gameText);
    }

    private String getEndScreenString(Game game) {
        final StringBuilder builder = new StringBuilder();
        builder.append(indexLine);
        builder.append(frameLine);

        final String matrixString;
        switch (game.getWinner()) {
            case PLAYER_1 -> matrixString = "|G G G G G G G G |\n";
            case PLAYER_2 -> matrixString = "|B B B B B B B B |\n";
            default -> matrixString = "|G B G B G B G B |\n";
        }

        IntStream.range(0, 8).forEach(index -> builder.append(index).append(matrixString));

        builder.append(frameLine);
        return builder.toString();
    }

    private String getGameString(Board board, Player player1, Player player2) {
        final StringBuilder builder = new StringBuilder();
        builder.append(indexLine);
        builder.append(frameLine);
        builder.append(getMenuString(player1, player2));

        IntStream.range(0, 8).forEach(index -> builder.append(index).append(getGameLineString(board, index)));

        builder.append(frameLine);
        return builder.toString();
    }

    private String getGameLineString(Board board, int lineIndex) {
        final StringBuilder builder = new StringBuilder();
        builder.append('|');
        IntStream.range(0, 8).forEach(index -> builder.append(getCharForField(board, index, lineIndex)).append(' '));
        builder.append('|');
        builder.append('\n');
        return builder.toString();
    }

    private char getCharForField(Board board, int xCoordinate, int yCoordinate) {
        final char fieldChar;
        final Optional<Field> selectedFieldOptional = board.getFields().stream()
                .filter(field -> field.xCoordinate() == xCoordinate)
                .filter(field -> field.yCoordinate() == yCoordinate)
                .findFirst();

        final PlayerID occupyingPlayer;
        final boolean isFieldHighlighted;
        if(selectedFieldOptional.isPresent()) {
            final Field selectedField = selectedFieldOptional.get();
            occupyingPlayer = selectedField.owner();
            isFieldHighlighted = board.getHighlight().stream()
                    .filter(field -> field.xCoordinate() == selectedField.xCoordinate())
                    .anyMatch(field -> field.xCoordinate() == selectedField.xCoordinate());
        } else {
            occupyingPlayer = PlayerID.NONE;
            isFieldHighlighted = false;
        }

        switch (occupyingPlayer) {
            case PLAYER_1 -> fieldChar = isFieldHighlighted ? 'g' : 'G';
            case PLAYER_2 -> fieldChar = isFieldHighlighted ? 'b' : 'B';
            default -> fieldChar = ' ';
        }
        return fieldChar;
    }

    private String getMenuString(Player player1, Player player2) {
        return "0|" +
                (player1.isBombJokerUsed() ? "O-" : "O ") +
                (player1.isDeleteJokerUsed() ? "C-" : "C ") +
                "  R Y   " +
                (player2.isDeleteJokerUsed() ? "C-" : "C ") +
                (player2.isBombJokerUsed() ? "O-" : "O |") +
                '\n';
    }

    private String getPlayerSelectString(Game game) {
        final StringBuilder builder = new StringBuilder();
        builder.append(indexLine);
        builder.append(frameLine);

        final String matrixString;
        if(game.getPLayerCount() == 1) {
            matrixString = "|G G G G         |\n";
        } else {
            matrixString = "|G G G G B B B B |\n";
        }

        IntStream.range(0, 8).forEach(index -> builder.append(index).append(matrixString));

        builder.append(frameLine);
        return builder.toString();
    }
}
