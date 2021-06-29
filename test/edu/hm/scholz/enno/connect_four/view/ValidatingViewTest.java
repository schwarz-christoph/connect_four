package edu.hm.scholz.enno.connect_four.view;

import edu.hm.scholz.enno.connect_four.datastore.Game;
import edu.hm.scholz.enno.connect_four.datastore.PlayerID;
import edu.hm.scholz.enno.connect_four.datastore.mutable.Factory;
import org.junit.Test;

public class ValidatingViewTest {

    @Test(expected = IllegalArgumentException.class)
    public void PreconditionValidatingViewTest(){
        Game game = Factory.makeGame(PlayerID.PLAYER_1);
        ValidatingView view = new ValidatingView(null);
    }

}