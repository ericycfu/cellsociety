package cellsociety_team06;

import java.util.ArrayList;

public abstract class Calculator {
	
	protected String[] properties;
	protected double parameter;
	
	public Calculator(String[] properties){
		this.properties = properties;
	}
	
	public double showParameter(){
		return parameter;
	}
	
	public Calculator(String[] properties, double parameter) {
		this.properties = properties;
		this.parameter = parameter;
	}
	
	protected int getState(String property){
		for (int i = 0; i < properties.length; i++)
			if (property.equals(properties[i]))
				return i;
		return -1; // state not found (error)
	}
	
	public abstract double calculation(ArrayList<Cell> relatedCells, Cell centerCell);

}
