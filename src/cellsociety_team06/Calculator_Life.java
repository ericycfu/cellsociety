package cellsociety_team06;

import java.util.List;

/**
 * This is the Calculation sub-class for the GameOfLife simulation.
 * The calculation method is implemented with respect to the rules 
 * and specifications of this simulation type. 
 * @author Frank Yin
 *
 */
public class Calculator_Life extends Calculator{
	
	public Calculator_Life(String[] properties) {
		super(properties);
	}
	
	public Calculator_Life(String[] properties, double parameter) {
		super(properties, parameter);
	}
	
	/**
	 * This method obeys the rules and specifications for the simulation 
	 * and returns one or zero, one representing that the centerCell will 
	 * be live and zero representing the other. 
	 */
	public double calculation(List<Cell> relatedCells, Cell centerCell){
		int reviveCondition = 0;
		for (Cell c : relatedCells)
			if (c.showCurrentProperty().equals("Live"))
				reviveCondition++;

		if (centerCell.showCurrentProperty().equals("Dead")){
			if (reviveCondition==3)
				return 1; 
		}
		else { 
			if (reviveCondition<2)
				return 0; 
			else if (reviveCondition==2 || reviveCondition==3)
				return 1;
			else if (reviveCondition>3)
				return 0;
		}
		
		return 0;
	}
	

}
