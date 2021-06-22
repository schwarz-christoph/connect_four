package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public enum BotMove {
    BOT_COLUMN_0(4),
    BOT_COLUMN_1(9),
    BOT_COLUMN_2(14),
    BOT_COLUMN_3(19),
    BOT_COLUMN_4(24),
    BOT_COLUMN_5(29),
    BOT_COLUMN_6(34),
    BOT_COLUMN_7(39),
    BOT_BOMB_JOKER(40),
    BOT_DELETE_JOKER(41),
    INVALID(42);

    private final int selectValue;

    BotMove(int selectValue) {
        this.selectValue = selectValue;
    }

    public static BotMove getWeightedRandomMove() {
        final double weightedNumber = Math.random() * BotMove.INVALID.selectValue;
        final Optional<BotMove> moveOptional = Arrays.stream(BotMove.values()).filter(move -> move.selectValue > weightedNumber).findFirst();
        return moveOptional.orElse(INVALID);
    }

    public static List<Move> translate(BotMove move, Game game, PlayerID player) {
        final int targetCordX = getCoordinates(move, player);
        final int currentXCord = game.getBoard().getHighlight().get(0).xCoordinate();

        List<Move> result = new ArrayList<>();
        final Move toggleMenu = getMoveYCoordinate(move, game);
        if (toggleMenu != null)
            result.add(toggleMenu);

        if (targetCordX < currentXCord)
            IntStream.range(0, currentXCord + 1 - targetCordX)
                    .forEach(n -> result.add(Move.LEFT));

        else if (targetCordX > currentXCord)
            IntStream.range(0, targetCordX + 1 - currentXCord)
                    .forEach(n -> result.add(Move.RIGHT));

        result.add(Move.CONFIRM);

        return result;
    }

    private static Move getMoveYCoordinate(BotMove move, Game game) {

        final int targetCordY;
        final Move result;
        final int currentYCord = game.getBoard().getHighlight().get(0).yCoordinate();

        if (move == BotMove.BOT_BOMB_JOKER || move == BotMove.BOT_DELETE_JOKER) {
            //Go to Menu (0)
            targetCordY = 0;
            if (targetCordY != currentYCord) {
                result = Move.UP;
            } else {
                result = null;
            }

        } else {
            //Go to Matrix (1-7)
            targetCordY = 1;
            if (targetCordY > currentYCord) {
                result = Move.DOWN;
            } else {
                result = null;
            }
        }

        return result;
    }

    private static int getCoordinates(BotMove move, PlayerID player) {
        final int targetCordX;

        if (move == BotMove.BOT_BOMB_JOKER || move == BotMove.BOT_DELETE_JOKER) {
            int bombJokerPlayer1 = 0;
            int deleteJokerPlayer1 = 1;

            int deleteJokerPlayer2 = 6;
            int bombJokerPlayer2 = 7;

            if (player == PlayerID.PLAYER_1) {
                if (move == BOT_BOMB_JOKER) {
                    targetCordX = bombJokerPlayer1;
                } else {
                    targetCordX = deleteJokerPlayer1;
                }
            } else {
                if (move == BOT_BOMB_JOKER) {
                    targetCordX = bombJokerPlayer2;
                } else {
                    targetCordX = deleteJokerPlayer2;
                }
            }
        } else {
            targetCordX = getMatrixXCoordinate(move);
        }

        return targetCordX;
    }

    private static int getMatrixXCoordinate(BotMove move) {
        return switch (move) {
            case BOT_COLUMN_0 -> 0;
            case BOT_COLUMN_1 -> 1;
            case BOT_COLUMN_2 -> 2;
            case BOT_COLUMN_3 -> 3;
            case BOT_COLUMN_4 -> 4;
            case BOT_COLUMN_5 -> 5;
            case BOT_COLUMN_6 -> 6;
            default -> 7;
        };
    }
}

