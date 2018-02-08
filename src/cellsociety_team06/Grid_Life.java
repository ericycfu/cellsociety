package cellsociety_team06;

import java.util.ArrayList;

public class Grid_Life extends Grid{
	
	public Grid_Life(int rownum, int colnum, Calculator myCalculator, Cell cellType) {
		super(rownum, colnum, myCalculator, cellType);
	}

	@Override
	public void updateCell(double prob, int centerCellRow, int centerCellCol){
		if (prob==1)
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Live"));
		else if (prob==0){
			System.out.println(myCalculator.getState("Dead")+"!!!");

			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Dead"));
		}
	}

	/*@Override
	public ArrayList<Cell> findAdjacentCells(int row, int col) {
		// TODO Auto-generated method stub
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		if (checkBoundary(row-1,col)) adjacentCells.add(getCell(row-1,col));
		if (checkBoundary(row,col-1)) adjacentCells.add(getCell(row,col-1));
		if (checkBoundary(row,col+1)) adjacentCells.add(getCell(row,col+1));
		if (checkBoundary(row+1,col)) adjacentCells.add(getCell(row+1,col));
		return adjacentCells;
	}*/
	
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
	
	@Override
	protected ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

