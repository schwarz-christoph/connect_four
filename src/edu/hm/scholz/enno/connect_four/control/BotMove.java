package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.Move;

import java.util.*;
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

    /**
     *  The integer value of the move the bot makes
     */
    private final int selectValue;

    BotMove(int selectValue) {
        this.selectValue = selectValue;
    }

    /**
     * Determines a move based on weights
     * @return determined move
     */
    public static BotMove getWeightedRandomMove() {
        final double weightedNumber = Math.random() * BotMove.INVALID.selectValue;
        final Optional<BotMove> moveOptional = Arrays.stream(BotMove.values()).filter(move -> move.selectValue > weightedNumber).findFirst();
        return moveOptional.orElse(INVALID);
    }

    /**
     *
     * @param move Destination of the desired location the bot wants to go to
     * @param game The current game.
     * @param player PlayerID of the current bot
     * @return List of moves necessary to get to the desired location of the bot
     */
    public static List<Move> translate(BotMove move, Game game, PlayerID player) {
        final int targetCordX = getCoordinates(move, player);
        final int currentXCord = game.getBoard().getHighlight().get(0).xCoordinate();

        final List<Move> result = new ArrayList<>();
        final Move toggleMenu = getMoveYCoordinate(move, game);
        if (toggleMenu != null)
            result.add(toggleMenu);

        if (targetCordX < currentXCord)
            IntStream.range(0, currentXCord - targetCordX)
                    .forEach(n -> result.add(Move.LEFT));

        else if (targetCordX > currentXCord)
            IntStream.range(0, targetCordX - currentXCord)
                    .forEach(n -> result.add(Move.RIGHT));

        result.add(Move.CONFIRM);

        if(move == BotMove.BOT_DELETE_JOKER || move == BotMove.BOT_BOMB_JOKER){
            result.addAll(appendDeleteJokerMoves());
        }

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
            final int bombJokerPlayer1 = 0;
            final int deleteJokerPlayer1 = 1;

            final int deleteJokerPlayer2 = 6;
            final int bombJokerPlayer2 = 7;

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

    private static List<Move> appendDeleteJokerMoves(){
        List<Move> result = new ArrayList<>();

        Random rand = new Random();
        Move randomMove = Move.LEFT;
        while (randomMove != Move.CONFIRM) {
            final int randomInt = rand.nextInt(Move.values().length);
            randomMove = Move.values()[randomInt];
            result.add(randomMove);
        }
        return result;
    }
}

