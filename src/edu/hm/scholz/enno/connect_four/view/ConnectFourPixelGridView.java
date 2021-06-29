package edu.hm.scholz.enno.connect_four.view;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.Player;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerActiveJoker;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public record ConnectFourPixelGridView(UI userInterface, Game game) implements View {

    /**
     * Constant for highlighted.
     */
    static final boolean HIGHLIGHTED = true;

    /**
     * Constant for not highlighted.
     */
    static final boolean NOT_HIGHLIGHTED = false;

    /**
     * Constant for not greyed out.
     */
    static final boolean NOT_GREYED_OUT = false;

    /**
     * xCoordinate of the left Border.
     */
    static final int LEFT_BORDER = 0;

    /**
     * xCoordinate of the right Border.
     */
    static final int RIGHT_BORDER = 8;

    public ConnectFourPixelGridView {
        if (userInterface == null || game == null)
            throw new IllegalArgumentException("Arguments cannot be null.");

        game.register(this);
    }

    @Override
    public void update(Board board, Game game, Player player1, Player player2) {
        if (game.isStarted()) {
            if (game.getActivePlayer() == PlayerID.NONE) {
                // end screen
                updateEndScreen(game);
            } else {
                // regular game
                updateRegularGame(board, game, player1, player2);
            }
        } else {
            // player select screen
            updatePlayerSelect(game);
        }
        userInterface().render();
    }

    @Override
    public void shut() {
        userInterface().shut();
    }

    private void updateEndScreen(Game game) {
        IntStream.range(LEFT_BORDER, RIGHT_BORDER).forEach(xIndex -> IntStream.range(LEFT_BORDER, RIGHT_BORDER)
                .forEach(yIndex -> userInterface().set(xIndex, yIndex, getEndScreenRGBCode(xIndex, game.getWinner()))));
    }

    private int getEndScreenRGBCode(int xCoordinate, PlayerID winner) {
        final int rgbCode;
        if (winner == PlayerID.PLAYER_1) {
            rgbCode = Colors.PLAYER_1.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
        } else if (winner == PlayerID.PLAYER_2) {
            rgbCode = Colors.PLAYER_2.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
        } else {
            rgbCode = xCoordinate % 2 == 0 ?
                    Colors.PLAYER_1.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT) :
                    Colors.PLAYER_2.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
        }
        return rgbCode;
    }

    private void updateRegularGame(Board board, Game game, Player player1, Player player2) {
        updateMenu(board, player1, player2);
        IntStream.range(LEFT_BORDER, RIGHT_BORDER).forEach(xIndex -> IntStream.range(LEFT_BORDER + 1, RIGHT_BORDER)
                .forEach(yIndex -> updateField(board, game, xIndex, yIndex)));
    }

    private void updateField(Board board, Game game, int xCoordinate, int yCoordinate) {
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

        userInterface().set(xCoordinate, yCoordinate, getRegularGameRGBCode(occupyingPlayer, isFieldHighlighted, game));
    }

    private int getRegularGameRGBCode(PlayerID player, boolean isHighlighted, Game game) {
        final int playerHighlightColor = game.getActivePlayer() == PlayerID.PLAYER_1 ?
                Colors.PLAYER_1.getRGBCode(HIGHLIGHTED, NOT_GREYED_OUT) :
                Colors.PLAYER_2.getRGBCode(HIGHLIGHTED, NOT_GREYED_OUT);
        final int highlightColor = game.getActiveJoker() == PlayerActiveJoker.NONE ?
                playerHighlightColor :
                Colors.HIGHLIGHT.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
        return getColorForPlayer(player, isHighlighted, highlightColor);
    }

    private int getColorForPlayer(PlayerID player, boolean isHighlighted, int highlightColor) {
        final int color;
        if (player == PlayerID.PLAYER_1)
            color = Colors.PLAYER_1.getRGBCode(isHighlighted, NOT_GREYED_OUT);
        else if (player == PlayerID.PLAYER_2)
            color = Colors.PLAYER_2.getRGBCode(isHighlighted, NOT_GREYED_OUT);
        else
            color = isHighlighted ? highlightColor : Colors.EMPTY.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);

        return color;
    }

    private void updateMenu(Board board, Player player1, Player player2) {
        final Optional<Field> menuHighlightOptional = board.getHighlight().stream()
                .filter(field -> field.yCoordinate() == 0)
                .findFirst();
        final int menuHighlight = menuHighlightOptional.map(Field::xCoordinate).orElse(-1);

        final Iterator<Integer> xIterator = IntStream.range(0, RIGHT_BORDER).iterator();
        final List<Integer> rgbValues = List.of(
                Colors.BOMB_JOKER.getRGBCode(getIsHighlighted(xIterator.next(), menuHighlight), player1.isBombJokerUsed()),
                Colors.DELETE_JOKER.getRGBCode(getIsHighlighted(xIterator.next(), menuHighlight), player1.isDeleteJokerUsed()),
                getIsHighlighted(xIterator.next(), menuHighlight) ?
                        Colors.HIGHLIGHT.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT) :
                        Colors.EMPTY.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT),
                Colors.END_GAME.getRGBCode(getIsHighlighted(xIterator.next(), menuHighlight), NOT_GREYED_OUT),
                Colors.RESTART_GAME.getRGBCode(getIsHighlighted(xIterator.next(), menuHighlight), NOT_GREYED_OUT),
                getIsHighlighted(xIterator.next(), menuHighlight) ?
                        Colors.HIGHLIGHT.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT) :
                        Colors.EMPTY.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT),
                Colors.DELETE_JOKER.getRGBCode(getIsHighlighted(xIterator.next(), menuHighlight), player2.isDeleteJokerUsed()),
                Colors.BOMB_JOKER.getRGBCode(getIsHighlighted(xIterator.next(), menuHighlight), player2.isBombJokerUsed())
        );

        IntStream.range(0, RIGHT_BORDER).forEach(xCoordinate -> userInterface().set(xCoordinate, 0, rgbValues.get(xCoordinate)));
    }

    private boolean getIsHighlighted(int fieldXCoordinate, int highlightXCoordinate) {
        return fieldXCoordinate == highlightXCoordinate;
    }

    private void updatePlayerSelect(Game game) {
        IntStream.range(LEFT_BORDER, RIGHT_BORDER).forEach(xIndex -> IntStream.range(LEFT_BORDER, RIGHT_BORDER)
                .forEach(yIndex -> userInterface().set(xIndex, yIndex, getPlayerSelectRGBCode(xIndex, game.getPLayerCount()))));
    }

    private int getPlayerSelectRGBCode(int xCoordinate, int playerCount) {
        final int rgbCode;
        final int partingLine = 4;

        if (xCoordinate < partingLine) {
            rgbCode = Colors.PLAYER_1.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
        } else {
            if (playerCount == 1) {
                rgbCode = Colors.EMPTY.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
            } else {
                rgbCode = Colors.PLAYER_2.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
            }
        }

        return rgbCode;
    }
}
