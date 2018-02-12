package cellsociety_team06;

import java.util.List;

/**
 * This class stores some necessary parameters for different 
 * grid-updating rules, and calculates the probability of a 
 * cell's behavior based on itself and other adjacent cells' 
 * states. 
 * This class depends on the Cell class, without which the 
 * important abstract method "calculation" would not function
 * properly. 
 * This can be viewed as a helper class between Cell class and 
 * Grid class. 
 * @author Frank Yin
 */
public abstract class Calculator {
	
	/**
	 * An array of String that stores the all possible states of one cell in the simulation.
	 * It is mainly used here to help the process of setting cell states easier. 
	 */
	protected String[] myProperties;
	
	/**
	 * A double that stores the necessary parameter that determines the updating of each cell 
	 * in the society. 
	 */
	protected double myParameter;
	
	/**
	 * This is the Calculator constructor, which stores the properties 
	 * that each cell could have in this simulation.
	 * @param properties: the set of all properties in this simulation 
	 */
	public Calculator(String[] properties){
		myProperties = properties;
	}
	
	/**
	 * The second constructor comes into play when some parameter is 
	 * required for updating the grid
	 * @param properties: the set of all properties in this simulation 
	 * @param parameter: the parameter used for updating the grid 
	 */
	public Calculator(String[] properties, double parameter) {
		myProperties = properties;
		myParameter = parameter;
	}
	
	/**
	 * This getter function returns the parameter value to the grid. 
	 * @return: returns myParameter as a double
	 */
	public double showParameter(){
		return myParameter;
	}
	
	public void resetParameter(double parameter){
		myParameter = parameter;
	}
	/**
	 * This is a helper method that makes the updating cell state easier 
	 * by returning the state value when given its String form. 
	 * @param property: current state in its String form 
	 * @return: returns the corresponding current state index 
	 */
	protected int getState(String property){
		for (int i = 0; i < myProperties.length; i++)
			if (property.equals(myProperties[i]))
				return i;
		return -1; // state not found (error)
	}
	
	/**
	 * This abstract method returns the probability of the centerCell's 
	 * moving behavior based on its adjacent cells and rules. 
	 * @param relatedCells: the List of cells that are related to the 
	 * centerCell's moving decision
	 * @param centerCell: the centerCell that this method checks for 
	 * its future motion
	 * @return: a double from zero to one, with higher value representing 
	 * higher probability of the occurrance of motion 
	 */
	public abstract double calculation(List<Cell> relatedCells, Cell centerCell);

}
