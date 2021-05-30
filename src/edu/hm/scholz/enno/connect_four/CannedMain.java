package edu.hm.scholz.enno.connect_four;

import edu.hm.scholz.enno.connect_four.datastore.Player;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.logic.LogicFactory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.Move;
import edu.hm.scholz.enno.connect_four.view.TextDump;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Demoprogramm fuer Rules und Moves.
 *
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2021-05-15
 */
public class CannedMain {
    /**
     * Entry point.
     *
     * @param args Kommandozeilenargumente: Keines fuer Schleifen;
     *             Irgend eines fuer Streams.
     */
    public static void main(String... args) {
        // Das Spiel
        final FullBoard board = Factory.makeBoard();
        final FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);
        // Die Regeln
        final GameManager gameManager = LogicFactory.makeGameManager(board, game);
        // View, die eine Textdarstellung produziert
        game.register(new TextDump());
        // Buchstaben fuer Moves
        final String script = "CLCLLCRC";

        if (args.length == 0) {
            for (char needle : script.toCharArray())
                for (PlayerID playerID : PlayerID.values())
                    if(playerID != PlayerID.NONE) {
                        for (Move move : gameManager.getMoves(playerID))
                            if (move.getMoveName() == needle) {
                                System.out.println(move);
                                gameManager.executeMove(move);
                                break;
                            }
                    }
        } else
            script.chars()
                    .sequential() // force sequential
                    .forEach(needle -> Stream.of(PlayerID.values())
                            .filter(playerID -> playerID != PlayerID.NONE)
                            .map(gameManager::getMoves)
                            .flatMap(List::stream)
                            .filter(move -> move.getMoveName() == needle)
                            .findAny()
                            .stream()
                            .peek(System.out::println)
                            .forEach(gameManager::executeMove));
    }
}
