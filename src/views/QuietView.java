package views;

import controllers.Controller;
import models.Model;
import models.Model.Stage;

/** An instance is a view of the Space Gems Game that immediately starts one
 * game and outputs nothing. */
public class QuietView implements View {
    /* True iff the game is still running. */
    private boolean running;

    @Override
    public void init(Controller c, Model m) {
        c.start();
        running= true;
        while (running) {
            c.update();
        }
    }

    @Override public void beginStage(Stage s) {}
    @Override public void endStage(Stage s) {}

    @Override
    public void endGame(int score) {
        running= false;
    }
    
    @Override public void outprint(String s) {}
    @Override public void errprint(String s) {}
}
