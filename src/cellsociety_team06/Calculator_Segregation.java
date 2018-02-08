package cellsociety_team06;

import java.util.ArrayList;

public class Calculator_Segregation extends Calculator{
	
	public Calculator_Segregation(String[] properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}
	
	public Calculator_Segregation(String[] properties, double parameter) {
		super(properties, parameter);
		// TODO Auto-generated constructor stub
	}

	public double calculation(ArrayList<Cell> relatedCells, Cell centerCell){
		if (centerCell.showCurrentProperty().equals("Unoccupied")) return 0;
		double alike = 0;
		double neighbor = 0;
		String alikeProperty = centerCell.showCurrentProperty();
		for (Cell c : relatedCells){
			if (c.showCurrentProperty().equals(alikeProperty))
				alike = alike + 1;
			if (c.showCurrentProperty().equals("X")||c.showCurrentProperty().equals("O"))
				neighbor = alike + 1;
		}
		
		alike = alike/neighbor;
		//System.out.println(alike);
		if (alike>=myParameter)
			return 0;
		else
			return 1;
		
	}
	
}

