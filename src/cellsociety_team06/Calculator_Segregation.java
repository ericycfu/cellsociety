package cellsociety_team06;

import java.util.ArrayList;

public class Calculator_Segregation extends Calculator{
	
	
	public Calculator_Segregation(String[] propertys, double parameter) {
		super(propertys, parameter);
		// TODO Auto-generated constructor stub
	}

	public double calculation(ArrayList<Cell> relatedCells, Cell centerCell){
		double alike = 0;
		int neighbor = 0;
		String alikeProperty = centerCell.showCurrentProperty();
		
		for (Cell c : relatedCells){
			if (c.showCurrentProperty().equals(alikeProperty))
				alike++;
			if (c.showCurrentProperty().equals("X")||c.showCurrentProperty().equals("O"))
				neighbor++;
		}
		
		alike = alike/neighbor;
		if (alike>=myParameter)
			return 0;
		else
			return 1;
	}
	
}
