package cellsociety_team06;

import java.util.List;

/**
 * This is the calculator sub-class for the SuagrScape simulation. 
 * It stores important information for the correct cell-updating 
 * process of the grid class. 
 * @author Frank Yin
 *
 */
public class Calculator_SugarScape extends Calculator{
 
 public Calculator_SugarScape(String[] propertys, double inputparameter) {
	 super(propertys);
	 myParameter = inputparameter;
 }
 
 /**
  * This calculation method is fairly straightforward because this type of 
  * simulation relies heavily on the configurations of the grid and thus 
  */
 @Override
 public double calculation(List<Cell> relatedCells, Cell centerCell) {
	 if (centerCell.showCurrentProperty().equals("Agent")){
		 return 1;
	 }
	 else return 0;
 	}
 
}
