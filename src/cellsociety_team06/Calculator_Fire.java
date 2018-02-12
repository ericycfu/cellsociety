package cellsociety_team06;

import java.util.List;
/**
 * calculates whether a cell has a adjacent burning cell in fire simulation so whether or not it has a chance of burning
 * @author Frank Yin
 * @author Eric Fu
 *
 */
public class Calculator_Fire extends Calculator{
	
	/**
	 * Creates an instance of a calculator for the fire simulation
	 * @param properties A string array representing the different states a cell can be in
	 * @param parameter probCatch, the probability of a cell catching on fire.
	 */
	public Calculator_Fire(String[] properties, double parameter) {
		super(properties, parameter);
	}
	
	/**
	 * returns 1 or 0 depending on if a cell should be a candidate for change or not respectively.
	 * @param relatedCells a list containing cells that are adjacent to the centerCell
	 * @param centerCell the cell that is the subject of the calculation
	 */
	@Override
	public double calculation(List<Cell> relatedCells, Cell centerCell) {
		for (Cell c: relatedCells)
				if (c.showCurrentProperty().equals("Burning"))
					return 1; 
		return 0;
	}
	
}

