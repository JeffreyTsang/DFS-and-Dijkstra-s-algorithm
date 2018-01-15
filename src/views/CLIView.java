package views;

import java.util.Scanner;

import controllers.Controller;
import models.Model;
import models.Model.Stage;
import models.Node;

/** An instance is a view of the Space Gems game that operates purely in the
 * console. */
public class CLIView extends Thread implements View {
    private ViewThread thread; // The currently running view thread
    private Controller ctrlr; // The controller attached to this game

    @Override
    public void init(Controller c, Model m) {
        ctrlr= c;
        thread= new ViewThread(c, m);
        thread.run();
    }

    @Override public void beginStage(Stage s) {}
    @Override public void endStage(Stage s) {
        switch (s) {
        case RESCUE:
            if (ctrlr.isRescueSuccessful()) {
                outprintln("\n\n\nRescue stage complete! Distance traveled: "
                         + thread.model.getDistanceTraveled());
                outprintln("\nScore: " + thread.model.getScore() + "\n\n\n");
            }
            break;

        case RETURN:
            if (ctrlr.isReturnSuccessful())
                outprintln("\n\n\nReturn stage completed!");
            break;

        default:
        }
    }

    @Override
    public void endGame(int score) {
        thread.running= false;
        outprintln(
            ".-'`'-._.=[{FINAL SCORE: " + score + "}]=._.-'`'-.\n\n\n");
    }

    private class ViewThread extends Thread {
        /* The controller and model of the current game. */
        private Controller ctrlr;
        private Model model;

        /* True iff the current game is still running. */
        private boolean running;
        
        public ViewThread(Controller c, Model m) {
            ctrlr= c;
            model= m;
        }

        /** Runs a single game, then prompts the user for further action. */
        @Override
        public void run() {
            // get the desired action
            outprintln("============================================");

            @SuppressWarnings("resource") // no reason to close stdin
            Scanner stdin= new Scanner(System.in);

            running= true;
            while (running) {
                outprintln("Enter (without quotes):\n"
                    + "\"s\" to start the game with seed \"" + model.getSeed()
                    + "\"\n"
                    + "\"n\" to generate a new game\n"
                    + "\"q\" to quit");
                String s= stdin.nextLine();
                if (s.isEmpty() || s.length() > 1) {
                    outprintln("error: invalid input \"" + s + '"');
                } else {
                    switch (s.charAt(0)) {
                    case 's':
                        outprintln("Starting...");
                        running= false;
                        break;
                    case 'n':
                        outprintln("Enter a new seed, or anything else "
                                 + "for a random seed");
                        ctrlr.newGame(stdin.nextLine());
                        return;
                    case 'q':
                        outprintln("Quitting...");
                        return;
                    default:
                        outprintln("error: invalid input \"" + s + '"');
                    }
                }
            }

            // run the game
            running= true;
            ctrlr.start();
            Node previous= model.getShipNode();
            while (running) {
                Node current= model.getShipNode();
                if (current != previous) {
                    outprint("[GEMS = " + model.getGems() + "]  ");
                    outprintln(previous + " >>>=----->  " + current);
                    previous= current;
                }
                ctrlr.update();
            }
            outprintln("\n[FINAL GEMS: " + model.getGems() + "]\n\n");
            ctrlr.reset();
        }
    }
}
