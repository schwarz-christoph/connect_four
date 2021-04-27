package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.Game;
import edu.hm.se2.connect_four.csgles.datastore.Player;
import edu.hm.se2.connect_four.csgles.datastore.PlayerID;

/**
 * A modifiable connect four game.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public interface FullGame extends Game {
    @SuppressWarnings("PMD.CommentRequired")
    void setActivePlayer(PlayerID activePlayer);

    @SuppressWarnings("PMD.CommentRequired")
    void setWinner(PlayerID winner);

    @SuppressWarnings("PMD.CommentRequired")
    void setIsStarted(boolean isStarted);

    @SuppressWarnings("PMD.CommentRequired")
    void setPlayerCount(int playerCount);
}
