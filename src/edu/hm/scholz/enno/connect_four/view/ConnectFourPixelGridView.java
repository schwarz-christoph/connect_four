package edu.hm.scholz.enno.connect_four.view;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.datastore.*;

import java.util.Optional;
import java.util.stream.IntStream;

public record ConnectFourPixelGridView(UI ui) implements View {

    final static boolean HIGHLIGHTED = true;
    final static boolean NOT_HIGHLIGHTED = false;
    final static boolean NOT_GREYED_OUT = false;

    public ConnectFourPixelGridView {
        if (ui == null)
            throw new IllegalArgumentException("UI cannot be null.");
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
        ui().render();
    }

    private void updateEndScreen(Game game) {
        IntStream.range(0, 8).forEach(xIndex -> IntStream.range(0, 8)
                .forEach(yIndex -> setColor(xIndex, yIndex, getEndScreenRGBCode(xIndex, game.getWinner()))));
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
        updateMenu(board, game, player1, player2);
        IntStream.range(1, 8).forEach(xIndex -> IntStream.range(0, 8)
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


        setColor(xCoordinate, yCoordinate, getRegularGameRGBCode(occupyingPlayer, isFieldHighlighted, game));
    }

    private int getRegularGameRGBCode(PlayerID player, boolean isHighlighted, Game game) {
        final int activePlayerHighlightColor = game.getActivePlayer() == PlayerID.PLAYER_1 ?
                Colors.PLAYER_1.getRGBCode(HIGHLIGHTED, NOT_GREYED_OUT) :
                Colors.PLAYER_2.getRGBCode(HIGHLIGHTED, NOT_GREYED_OUT);
        final int highlightColor = game.getActiveJoker() == PlayerActiveJoker.NONE ?
                activePlayerHighlightColor :
                Colors.HIGHLIGHT.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);

        return switch (player) {
            case PLAYER_1 -> Colors.PLAYER_1.getRGBCode(isHighlighted, NOT_GREYED_OUT);
            case PLAYER_2 -> Colors.PLAYER_2.getRGBCode(isHighlighted, NOT_GREYED_OUT);
            default -> isHighlighted ? highlightColor : Colors.EMPTY.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
        };
    }

    private void updateMenu(Board board, Game game, Player player1, Player player2) {
        final Optional<Field> menuHighlightFieldOptional = board.getHighlight().stream()
                .filter(field -> field.yCoordinate() == 0)
                .findFirst();
        final int menuHighlightXCoordinate = menuHighlightFieldOptional.map(Field::xCoordinate).orElse(-1);

        final int color_Player1BombJoker = Colors.BOMB_JOKER.getRGBCode(
                getIsHighlighted(0, menuHighlightXCoordinate), player1.isBombJokerUsed());
        final int color_Player1DeleteJoker = Colors.DELETE_JOKER.getRGBCode(
                getIsHighlighted(1, menuHighlightXCoordinate), player1.isDeleteJokerUsed());
        final int color_Player1EmptyField = getIsHighlighted(2, menuHighlightXCoordinate) ?
                Colors.HIGHLIGHT.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT) :
                Colors.EMPTY.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
        final int color_EndGameField = Colors.END_GAME.getRGBCode(
                getIsHighlighted(3, menuHighlightXCoordinate), NOT_GREYED_OUT);
        final int color_RestartGameField = Colors.RESTART_GAME.getRGBCode(
                getIsHighlighted(4, menuHighlightXCoordinate), NOT_GREYED_OUT);
        final int color_Player2EmptyField = getIsHighlighted(5, menuHighlightXCoordinate) ?
                Colors.HIGHLIGHT.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT) :
                Colors.EMPTY.getRGBCode(NOT_HIGHLIGHTED, NOT_GREYED_OUT);
        final int color_Player2DeleteJoker = Colors.DELETE_JOKER.getRGBCode(
                getIsHighlighted(6, menuHighlightXCoordinate), player1.isDeleteJokerUsed());
        final int color_Player2BombJoker = Colors.BOMB_JOKER.getRGBCode(
                getIsHighlighted(7, menuHighlightXCoordinate), player1.isBombJokerUsed());

        setColor(0, 0, color_Player1BombJoker);
        setColor(1, 0, color_Player1DeleteJoker);
        setColor(2, 0, color_Player1EmptyField);
        setColor(3, 0, color_EndGameField);
        setColor(4, 0, color_RestartGameField);
        setColor(5, 0, color_Player2EmptyField);
        setColor(6, 0, color_Player2DeleteJoker);
        setColor(7, 0, color_Player2BombJoker);
    }

    private boolean getIsHighlighted(int fieldXCoordinate, int highlightXCoordinate) {
        return fieldXCoordinate == highlightXCoordinate;
    }

    private void updatePlayerSelect(Game game) {
        IntStream.range(0, 8).forEach(xIndex -> IntStream.range(0, 8)
                .forEach(yIndex -> setColor(xIndex, yIndex, getPlayerSelectRGBCode(xIndex, game.getPLayerCount()))));
    }

    private int getPlayerSelectRGBCode(int xCoordinate, int playerCount) {
        final int rgbCode;

        if (xCoordinate < 4) {
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

    private void setColor(int x, int y, int rgb) {
        final int maxYIndex = 7;
        ui().set(x, maxYIndex - y, rgb);
    }
}
