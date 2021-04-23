package edu.hm.se2.connect_four.csgles.demo;

import edu.hm.se2.connect_four.csgles.datastore.*;
import edu.hm.se2.connect_four.csgles.datastore.mutable.*;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourDemo {
    public static void main(String... args) {
        FullBoard board = FullConnectFourBoard.make();
        FullPlayer playerOne = FullConnectFourPlayer.make(PlayerID.PLAYER_1);
        FullPlayer playerTwo = FullConnectFourPlayer.make(PlayerID.PLAYER_2);
        FullGame game = FullConnectFourGame.make(PlayerID.PLAYER_1, board);
        printGame(game, board);

        game.setPlayerCount(2);
        printGame(game, board);

        game.setIsStarted(true);
        List<Field> highlightPlayer1 = new ArrayList<>(List.of(
            ConnectFourField.make(3, 0, PlayerID.PLAYER_1),
            ConnectFourField.make(3, 1, PlayerID.PLAYER_1),
            ConnectFourField.make(3, 2, PlayerID.PLAYER_1),
            ConnectFourField.make(3, 3, PlayerID.PLAYER_1),
            ConnectFourField.make(3, 4, PlayerID.PLAYER_1),
            ConnectFourField.make(3, 5, PlayerID.PLAYER_1),
            ConnectFourField.make(3, 6, PlayerID.PLAYER_1)
        ));
        board.setHighlight(highlightPlayer1);
        printGame(game, board);

        Field field_30 = ConnectFourField.make(3, 0, PlayerID.PLAYER_1);
        board.placeStone(field_30);
        List<Field> highlightPlayer2 = new ArrayList<>(List.of(
                ConnectFourField.make(3, 0, PlayerID.PLAYER_2),
                ConnectFourField.make(3, 1, PlayerID.PLAYER_2),
                ConnectFourField.make(3, 2, PlayerID.PLAYER_2),
                ConnectFourField.make(3, 3, PlayerID.PLAYER_2),
                ConnectFourField.make(3, 4, PlayerID.PLAYER_2),
                ConnectFourField.make(3, 5, PlayerID.PLAYER_2),
                ConnectFourField.make(3, 6, PlayerID.PLAYER_2)
        ));
        board.setHighlight(highlightPlayer2);
        printGame(game, board);
    }

    private static void printGame(Game game, Board board) {
        if(game.isStarted()) {
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
        for(int y = 6; y >= 0; y--) {
            System.out.print('|');
            for(int x = 7; x >= 0; x--) {
                PlayerID occupyingPlayer = isFieldOccupied(allFields, x, y);
                char symbolToPrint;
                switch(occupyingPlayer) {
                    case PLAYER_1 -> symbolToPrint = 'G';
                    case PLAYER_2 -> symbolToPrint = 'B';
                    default -> symbolToPrint = ' ';
                }
                if(symbolToPrint == ' ') {
                    PlayerID highlightPlayer = isFieldOccupied(board.getHighlight(), x, y);
                    switch(highlightPlayer) {
                        case PLAYER_1 -> symbolToPrint = 'g';
                        case PLAYER_2 -> symbolToPrint = 'b';
                        default -> symbolToPrint = ' ';
                    }
                }
                System.out.print(symbolToPrint);
            }
            System.out.print("|\n");
        }
        System.out.println( "+========+\n" );
    }

    private static PlayerID isFieldOccupied(List<Field> allFields, int x, int y) {
        PlayerID occupyingPlayer = PlayerID.NONE;
        for(Field field : allFields) {
            if(field.xCoordinate() == x && field.yCoordinate() == y) {
                occupyingPlayer = field.owner();
            }
        }
        return occupyingPlayer;
    }

    private static void printPlayerSelect(Game game) {
        if(game.getPLayerCount() == 1) {
            System.out.println("Use case: Player select screen with 1 player.");
            System.out.println(
                    "+========+\n" +
                    "|GGGG    |\n" +
                    "|GGGG    |\n" +
                    "|GGGG    |\n" +
                    "|GGGG    |\n" +
                    "|GGGG    |\n" +
                    "|GGGG    |\n" +
                    "|GGGG    |\n" +
                    "|GGGG    |\n" +
                    "+========+\n");
        } else {
            System.out.println("Use case: Player select screen with 2 players.");
            System.out.println(
                    "+========+\n" +
                    "|GGGGBBBB|\n" +
                    "|GGGGBBBB|\n" +
                    "|GGGGBBBB|\n" +
                    "|GGGGBBBB|\n" +
                    "|GGGGBBBB|\n" +
                    "|GGGGBBBB|\n" +
                    "|GGGGBBBB|\n" +
                    "|GGGGBBBB|\n" +
                    "+========+\n");
        }
    }
}
