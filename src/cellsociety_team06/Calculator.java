package cellsociety_team06;

import java.util.ArrayList;

public abstract class Calculator {
	protected String[] myPropertys;
	protected double myParameter = 0;
	
	public Calculator(String[] propertys){
		myPropertys = propertys;
	}
	
	public Calculator(String[] propertys, double parameter){
		myParameter = parameter;
	}
	
	public double showParameter(){
		return myParameter;
	}
	
	protected int getState(String property){
		for (int i = 0; i < myPropertys.length; i++)
			if (property.equals(myPropertys[i]))
				return i;
		return -1; // state not found (error)
	
	public abstract double calculation(ArrayList<Cell> relatedCells, Cell centerCell);
	
}
