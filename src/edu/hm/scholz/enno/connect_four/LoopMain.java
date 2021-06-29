package edu.hm.scholz.enno.connect_four;

import java.io.IOException;
import java.util.List;

import edu.hm.cs.rs.se2.ui.UI;
import edu.hm.scholz.enno.connect_four.control.Bot;
import edu.hm.scholz.enno.connect_four.control.Control;
import edu.hm.scholz.enno.connect_four.control.JoystickController;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.LogicFactory;
import edu.hm.scholz.enno.connect_four.view.ConnectFourPixelGridView;
import edu.hm.scholz.enno.connect_four.view.View;

/**
 * Einfaches Hauptprogramm einer MVC-Anwendung.
 * Steuert den Ablauf.
 *
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2021-03-07
 */
public class LoopMain {

    /**
     * Get the controllers for the main game. Executes the player select screen to
     * determine which controllers are to be used.
     *
     * @param game       A game object, needed for controller creation.
     * @param rules      A rules object, needed for controller creation.
     * @param ui         An UI object, needed for controller creation.
     * @param isBotVsBot Whether there are only bot controllers or not.
     * @return A list of controllers.
     */
    private static List<Control> getControllers(FullGame game, GameManager rules, UI ui, boolean isBotVsBot) {
        final List<Control> controlList;

        if (isBotVsBot)
            controlList = List.of(
                    new Bot(game, rules, PlayerID.PLAYER_1),
                    new Bot(game, rules, PlayerID.PLAYER_2));
        else {
            final Control player1Control = new JoystickController(game, rules, ui, PlayerID.PLAYER_1);
            //execute player select screen
            player1Control.step();

            final Control player2Control;
            if (game.getPLayerCount() == 1)
                player2Control = new Bot(game, rules, PlayerID.PLAYER_2);
            else
                player2Control = new JoystickController(game, rules, ui, PlayerID.PLAYER_2);

            controlList = List.of(player1Control, player2Control);
        }

        return controlList;
    }

    /**
     * Entry point.
     *
     * @param args Kommandozeilenargumente: keine.
     */
    public static void main(String... args) throws IOException {
        // I/O fuer Views und Controls
        final UI ui = UI.make("awt", 8);

        // Datastore
        final FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        final FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);
        final FullBoard board = Factory.makeBoard();
        final FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);

        // Logik
        final GameManager rules = LogicFactory.makeGameManager(board, game, player1, player2);

        // Liste von Views
        final List<View> views = List.of(new ConnectFourPixelGridView(ui, game));

        // Initiales update, Views zeigen Startzustand
        views.forEach(view -> view.update(board, game, player1, player2));

        // Liste von Controls, eine fuer jeden Spieler
        final boolean isBotVsBot = false;
        final List<Control> controls = getControllers(game, rules, ui, isBotVsBot);

        // Fortfahren bis das Spiel beendet ist
        while (game.getActivePlayer() != PlayerID.NONE)
            controls.stream()
                    .filter(Control::running) // noch beteiligte Spieler herausfiltern
                    .forEach(Control::step); // jeder Spieler zieht

        if(!isBotVsBot)
            controls.stream().findFirst().get().step();

        // Views und Controls abbauen
        controls.forEach(Control::close);
        views.forEach(View::shut);
    }
}
