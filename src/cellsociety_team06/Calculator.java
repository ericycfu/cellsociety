package cellsociety_team06;

import java.util.ArrayList;

public abstract class Calculator {
	protected String[] myPropertys;
	protected double myParameter;
	
	public Calculator(String[] propertys, double parameter){
		myPropertys = propertys;
		myParameter = parameter;
	}
	
	protected int getState(String property){
		for (int i = 0; i < myPropertys.length; i++)
			if (property.equals(myPropertys[i]))
				return i;
		return -1; // state not found (error)
	}
	
	public abstract double calculation(ArrayList<Cell> relatedCells, Cell centerCell);
	
}
