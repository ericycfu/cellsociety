package cellsociety_team06;

import java.util.ArrayList;

public abstract class Grid {
	private Cell[][] myCells;
	private int myRowNum;
	private int myColNum;
	private Calculator myCalculator;
	
	public Grid(int rownum, int colnum){
		myRowNum = rownum;
		myColNum = colnum;
		myCells = new Cell[rownum][colnum];
	}
	
	public abstract void updateCell(double prob);
	
	// takes in the locations of each cell and puts them into myGrid
	public void createCells(int row, int col, Cell cell){ 
		myCells[row][col] = cell;
	}
	
	public abstract ArrayList<Cell> findAdjacentCells(int row, int col);
	
	public void iterate(){
		for (int i = 0; i < myRowNum; i++)
			for (int j = 0; j < myColNum; j++){
				updateCell(myCalculator.calculation(findAdjacentCells(i,j)));
			}
	}
	
}
