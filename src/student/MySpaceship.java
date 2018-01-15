package student;

import controllers.Spaceship;
import models.Edge;
import models.Node;
import models.NodeStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controllers.RescueStage;
import controllers.ReturnStage;

/** An instance implements the methods needed to complete the mission. */
public class MySpaceship implements Spaceship {

    /** The spaceship is on the location given by parameter state.
     * Move the spaceship to Planet X and then return, while the spaceship is on
     * Planet X. This completes the first phase of the mission.
     * 
     * If the spaceship continues to move after reaching planet X, rather than
     * returning, it will not count. Returning from this procedure while
     * not on Planet X counts as a failure.
     * <p>
     * There is no limit to how many steps you can take, but your score is
     * directly related to how long it takes you to find Planet X.
     * <p>
     * At every step, you know only the current planet's ID, the IDs of
     * neighboring planets, and the strength of the ping from Planet X at
     * each planet.
     * <p>
     * In this rescueStage, parameter stage has useful methods:<br>
     * (1) In order to get information about the current state, use functions
     * getLocation(), neighbors(), getPing(), and foundSpaceship().
     * <p>
     * (2) You know you are on Planet X when foundSpaceship() is true.
     * <p>
     * (3) Use function moveTo(long id) to move to a neighboring planet
     * by its ID. Doing this will change state to reflect your new position.
     */
    @Override
    public void rescue(RescueStage state) {
    	HashSet<Integer> visited=new HashSet<Integer>();
    	dfs(state,visited);
    	

    }


    /** Completes the mission to find Planet X.
     *  Finds planet x based on strength of distress signal  
     *  Current planet must be unvisited
     *  if spaceship is at planet x then return
     *  else call dfs on neighbors*/
    public void dfs(RescueStage s,HashSet<Integer> h) {

    	if (s.foundSpaceship()==true){return;}
    	int loc=s.getLocation();
    	h.add(loc);
    	NodeStatus[] n=s.neighbors();
    	Arrays.sort(n);
    	for (int i=n.length-1;i>=0;i--){
    		if(!h.contains(n[i].getId())) {
    			s.moveTo(n[i].getId());
    			dfs(s,h);
    			if (s.foundSpaceship()==true){return;}
    			s.moveTo(loc);
    		}
    		
    	}
    	
    	
    	
    }

    /** Return to Earth while collecting as many gems as possible.
     * The rescued spaceship has information on the entire galaxy, so you
     * now have access to the entire underlying graph. This can be accessed
     * through ReturnStage. getCurrentNode() and getEarth() will return Node
     * objects of interest, and getNodes() gives you a Set of all nodes
     * in the graph. 
     *
     * You must return from this function while on Earth. Returning from the
     * wrong location will be considered a failed run.
     *
     * You must make it back to Earth before running out of fuel.
     * state.getDistanceLeft() will tell you how far you can travel with your  
     * remaining fuel stores.
     * 
     * You can increase your score by collecting more gems on your way back to 
     * Earth. You should look for ways to optimize your return. The information 
     * from the rescued ship includes information on where gems are located. 
     * getNumGems() will give you the number of gems on a node. You will 
     * automatically collect any remaining gems when you move to a planet during 
     * the rescue stage.  */
    @Override
    public void returnToEarth(ReturnStage state) {

    	earth(state);

    	
   
    }
    /** Return to Earth while collecting as many gems as possible.
     *  Sorts planets in graph based on gem number and calculates and travels 
     *  on the minimum path to planets with the greatest gems in descending order
     *  if there is distance left
     *  if not, calculate minimum path and travel back to earth  */
    public void earth(ReturnStage s) {

    	ArrayList<Node> gems = new ArrayList<Node>();
    	
    	for (Node w:s.getNodes()) {
    		gems.add(w);
    	}
    	//lambda expression to sort planets based on gems
    	gems.sort((Node a, Node b)-> Integer.compare(a.getNumGems(),b.getNumGems()));
    	
    	for (int i=gems.size()-1;i>=0;i--){
    		List<Node> route=new ArrayList<Node>();
    		//path weight from current node to next planet
    		int current_max=MinPath.pathWeight(MinPath.minPath(s.getCurrentNode(),gems.get(i)));
    		int earth_max=MinPath.pathWeight(MinPath.minPath(s.getEarth(),gems.get(i)));
    		if (s.getDistanceLeft()>=(current_max)+(earth_max)){
    		route.addAll(MinPath.minPath(s.getCurrentNode(),gems.get(i)));
    		}
    		else {
    			route.addAll(MinPath.minPath(s.getCurrentNode(),s.getEarth()));
    		}
    	   	for (Node x:route.subList(1, route.size())) {
        		s.moveTo(x);
        	}
    	}
    	
   
    }

    public void earth1(ReturnStage s)  {
    	
    	ArrayList<Node> gems = new ArrayList<Node>();
    	
    	for (Node w:s.getNodes()) {
    		gems.add(w);
    	}
    	gems.sort((Node a, Node b)-> Integer.compare(a.getNumGems(),b.getNumGems()));

    	int i=0;
    	while (i<gems.size()) {
    	for (int k=gems.size()-1;k>=0;k--){
    		List<Node> m=new ArrayList<Node>();
    		int current_max=MinPath.pathWeight(MinPath.minPath(s.getCurrentNode(),gems.get(k)));
    		int earth_max=MinPath.pathWeight(MinPath.minPath(s.getEarth(),gems.get(k)));
    		if (s.getDistanceLeft()>=(current_max)+(earth_max)){
    		m.addAll(MinPath.minPath(s.getCurrentNode(),gems.get(i)));
    		i++;
    		}
    		else {
    			m.addAll(MinPath.minPath(s.getCurrentNode(),s.getEarth()));
    		}
    	   	for (Node x:m.subList(1, m.size())) {
        		s.moveTo(x);
        	}
    	}
    	}
    }
    
}
