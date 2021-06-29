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
     * The integer value of the move the bot makes.
     */
    private final int selectValue;

    BotMove(int selectValue) {
        this.selectValue = selectValue;
    }

    /**
     * Determines a move based on weights.
     *
     * @return determined move.
     */
    public static BotMove getWeightedRandomMove() {
        final double weightedNumber = Math.random() * BotMove.INVALID.selectValue;
        final Optional<BotMove> moveOptional = Arrays.stream(BotMove.values()).filter(move -> move.selectValue > weightedNumber).findFirst();
        return moveOptional.orElse(INVALID);
    }

    /**
     * Translates the desired destination into the necessary moves to reach it.
     *
     * @param destination Destination of the desired location the bot wants to go to.
     * @param game        The current game.
     * @param player      PlayerID of the current bot.
     * @return List of moves necessary to get to the desired location of the bot.
     */
    public static List<Move> translate(BotMove destination, Game game, PlayerID player) {
        final int targetCordX = getCoordinates(destination, player);
        final int currentXCord = game.getBoard().getHighlight().get(0).xCoordinate();

        final List<Move> result = new ArrayList<>();
        final Move toggleMenu = getMoveYCoordinate(destination, game);
        if (toggleMenu != null)
            result.add(toggleMenu);

        if (targetCordX < currentXCord)
            IntStream.range(0, currentXCord - targetCordX)
                    .forEach(n -> result.add(Move.LEFT));

        else if (targetCordX > currentXCord)
            IntStream.range(0, targetCordX - currentXCord)
                    .forEach(n -> result.add(Move.RIGHT));

        result.add(Move.CONFIRM);

        if (destination == BotMove.BOT_DELETE_JOKER || destination == BotMove.BOT_BOMB_JOKER) {
            result.addAll(appendDeleteJokerMoves());
        }

        return result;
    }

    private static Move getMoveYCoordinate(BotMove destination, Game game) {

        final int targetCordY;
        final Move result;
        final int currentYCord = game.getBoard().getHighlight().get(0).yCoordinate();

        if (destination == BotMove.BOT_BOMB_JOKER || destination == BotMove.BOT_DELETE_JOKER) {
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

    private static int getCoordinates(BotMove destination, PlayerID player) {
        final int targetCordX;

        if (destination == BotMove.BOT_BOMB_JOKER || destination == BotMove.BOT_DELETE_JOKER) {
            final int bombJokerPlayer1 = 0;
            final int deleteJokerPlayer1 = 1;

            final int deleteJokerPlayer2 = 6;
            final int bombJokerPlayer2 = 7;

            if (player == PlayerID.PLAYER_1) {
                if (destination == BOT_BOMB_JOKER) {
                    targetCordX = bombJokerPlayer1;
                } else {
                    targetCordX = deleteJokerPlayer1;
                }
            } else {
                if (destination == BOT_BOMB_JOKER) {
                    targetCordX = bombJokerPlayer2;
                } else {
                    targetCordX = deleteJokerPlayer2;
                }
            }
        } else {
            targetCordX = getMatrixXCoordinate(destination);
        }

        return targetCordX;
    }

    private static int getMatrixXCoordinate(BotMove destination) {
        final int rightBorder = 8;
        final List<Integer> xCoordinatesColumns = new ArrayList<>();
        IntStream.range(0, rightBorder)
                .forEach(xCoordinatesColumns::add);
        final Iterator<Integer> xCoordinateIterator = xCoordinatesColumns.listIterator();

        HashMap<BotMove, Integer> columnXCoordinate = new HashMap<>();
        columnXCoordinate.put(BOT_COLUMN_0, xCoordinateIterator.next());
        columnXCoordinate.put(BOT_COLUMN_1, xCoordinateIterator.next());
        columnXCoordinate.put(BOT_COLUMN_2, xCoordinateIterator.next());
        columnXCoordinate.put(BOT_COLUMN_3, xCoordinateIterator.next());
        columnXCoordinate.put(BOT_COLUMN_4, xCoordinateIterator.next());
        columnXCoordinate.put(BOT_COLUMN_5, xCoordinateIterator.next());
        columnXCoordinate.put(BOT_COLUMN_6, xCoordinateIterator.next());
        columnXCoordinate.put(BOT_COLUMN_7, xCoordinateIterator.next());
        columnXCoordinate.put(BOT_BOMB_JOKER, rightBorder - 1);
        columnXCoordinate.put(BOT_DELETE_JOKER, rightBorder - 1);
        columnXCoordinate.put(INVALID, rightBorder - 1);

        return columnXCoordinate.get(destination);
    }

    private static List<Move> appendDeleteJokerMoves() {
        final List<Move> result = new ArrayList<>();

        final Random rand = new Random();
        Move randomMove = Move.LEFT;
        while (randomMove != Move.CONFIRM) {
            final int randomInt = rand.nextInt(Move.values().length);
            randomMove = Move.values()[randomInt];
            result.add(randomMove);
        }
        return result;
    }
}

