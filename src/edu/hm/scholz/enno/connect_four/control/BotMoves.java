package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.logic.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public enum BotMoves {
    BOT_COLUMN_0, BOT_COLUMN_1, BOT_COLUMN_2, BOT_COLUMN_3, BOT_COLUMN_4, BOT_COLUMN_5, BOT_COLUMN_6, BOT_COLUMN_7, BOT_BOMB_JOKER, BOT_DELETE_JOKER;

    public static List<Move> translate(BotMoves move, Game game, PlayerID player) {

        final int targetCordX = getCoordinates(move, player);

        int currentXCord = game.getBoard().getHighlight().get(0).xCoordinate();

        List<Move> result = new ArrayList<>();
        result.add(getMoveYCoordinate(move, game));


        if (targetCordX < currentXCord)
            IntStream.range(0, currentXCord + 1 - targetCordX)
                    .forEach(n -> result.add(Move.LEFT));

        else if (targetCordX > currentXCord)
            IntStream.range(0, targetCordX + 1 - currentXCord)
                    .forEach(n -> result.add(Move.RIGHT));

        return result;
    }

    private static Move getMoveYCoordinate(BotMoves move, Game game){

        final int targetCordY;
        final Move result;
        final int currentYCord = game.getBoard().getHighlight().get(0).yCoordinate();

        if (move == BotMoves.BOT_BOMB_JOKER || move == BotMoves.BOT_DELETE_JOKER) {
            //Go to Menu (0)
            targetCordY = 0;
            if(targetCordY != currentYCord){
                result = Move.UP;
            }else{
                result = null;
            }

        } else {
            //Go to Matrix (1-7)
            targetCordY = 1;
            if(targetCordY != currentYCord){
                result = Move.DOWN;
            }else{
                result = null;
            }
        }

        return result;
    }

    private static int getCoordinates(BotMoves move, PlayerID player) {

        int targetCordX;

        if (move == BotMoves.BOT_BOMB_JOKER || move == BotMoves.BOT_DELETE_JOKER) {
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

    private static int getMatrixXCoordinate(BotMoves move){
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

