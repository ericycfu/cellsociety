package cellsociety_team06;

import java.util.ArrayList;

public class Calculator_Wator extends Calculator{
	
	public Calculator_Wator(String[] propertys, double parameter) {
		super(propertys);
		myParameter = parameter;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double calculation(ArrayList<Cell> relatedCells, Cell centerCell) {
		if (centerCell.showCurrentProperty().equals("Fish")){
			for (Cell c: relatedCells){
				if (c.showCurrentProperty().equals("Unoccupied"))
					return 1; 
			}
		}
		else if (centerCell.showCurrentProperty().equals("Shark")){
			for (Cell c: relatedCells){
				if (c.showCurrentProperty().equals("Unoccupied") || c.showCurrentProperty().equals("Fish"))
					return 1; 
			}
		}
		return 0;
	}
	
}
