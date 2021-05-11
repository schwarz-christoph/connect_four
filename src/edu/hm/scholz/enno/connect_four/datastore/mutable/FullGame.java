package edu.hm.scholz.enno.connect_four.datastore.mutable;

import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.Game;

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
