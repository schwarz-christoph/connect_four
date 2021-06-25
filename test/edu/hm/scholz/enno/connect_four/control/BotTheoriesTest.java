package edu.hm.scholz.enno.connect_four.control;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullBoard;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullGame;
import edu.hm.scholz.enno.connect_four.datastore.mutable.FullPlayer;
import edu.hm.scholz.enno.connect_four.logic.GameManager;
import edu.hm.scholz.enno.connect_four.logic.LogicFactory;
import edu.hm.scholz.enno.connect_four.view.ValidatingView;
import edu.hm.scholz.enno.connect_four.view.View;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class BotTheoriesTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(1_000);

    private static int testRun;

    private static final int testRunCount = 1_000;
    private static int player1Winn;
    private static int player2Winn;
    private static int playerNoneWinn;

    @DataPoints("rep") public static List<Integer> repeat = IntStream.range(0, testRunCount).boxed().collect(Collectors.toList());

    @Theory
    public void theoriesTest(@FromDataPoints("rep")int repeats){
        // Datastore
        final FullPlayer player1 = Factory.makePlayer(PlayerID.PLAYER_1);
        final FullPlayer player2 = Factory.makePlayer(PlayerID.PLAYER_2);
        final FullBoard board = Factory.makeBoard();
        final FullGame game = Factory.makeGame(PlayerID.PLAYER_1, board);

        // Logik
        final GameManager rules = LogicFactory.makeGameManager(board, game, player1, player2);

        // Liste von Views
        final ValidatingView valiView = new ValidatingView(game);
        final List<View> views = List.of(valiView);

        // Liste von Controls, eine fuer jeden Spieler
        final List<Control> controls = List.of(
                new Bot(game, rules, PlayerID.PLAYER_1),
                new Bot(game, rules, PlayerID.PLAYER_2));

        //Bot vs Bot setup required
        game.setActivePlayer(PlayerID.PLAYER_1);
        game.setPlayerCount(2);
        game.setIsStarted(true);
        board.setHighlight(List.of(Factory.makeField(0, 1, PlayerID.NONE)));

        // Initiales update, Views zeigen Startzustand
        views.forEach(view -> view.update(board, game, player1, player2));

        // Fortfahren bis das Spiel beendet ist
        while(game.getActivePlayer() != PlayerID.NONE)
            controls.stream()
                    .filter(Control::running) // noch beteiligte Spieler herausfiltern
                    .forEach(Control::step); // jeder Spieler zieht

        // Views und Controls abbauen
        controls.forEach(Control::close);
        views.forEach(View::shut);
        System.out.println(testRun++ + " | Winner: " + game.getWinner());

        //Only for counting specific wins -------------------------------------------------------------------------------------------------------------
        if(game.getWinner() == PlayerID.PLAYER_1){
            player1Winn++;
        }else if(game.getWinner() == PlayerID.PLAYER_2){
            player2Winn++;
        }else{
            playerNoneWinn++;
        }

        if(testRun == testRunCount){
            System.out.println("| Player 1 Win: " + player1Winn + " | Player 2 Win: " + player2Winn + " | Player None Win: " + playerNoneWinn + " |");
        }
        //---------------------------------------------------------------------------------------------------------------------------------------------
        assertTrue(valiView.isValidGame());
    }
}
