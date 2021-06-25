package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.Player;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;
import edu.hm.scholz.enno.connect_four.datastore.Observer;

import java.util.Optional;
import java.util.stream.IntStream;

public class TextDump implements Observer {

    private final String indexLine = "  0 1 2 3 4 5 6 7  \n";
    private final String frameLine = " +================+\n";

    @Override
    public void update(Board board, Game game, Player player1, Player player2) {
        final String gameText;
        if (game.isStarted()) {
            if (game.getActivePlayer() == PlayerID.NONE) {
                // end screen
                gameText = getEndScreenString(game);
            } else {
                // regular game
                gameText = getGameString(board, game, player1, player2);
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

    private String getGameString(Board board, Game game, Player player1, Player player2) {
        final StringBuilder builder = new StringBuilder();
        builder.append(indexLine);
        builder.append(frameLine);
        builder.append(getMenuString(board, game, player1, player2));

        IntStream.range(1, 8).forEach(index -> builder.append(index).append(getGameLineString(board, game, index)));

        builder.append(frameLine);
        return builder.toString();
    }

    private String getGameLineString(Board board, Game game, int lineIndex) {
        final StringBuilder builder = new StringBuilder();
        builder.append('|');
        IntStream.range(0, 8).forEach(index -> builder.append(getCharForField(board, game, index, lineIndex)).append(' '));
        builder.append('|');
        builder.append('\n');
        return builder.toString();
    }

    private char getCharForField(Board board, Game game, int xCoordinate, int yCoordinate) {
        final char fieldChar;

        final Optional<Field> selectedFieldOptional = board.getFields().stream()
                .filter(field -> field.xCoordinate() == xCoordinate)
                .filter(field -> field.yCoordinate() == yCoordinate)
                .findFirst();
        final PlayerID occupyingPlayer = selectedFieldOptional.isPresent() ?
                selectedFieldOptional.get().owner() :
                PlayerID.NONE;

        final boolean isFieldHighlighted = board.getHighlight().stream()
                .filter(field -> field.xCoordinate() == xCoordinate)
                .anyMatch(field -> field.yCoordinate() == yCoordinate);
        final char highlightChar = game.getActiveJoker() != PlayerActiveJoker.NONE ? 'W' :
                game.getActivePlayer() == PlayerID.PLAYER_1 ? 'g' : 'b';

        switch (occupyingPlayer) {
            case PLAYER_1 -> fieldChar = game.getActiveJoker() != PlayerActiveJoker.NONE && isFieldHighlighted ? 'g' : 'G';
            case PLAYER_2 -> fieldChar = game.getActiveJoker() != PlayerActiveJoker.NONE && isFieldHighlighted ? 'b' : 'B';
            default -> fieldChar = isFieldHighlighted ? highlightChar : ' ';
        }
        return fieldChar;
    }

    private String getMenuString(Board board, Game game, Player player1, Player player2) {
        final char activePlayerHighlightChar = game.getActivePlayer() == PlayerID.PLAYER_1 ? 'g' : 'b';
        final Field highlight = board.getHighlight().get(0);
        final boolean isMenuHighlight = highlight.yCoordinate() == 0;

        final String index = "0";
        final char frame = '|';
        final String player1BombJoker =
                getJokerString(true, player1.isBombJokerUsed(), isMenuHighlight && highlight.xCoordinate() == 0);
        final String player1DeleteJoker =
                getJokerString(false, player1.isDeleteJokerUsed(), isMenuHighlight && highlight.xCoordinate() == 1);
        final String emptySpaceLeft = (isMenuHighlight && highlight.xCoordinate() == 2) ? activePlayerHighlightChar + " " : "  ";
        final String resetButton = (isMenuHighlight && highlight.xCoordinate() == 3) ? "y " : "Y ";
        final String exitButton = (isMenuHighlight && highlight.xCoordinate() == 4) ? "r " : "R ";
        final String emptySpaceRight = (isMenuHighlight && highlight.xCoordinate() == 5) ? activePlayerHighlightChar + " " : "  ";
        final String player2DeleteJoker =
                getJokerString(false, player2.isDeleteJokerUsed(), isMenuHighlight && highlight.xCoordinate() == 6);
        final String player2BombJoker =
                getJokerString(true, player2.isBombJokerUsed(), isMenuHighlight && highlight.xCoordinate() == 7);

        return index + frame +
                player1BombJoker +
                player1DeleteJoker +
                emptySpaceLeft +
                resetButton +
                exitButton +
                emptySpaceRight +
                player2DeleteJoker +
                player2BombJoker +
                frame + '\n';
    }

    private String getJokerString(boolean isBomb, boolean isUsed, boolean isHighlighted) {
        String color = isBomb ? "O" : "C";
        color = isHighlighted ? color.toLowerCase() : color;
        color += isUsed ? '-' : ' ';
        return color;
    }

    private String getPlayerSelectString(Game game) {
        final StringBuilder builder = new StringBuilder();
        builder.append(indexLine);
        builder.append(frameLine);

        final String matrixString;
        if (game.getPLayerCount() == 1) {
            matrixString = "|G G G G         |\n";
        } else {
            matrixString = "|G G G G B B B B |\n";
        }

        IntStream.range(0, 8).forEach(index -> builder.append(index).append(matrixString));

        builder.append(frameLine);
        return builder.toString();
    }
}
