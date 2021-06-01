package edu.hm.scholz.enno.connect_four;

import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TestUtility {
    public static List<Field> getOccupiedFieldList(String boardState) {
        final List<Field> allOccupiedFields = new ArrayList<>();
        IntStream.range(0, boardState.length())
                .filter(index -> boardState.charAt(index) != '.')
                .forEach(index -> allOccupiedFields.add(generateField(index, boardState.charAt(index))));
        return allOccupiedFields;
    }

    public static void createBoardState(FullBoard board, String boardState) {
        getOccupiedFieldList(boardState).forEach(board::placeStone);
    }

    private static Field generateField(int index, char owningPlayer) {
        final int fieldSize = 8;
        final PlayerID player = owningPlayer == 'G' ? PlayerID.PLAYER_1 : PlayerID.PLAYER_2;
        final int fieldXCoordinate = index % fieldSize;
        final int fieldYCoordinate = (index / fieldSize) + 1;
        return Factory.makeField(fieldXCoordinate, fieldYCoordinate, player);
    }
}
