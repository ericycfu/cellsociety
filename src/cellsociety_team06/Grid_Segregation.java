package cellsociety_team06;

import java.util.ArrayList;

public class Grid_Segregation extends Grid{
	
	public Grid_Segregation(int rownum, int colnum, Calculator myCalculator) {
		super(rownum, colnum, myCalculator);
	}

	public void updateCell(double prob, int centerCellRow, int centerCellCol){
		if (!myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Unoccupied")){
			if (prob==1){
				int random = getRandomNumberInRange(0, myCellsUnoccupiedNextIteration.size()-1);
				myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Unoccupied"));
				myCellsUnoccupiedNextIteration.get(random).setFutureState(myCells[centerCellRow][centerCellCol].showCurrentState());
				myCellsUnoccupiedNextIteration.remove(random);
				myCellsUnoccupiedNextIteration.add(myCells[centerCellRow][centerCellCol]);
			}
			else if (prob==0){
				myCells[centerCellRow][centerCellCol].setFutureState(myCells[centerCellRow][centerCellCol].showCurrentState());
			}
		}
	}
	
	// takes in the locations of each cell and puts them into myGrid
	
	public ArrayList<Cell> findAdjacentCells(int row, int col){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		if (checkBoundary(row-1,col-1)) adjacentCells.add(getCell(row-1,col-1));
		if (checkBoundary(row-1,col)) adjacentCells.add(getCell(row-1,col));
		if (checkBoundary(row-1,col+1)) adjacentCells.add(getCell(row-1,col+1));
		if (checkBoundary(row,col-1)) adjacentCells.add(getCell(row,col-1));
		if (checkBoundary(row,col+1)) adjacentCells.add(getCell(row,col+1));
		if (checkBoundary(row+1,col-1)) adjacentCells.add(getCell(row+1,col-1));
		if (checkBoundary(row+1,col)) adjacentCells.add(getCell(row+1,col));
		if (checkBoundary(row+1,col+1)) adjacentCells.add(getCell(row+1,col+1));
		return adjacentCells;
	}

	@Override
	protected void updateUnoccupiedCellArray() {
		myCellsUnoccupiedNextIteration = new ArrayList<Cell>();
		for (int i = 0; i < myRowNum; i++)
			for (int j = 0; j < myColNum; j++){
				if (myCells[i][j].showCurrentProperty().equals("Unoccupied")) 
					myCellsUnoccupiedNextIteration.add(myCells[i][j]);
			}
	}

	@Override
	public ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
