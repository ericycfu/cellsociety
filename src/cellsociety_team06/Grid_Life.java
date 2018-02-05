package cellsociety_team06;

import java.util.ArrayList;

public class Grid_Life extends Grid{
	
	public Grid_Life(int rownum, int colnum, Calculator myCalculator) {
		super(rownum, colnum, myCalculator);
	}

	@Override
	public void updateCell(double prob, int centerCellRow, int centerCellCol){
		if (prob==1)
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Live"));
		else if (prob==0)
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Dead"));
	}

	@Override
	public ArrayList<Cell> findAdjacentCells(int row, int col) {
		// TODO Auto-generated method stub
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		if (checkBoundary(row-1,col)) adjacentCells.add(getCell(row-1,col));
		if (checkBoundary(row,col-1)) adjacentCells.add(getCell(row,col-1));
		if (checkBoundary(row,col+1)) adjacentCells.add(getCell(row,col+1));
		if (checkBoundary(row+1,col)) adjacentCells.add(getCell(row+1,col));
		return adjacentCells;
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

