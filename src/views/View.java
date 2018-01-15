package views;

import models.Model;
import static models.Model.Stage;

import controllers.Controller;

/** An instance is minimum requirements of a view of the Space Gem game. */
public interface View {
    /** Initializes the view with the given Controller and Model. If the view
     * has already been initialized, this will simply overwrite the previous
     * initialization. */
    public void init(Controller c, Model m);
    
    /** Begins a particular stage. The view will autonomously update and display
     * the model until told to stop updating this particular stage. */
    public void beginStage(Stage s);
    
    /** Signals the end of a particular stage; the view will stop updating,
     * leaving a static displayed state.
     * 
     * Precondition: this stage has begun. */
    public void endStage(Stage s);
    
    /** Signals that the game has ended with the given score. */
    public void endGame(int score);
    
    /** Print s as a regular message. */
    public default void outprint(String s) {
        System.out.print(s);
    }
    
    /** Print s as an error message. */
    public default void errprint(String s) {
        System.err.print(s);
    }

    /** Print s terminated by a newline as a regular message. */
    public default void outprintln(String s) {
        outprint(s + '\n');
    }
    
    /** Print s terminated by a newline as an error message. */
    public default void errprintln(String s) {
        errprint(s + '\n');
    }
}
