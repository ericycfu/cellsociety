package cellsociety_team06;

import java.util.ArrayList;

/**
 * This is the grid sub-class that implements the Segregation simulation. 
 * @author Frank Yin
 *
 */
public class Grid_Segregation extends Grid{
	
	private ArrayList<Cell> myCellsUnoccupiedNextIteration;
	
	public Grid_Segregation(int rownum, int column, Calculator myCalculator) {
		super(rownum, column, myCalculator);
		myCellsUnoccupiedNextIteration = new ArrayList<Cell>();
	}
	
	/**
	 * This method implements the rules and specifications of the Segregation 
	 * simulation. 
	 */
	public void updateCell(double prob, int centerCellRow, int centerCellCol){
		if (!myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Unoccupied")){
			if (prob==1){
				if (!myCellsUnoccupiedNextIteration.isEmpty()){
					int random = getRandomNumberInRange(0, myCellsUnoccupiedNextIteration.size()-1);
					myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Unoccupied"));
					myCellsUnoccupiedNextIteration.get(random).setFutureState(myCells[centerCellRow][centerCellCol].showCurrentState());
					myCellsUnoccupiedNextIteration.remove(random);
					myCellsUnoccupiedNextIteration.add(myCells[centerCellRow][centerCellCol]);
				}
			}
			else if (prob==0){
				myCells[centerCellRow][centerCellCol].setFutureState(myCells[centerCellRow][centerCellCol].showCurrentState());
			}
		}
	}
	
	/**
	 * This method finds all side and diagonal adjacent cells and returns it as an ArrayList. 
	 */
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
	 * This method renews the ArrayList of cells that contain the Unoccupied property so that 
	 * they can be taken in the next round as needed. 
	 */
	protected void updateUnoccupiedCellArray() {
		myCellsUnoccupiedNextIteration = new ArrayList<Cell>();
		for (int i = 0; i < myRowNum; i++)
			for (int j = 0; j < myColNum; j++){
				if (myCells[i][j].showCurrentProperty().equals("Unoccupied")) 
					myCellsUnoccupiedNextIteration.add(myCells[i][j]);
			}
	}
	
	/**
	 * This method overrides the original one in the super-class to allow the 
	 * updateUnoccupiedCellArray method to be implemented. 
	 */
	@Override
	protected void update(){
		for (int i = 0; i < myRowNum; i++)
			for (int j = 0; j < myColNum; j++){
				myCells[i][j].update();
			}
		updateUnoccupiedCellArray();
	}
	
	/**
	 * This method is not used by this sub-class. 
	 */
	@Override
	protected ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property) {
		return new ArrayList<Cell>();
	}
	
	/**
	 * This method is not used by this sub-class. 
	 */
	@Override
	protected ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property) {
		return new ArrayList<Cell>();
	}
	
}

