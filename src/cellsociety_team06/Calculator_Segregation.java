package cellsociety_team06;

import java.util.List;

/**
 * This is the calculator sub-class for the Segregation simulation in particular. 
 * This sub-class inherits the methods in the super-class, and carries a similar, 
 * but more specified task to fit the simulation rules. 
 * @author Frank Yin
 *
 */
public class Calculator_Segregation extends Calculator{
	
	public Calculator_Segregation(String[] properties) {
		super(properties);
	}
	
	public Calculator_Segregation(String[] properties, double parameter) {
		super(properties, parameter);
	}
	
	/**
	 * This calculation method returns a double of one or zero. One represents 
	 * that the cell will move to a random location, and zero represents that 
	 * the cell won't. 
	 */
	public double calculation(List<Cell> relatedCells, Cell centerCell){
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
		if (alike>=myParameter)
			return 0;
		else
			return 1;
		
	}
	
}

