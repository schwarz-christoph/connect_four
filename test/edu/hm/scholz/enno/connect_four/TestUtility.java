package edu.hm.scholz.enno.connect_four;

import edu.hm.scholz.enno.connect_four.common.Settings;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;

import java.util.stream.IntStream;

public class TestUtility {
    public static void createBoardState(FullBoard board, String boardState) {
        IntStream.range(0, boardState.length())
                .filter(index -> boardState.charAt(index) != '.')
                .forEach(index -> board.placeStone(generateField(index, boardState.charAt(index))));
    }

    private static Field generateField(int index, char owningPlayer) {
        final PlayerID player = owningPlayer == 'G' ? PlayerID.PLAYER_1 : PlayerID.PLAYER_2;
        final int fieldXCoordinate = index % Settings.fieldSize;
        final int fieldYCoordinate = (index / Settings.fieldSize) + 1;
        return Factory.makeField(fieldXCoordinate, fieldYCoordinate, player);
    }
}
