package cellsociety_team06;

import java.util.ArrayList;

public abstract class Calculator {
	
	protected String[] myProperties;
	protected double myParameter;
	
	public Calculator(String[] properties){
		myProperties = properties;
	}
	
	public double showParameter(){
		return myParameter;
	}
	
	public Calculator(String[] properties, double parameter) {
		myProperties = properties;
		myParameter = parameter;
	}
	
	protected int getState(String property){
		for (int i = 0; i < myProperties.length; i++)
			if (property.equals(myProperties[i]))
				return i;
		return -1; // state not found (error)
	}
	
	public abstract double calculation(ArrayList<Cell> relatedCells, Cell centerCell);

}
