package cellsociety_team06;

import java.util.ArrayList;

public abstract class Calculator {
	
	public Calculator(String[] properties, double parameter) {
		// TODO Auto-generated constructor stub
	}
	
	//for simple simulations without properties or parameters
	public Calculator() {
		
	}
	
	public abstract double calculation(ArrayList<Cell> relatedCells, Cell centerCell);
	
}
