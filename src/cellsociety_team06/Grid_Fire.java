package cellsociety_team06;

import java.util.ArrayList;

public class Grid_Fire extends Grid{
	
	public Grid_Fire(int rownum, int colnum, Calculator myCalculator) {
		super(rownum, colnum, myCalculator);
	}
	
	public void updateCell(double prob, int centerCellRow, int centerCellCol){
		if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Burning"))
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Empty"));
		if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Tree") && myCells[centerCellRow][centerCellCol].showFutureProperty().equals("Tree")){
			if (prob==1){ // one adjacent on fire
				int random = getRandomNumberInRange(0,100);
				if (random<=myCalculator.showParameter()*100){
					myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Burning"));
				}
				else myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Tree"));
			}
			else myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Tree"));
		}
		if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Empty"))
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Empty"));
	}
	
	
	
	// takes in the locations of each cell and puts them into myGrid
	
	public ArrayList<Cell> findAdjacentCells(int row, int col){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		if (checkBoundary(row-1,col)) adjacentCells.add(getCell(row-1,col));
		if (checkBoundary(row,col-1)) adjacentCells.add(getCell(row,col-1));
		if (checkBoundary(row,col+1)) adjacentCells.add(getCell(row,col+1));
		if (checkBoundary(row+1,col)) adjacentCells.add(getCell(row+1,col));
		return adjacentCells;
	}
	
	public ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		if (checkBoundary(row-1,col) && myCells[row-1][col].showCurrentProperty().equals(property)) adjacentCells.add(getCell(row-1,col));
		if (checkBoundary(row,col-1) && myCells[row][col-1].showCurrentProperty().equals(property)) adjacentCells.add(getCell(row,col-1));
		if (checkBoundary(row,col+1) && myCells[row][col+1].showCurrentProperty().equals(property)) adjacentCells.add(getCell(row,col+1));
		if (checkBoundary(row+1,col) && myCells[row+1][col].showCurrentProperty().equals(property)) adjacentCells.add(getCell(row+1,col));
		return adjacentCells;
	}
	
	public ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		if (checkBoundary(row-1,col) && myCells[row-1][col].showFutureProperty().equals(property)) adjacentCells.add(getCell(row-1,col));
		if (checkBoundary(row,col-1) && myCells[row][col-1].showFutureProperty().equals(property)) adjacentCells.add(getCell(row,col-1));
		if (checkBoundary(row,col+1) && myCells[row][col+1].showFutureProperty().equals(property)) adjacentCells.add(getCell(row,col+1));
		if (checkBoundary(row+1,col) && myCells[row+1][col].showFutureProperty().equals(property)) adjacentCells.add(getCell(row+1,col));
		return adjacentCells;
	}

	@Override
	protected void updateUnoccupiedCellArray() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
