package edu.hm.scholz.enno.connect_four;

import edu.hm.scholz.enno.connect_four.datastore.Player;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.Move;
import edu.hm.scholz.enno.connect_four.view.TextDump;

import java.util.Set;
import java.util.stream.Stream;
// TODO Ergaenzen Sie hier Imports Ihrer Packages

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
    // TODO: Bitte passen Sie den Code an Ihre Typen und Methoden an.
    // Aendern Sie *nicht* die Struktur des Programms.
    public static void main(String... args) {
        // Das Spiel
        final FullGame game = Factory.makeGame(PlayerID.PLAYER_1);
        // Die Regeln
        final GameManager rules = Factory.makeGameManager(game);

        //TODO Implement the rest of Methode
        // View, die eine Textdarstellung produziert
        game.register(new TextDump());
        // Buchstaben fuer Moves
        final String script = "1DDDDDDRDDDDDDRDDDDDDRDOO";

        if (args.length == 0) {
            for (char needle : script.toCharArray())
                for (PlayerID playerID : PlayerID.values())
                    for (Move move : rules.getMoves(playerID))
                        if (Move.getAllMovesString().indexOf(needle) >= 0) {
                            System.out.println(move);
                            rules.executeMove(move);
                            break;
                        }
        } else
            script.chars()
                    .sequential() // force sequential
                    .forEach(needle -> Stream.of(PlayerID.values())
                            .map(rules::getMoves)
                            .flatMap(Set::stream)
                            .filter(move -> move.type().name().indexOf(needle) >= 0)
                            .findAny()
                            .stream()
                            .peek(System.out::println)
                            .forEach(rules::fire));
    }
}
