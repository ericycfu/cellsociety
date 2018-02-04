package cellsociety_team06;

import java.util.ArrayList;

public class Calculator_Fire extends Calculator{
	
	public Calculator_Fire(String[] propertys, double parameter) {
		super(propertys);
		myParameter = parameter; // probCatch
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double calculation(ArrayList<Cell> relatedCells, Cell centerCell) {
		for (Cell c: relatedCells)
				if (c.showCurrentProperty().equals("Burning"))
					return 1; 
		return 0;
	}
	
}