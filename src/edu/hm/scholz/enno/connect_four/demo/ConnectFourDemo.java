package edu.hm.scholz.enno.connect_four.demo;

import edu.hm.scholz.enno.connect_four.datastore.Board;
import edu.hm.scholz.enno.connect_four.datastore.Field;
import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourDemo {
    public static void main(String... args) {
        FullBoard board = Factory.makeBoard();
        FullPlayer playerOne = Factory.makePlayer(PlayerID.PLAYER_1);
        FullPlayer playerTwo = Factory.makePlayer(PlayerID.PLAYER_2);
        FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        printGame(game, board);

        game.setPlayerCount(2);
        printGame(game, board);

        game.setIsStarted(true);
        List<Field> highlightPlayer1 = new ArrayList<>(List.of(
                Factory.makeField(3, 0, PlayerID.PLAYER_1),
                Factory.makeField(3, 1, PlayerID.PLAYER_1),
                Factory.makeField(3, 2, PlayerID.PLAYER_1),
                Factory.makeField(3, 3, PlayerID.PLAYER_1),
                Factory.makeField(3, 4, PlayerID.PLAYER_1),
                Factory.makeField(3, 5, PlayerID.PLAYER_1),
                Factory.makeField(3, 6, PlayerID.PLAYER_1)
        ));
        board.setHighlight(highlightPlayer1);
        printGame(game, board);

        Field field_30 = Factory.makeField(3, 0, PlayerID.PLAYER_1);
        board.placeStone(field_30);
        List<Field> highlightPlayer2 = new ArrayList<>(List.of(
                Factory.makeField(3, 0, PlayerID.PLAYER_2),
                Factory.makeField(3, 1, PlayerID.PLAYER_2),
                Factory.makeField(3, 2, PlayerID.PLAYER_2),
                Factory.makeField(3, 3, PlayerID.PLAYER_2),
                Factory.makeField(3, 4, PlayerID.PLAYER_2),
                Factory.makeField(3, 5, PlayerID.PLAYER_2),
                Factory.makeField(3, 6, PlayerID.PLAYER_2)
        ));
        board.setHighlight(highlightPlayer2);
        printGame(game, board);
    }

    private static void printGame(Game game, Board board) {
        if (game.isStarted()) {
            printMatrix(game, board);
        } else {
            printPlayerSelect(game);
        }
    }

    private static void printMatrix(Game game, Board board) {
        final List<Field> allFields = board.getFields();
        System.out.println("Use case: Started game with menu bar, highlight and placed stones.");
        //Headline stays the same in this use case
        System.out.println(
                "+========+\n" +
                        "|OC RY CO|");
        for (int y = 6; y >= 0; y--) {
            System.out.print('|');
            for (int x = 7; x >= 0; x--) {
                PlayerID occupyingPlayer = isFieldOccupied(allFields, x, y);
                char symbolToPrint;
                switch (occupyingPlayer) {
                    case PLAYER_1 -> symbolToPrint = 'G';
                    case PLAYER_2 -> symbolToPrint = 'B';
                    default -> symbolToPrint = ' ';
                }
                if (symbolToPrint == ' ') {
                    PlayerID highlightPlayer = isFieldOccupied(board.getHighlight(), x, y);
                    switch (highlightPlayer) {
                        case PLAYER_1 -> symbolToPrint = 'g';
                        case PLAYER_2 -> symbolToPrint = 'b';
                        default -> symbolToPrint = ' ';
                    }
                }
                System.out.print(symbolToPrint);
            }
            System.out.print("|\n");
        }
        System.out.println("+========+\n");
    }

    private static PlayerID isFieldOccupied(List<Field> allFields, int x, int y) {
        PlayerID occupyingPlayer = PlayerID.NONE;
        for (Field field : allFields) {
            if (field.xCoordinate() == x && field.yCoordinate() == y) {
                occupyingPlayer = field.owner();
            }
        }
        return occupyingPlayer;
    }

    private static void printPlayerSelect(Game game) {
        if (game.getPLayerCount() == 1) {
            System.out.println("Use case: Player select screen with 1 player.");
            System.out.println(
                    """
                    +========+
                    |GGGG    |
                    |GGGG    |
                    |GGGG    |
                    |GGGG    |
                    |GGGG    |
                    |GGGG    |
                    |GGGG    |
                    |GGGG    |
                    +========+
                    """);
        } else {
            System.out.println("Use case: Player select screen with 2 players.");
            System.out.println(
                    """
                    +========+
                    |GGGGBBBB|
                    |GGGGBBBB|
                    |GGGGBBBB|
                    |GGGGBBBB|
                    |GGGGBBBB|
                    |GGGGBBBB|
                    |GGGGBBBB|
                    |GGGGBBBB|
                    +========+
                    """);
        }
    }
}