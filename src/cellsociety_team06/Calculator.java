package cellsociety_team06;

import java.util.ArrayList;

public abstract class Calculator {
	
	private String[] properties;
	private double parameter;
	
	public Calculator(String[] propertys){
		myPropertys = propertys;
	}
	
	public Calculator(String[] properties, double parameter) {
		this.properties = properties;
		this.parameter = parameter;
	}
	
	protected int getState(String property){
		for (int i = 0; i < myPropertys.length; i++)
			if (property.equals(myPropertys[i]))
				return i;
		return -1; // state not found (error)
	}
	
	public abstract double calculation(ArrayList<Cell> relatedCells, Cell centerCell);

}
