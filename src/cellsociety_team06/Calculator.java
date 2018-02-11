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
 * @author Frank Yin
 * 
 * 
 *
 */
public abstract class Calculator {
	
	protected String[] myProperties;
	protected double myParameter;
	
	public Calculator(String[] properties){
		myProperties = properties;
	}
	
	public Calculator(String[] properties, double parameter) {
		myProperties = properties;
		myParameter = parameter;
	}
	
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
