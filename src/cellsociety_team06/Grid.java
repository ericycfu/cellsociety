package cellsociety_team06;

import java.util.ArrayList;
import java.util.Random;

public abstract class Grid {
	protected Cell[][] myCells;
	protected int myRowNum;
	protected int myColNum;
	protected Calculator myCalculator;
	protected boolean end = false;
	protected Cell myCellType;
	
	public Grid(int rownum, int colnum, Calculator calculator, Cell CellType){
		myRowNum = rownum;
		myColNum = colnum;
		myCells = new Cell[rownum][colnum];
		myCalculator = calculator;
		myCellType = CellType;
	}
	
	protected int getRandomNumberInRange(int min, int max) {
    	if (min > max) {
    		throw new IllegalArgumentException("max must be greater than min");
    	}
    	if (min==max) return min;
    	Random r = new Random();
    	return r.nextInt((max - min) + 1) + min;
    }
	
	protected boolean checkBoundary(int row, int col){
		return (row<0 || row>=myRowNum || col<0 || col>=myColNum);
	}
	
	protected Cell getCell(int row, int col){
		return myCells[row][col];
	}
	
	protected ArrayList<Cell> getCellswithProperty(String property){
		ArrayList<Cell> myCellsUnoccupied = new ArrayList<Cell>();
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				if (myCells[i][j].showCurrentProperty().equals(property))
					myCellsUnoccupied.add(myCells[i][j]);
			}
		}
		return myCellsUnoccupied;
	}
	
	public abstract void updateCell(double prob, int centerCellRow, int centerCellCol);
	
	
	// takes in the locations of each cell and puts them into myGrid
	public void createCells(int row, int col, Cell cell){ 
		myCells[row][col] = cell;
	}
	
	protected abstract ArrayList<Cell> findAdjacentCells(int row, int col);
	
	protected abstract ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property);
	
	protected abstract ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property);
	
	public void iterate(){
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				updateCell(myCalculator.calculation(findAdjacentCells(i,j), getCell(i,j)),i,j);
			}
		}
		update();
	}

	protected void update(){
		for (int i = 0; i < myRowNum; i++)
			for (int j = 0; j < myColNum; j++){
				myCells[i][j].update();
			}
		
	}
	
	public boolean checkTerminate(){
		int unchangedCellNum = 0;
		for (int i = 0; i < myRowNum; i++)
			for (int j = 0; j < myColNum; j++){
				if (myCells[i][j].showCurrentState()==myCells[i][j].showFutureState())
					unchangedCellNum++;
			}
		return (unchangedCellNum == myRowNum*myColNum);
	}
}
