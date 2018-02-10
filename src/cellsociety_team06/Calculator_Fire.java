package cellsociety_team06;

import java.util.List;

public class Calculator_Fire extends Calculator{
	
	public Calculator_Fire(String[] properties, double parameter) {
		super(properties, parameter);//parameter is probCatch
	}
	
	@Override
	public double calculation(List<Cell> relatedCells, Cell centerCell) {
		for (Cell c: relatedCells)
				if (c.showCurrentProperty().equals("Burning"))
					return 1; 
		return 0;
	}
	
}

