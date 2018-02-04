package cellsociety_team06;

import java.util.ArrayList;

public abstract class Calculator {
	
	private String[] properties;
	private double parameter;
	
	
	public Calculator(String[] properties, double parameter) {
		this.properties = properties;
		this.parameter = parameter;
	}
	
	//for simple simulations without properties or parameters
	public Calculator() {
		
	}
	
	public abstract double calculation(ArrayList<Cell> relatedCells, Cell centerCell);

	public abstract int getState(String string);
	
}
