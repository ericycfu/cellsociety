package cellsociety_team06;

import java.util.ArrayList;

/**
 * This is the grid sub-class for the Game of Life simulation. It inherits most 
 * important methods from its super-class and reserves deviations for the purpose 
 * of unique rules and specifications. 
 * @author Frank Yin
 *
 */
public class Grid_Life extends Grid{
	
	public Grid_Life(int rownum, int colnum, Calculator myCalculator) {
		super(rownum, colnum, myCalculator);
	}
	
	/**
	 * This method implements the Game of Life rules for cell-updating. 
	 */
	@Override
	public void updateCell(double prob, int centerCellRow, int centerCellCol){
		if (prob==1)
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Live"));
		else if (prob==0){
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Dead"));
		}
	}
	
	/**
	 * For Game of Life simulations, both side adjacency and diagonal adjacency are counted. 
	 */
	@Override
	protected ArrayList<Cell> findAdjacentCells(int row, int col){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		Cell currentCell = myCells[row][col];
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				if (currentCell.checkSideAdjacency(myCells[i][j]) || currentCell.checkDiagonalAdjacency(myCells[i][j]))
					adjacentCells.add(myCells[i][j]);
			}
		}
		return adjacentCells;
	}
	
	/**
	 * This method is not used in Game of Life simulation so is left empty. 
	 */
	@Override
	protected ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property) {
		// TODO Auto-generated method stub
		return new ArrayList<Cell>();
	}
	
	/**
	 * This method is not used in Game of Life simulation so is left empty. 
	 */
	@Override
	protected ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property) {
		// TODO Auto-generated method stub
		return new ArrayList<Cell>();
	}
	
}

