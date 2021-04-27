package edu.hm.se2.connect_four.csgles.datastore.mutable;

import edu.hm.se2.connect_four.csgles.datastore.Board;
import edu.hm.se2.connect_four.csgles.datastore.Game;
import edu.hm.se2.connect_four.csgles.datastore.Observable;
import edu.hm.se2.connect_four.csgles.datastore.Observer;
import edu.hm.se2.connect_four.csgles.datastore.Player;

import java.util.Collections;
import java.util.List;

/**
 * An abstract observable with methods to register and notify registered observers.
 *
 * @author Christoph Schwarz (schwarz4@hm.edu)
 * @author Georg Lang (glang@hm.edu)
 * @author Enno Scholz (enno.scholz@hm.edu)
 * @version 04-01-2021
 */

public abstract class AbstractConnectFourObservable implements Observable {

    /**
     * A list of observers used to notify them.
     */
    private final List<Observer> observers;

    /**
     * The constructor of the observable
     */
    public AbstractConnectFourObservable() {
        this.observers = Collections.emptyList();
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Game game) {
        for(Observer observer : observers) {
            observer.updatePlayerSelect(game);
            observer.updateWinner(game);
        }
    }

    @Override
    public void notifyObservers(Board board, Player player) {
        for(Observer observer : observers) {
            observer.updateMatrix(board, player);
        }
    }

    @Override
    public void notifyObservers(Board board) {
        for(Observer observer : observers) {
            observer.updateCursor(board);
        }
    }
}
