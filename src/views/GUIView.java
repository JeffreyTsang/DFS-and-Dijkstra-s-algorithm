package views;

import javax.swing.JOptionPane;

import controllers.Controller;
import gui.GUI;
import models.Model;
import models.Model.Stage;

/** An instance represents a graphical view. */
public class GUIView implements View {
    private GUI gui; // The GUI rendering this game
    private Controller ctrlr; // The controller of this game's model
    
    /** Constructor: a GUIView with a blank GUI. */
    public GUIView() {
        gui= new GUI();
        addTopMenuListeners();
    }

    /** Adds listeners to the top menu to relay user input to the presenter. */
    private void addTopMenuListeners() {
        gui.addStartListener(e -> ctrlr.start()); 
        gui.addResetListener(e -> ctrlr.reset());
        gui.addNewMapListener(e -> ctrlr.newGame(JOptionPane.showInputDialog(
            "Enter either a valid seed, or anything else to get a random seed."
        )));
    }

    @Override
    public void init(Controller c, Model m) {
        System.out.println("Seed: " + m.getSeed());
        ctrlr= c;
        gui.init(c, m);
    }

    @Override
    public void beginStage(Stage s) {
        gui.beginStage(s);
    }

    @Override
    public void endStage(Stage s) {
        gui.endStage(s);
        switch (s) {
        case RESCUE:
            if (ctrlr.isRescueSuccessful())
                System.out.println("Rescue stage ended successfully!");
            break;
            
        case RETURN:
            if (ctrlr.isReturnSuccessful())
                System.out.println("Return stage ended successfully!");
            break;
            
        default:
        }
    }
    
    @Override
    public void endGame(int score) {
        gui.pause();
        System.out.println("Score: " + score); 
    }
}
