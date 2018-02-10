package cellsociety_team06;

import java.util.List;

public class Calculator_SugarScape extends Calculator{
 
 public Calculator_SugarScape(String[] propertys, double inputparameter) {
	 super(propertys);
	 myParameter = inputparameter;
  // TODO Auto-generated constructor stub
 }
 
 @Override
 public double calculation(List<Cell> relatedCells, Cell centerCell) {
	 if (centerCell.showCurrentProperty().equals("Agent")){
		 return 1;
	 }
	 else return 0;
 	}
 
}
