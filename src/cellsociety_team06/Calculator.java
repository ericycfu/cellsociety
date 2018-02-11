package cellsociety_team06;

import java.util.List;
/**
 * 
 * @author Frank Yin
 * Purpose: This class stores some necessary parameters for grid-updating rules, and calculates 
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
