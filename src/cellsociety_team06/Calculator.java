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
	protected String[] myProperties;
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
	 * 
	 * @param properties: the set of all properties in this simulation 
	 * @param parameter: the parameter used for updating the grid 
	 */
	public Calculator(String[] properties, double parameter) {
		myProperties = properties;
		myParameter = parameter;
	}
	
	/**
	 * 
	 * @return: 
	 */
	public double showParameter(){
		return myParameter;
	}
	
	protected int getState(String property){
		for (int i = 0; i < myProperties.length; i++)
			if (property.equals(myProperties[i]))
				return i;
		return -1; // state not found (error)
	}
	
	public abstract double calculation(List<Cell> relatedCells, Cell centerCell);

}
