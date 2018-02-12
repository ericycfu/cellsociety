package cellsociety_team06;

import java.util.ArrayList;
import java.util.Random;

/**
 * The purpose of this class is to implement all kinds of simulations 
 * that this cell society project can carry. It has access to all the 
 * cells and contains the methods to determine the future states of each 
 * cell and to update them in the next iteration. 
 * This class depends on the cell class and the calculator class, and 
 * passes information to the simulation class, without which the tasks 
 * of the simulations wouldn't be able to carry out. 
 * @author Frank Yin
 *
 */
public abstract class Grid {
	
	/**
	 * A 2D array that stores all the cells in the society. This parameter 
	 * can be passed to the simulation class for visualization purposes. 
	 */
	protected Cell[][] myCells;
	
	/**
	 * An integer that stores the grid's row number.
	 */
	protected int myRowNum;
	
	/**
	 * An integer that stores the grid's column number.
	 */
	protected int myColNum;
	
	/**
	 * The calculator that will be used by this grid to assist the cell-updating 
	 * process. It also contains some important parameter to be called in some method 
	 * of the grid class. 
	 */
	protected Calculator myCalculator;
	
	/**
	 * The boolean logic that represents the ending of one simulation. All simulations 
	 * implemented for this project allows the states to achieve dynamic equilibrium, so 
	 * this implementation is only necessary if future extensions require the cell-updating 
	 * process to terminate by itself. 
	 */
	protected boolean end = false;
	
	/**
	 * The grid constructor takes in three parameters, and initializes the 2D array of cells 
	 * to be mapped later. 
	 * @param rownum: row number
	 * @param colnum: column number
	 * @param calculator: calculator used in this grid 
	 */
	public Grid(int rownum, int colnum, Calculator calculator){
		myRowNum = rownum;
		myColNum = colnum;
		myCells = new Cell[rownum][colnum];
		myCalculator = calculator;
	}
	
	/**
	 * A helper method that chooses one random item from an array of items. This method is 
	 * very useful because lots of simulations require the cells to randomly select potential 
	 * neighbors to move to. 
	 * @param min: lower bound of the array, usually 0
	 * @param max: upper bound of the array, usually arraylist.size()-1 
	 * @return
	 */
	protected int getRandomNumberInRange(int min, int max) {
    	if (min > max) {
    		throw new IllegalArgumentException("max must be greater than min");
    	}
    	if (min==max) return min;
    	Random r = new Random();
    	return r.nextInt((max - min) + 1) + min;
    }

	/**
	 * This method gets a cell from one specific location in the grid. Since 
	 * it is mainly used in the the grid class and its sub-classes as a helper, 
	 * it is set to be protected. 
	 * @param row: the row number of the cell
	 * @param col: the column number of the cell 
	 * @return: returns the cell to the requester 
	 */
	protected Cell getCell(int row, int col){
		return myCells[row][col];
	}
	
	/**
	 * This method is used to find all the cells with some property, so that some 
	 * cell can move to these cells as needed. The specific rules are determined by 
	 * the simulation. 
	 * @param property: cell state in String format 
	 * @return: An ArrayList of certain cells 
	 */
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
	
	/**
	 * This method determines the future state of the cell at the 
	 * index (centerCellRow, centerCellCol). This also involves in 
	 * the state/property changes of other related cells. 
	 * 
	 * @param prob: double returned from the calculator class 
	 * @param centerCellRow: the row number of the cell
	 * @param centerCellCol: the column number of the cell 
	 */
	public abstract void updateCell(double prob, int centerCellRow, int centerCellCol);
	
	/**
	 * This method puts a cell in the designated location of the grid. 
	 * @param row
	 * @param col
	 * @param cell
	 */
	public void createCells(int row, int col, Cell cell){ 
		myCells[row][col] = cell;
	}
	
	/**
	 * This method returns the grid's row number for uses in the 
	 * simulation class. 
	 * @return
	 */
	public int showRowNum(){
		return myRowNum;
	}
	
	/**
	 * This method returns the grid's column number for uses in 
	 * the simulation class. 
	 * @return
	 */
	public int showColNum(){
		return myColNum;
	}
	
	/**
	 * This method returns a copy of the 2D array of cells so that 
	 * other classes can extract state information without the 
	 * ability to modify the contents of the cells. 
	 * @return
	 */
	public Cell[][] showMyCells(){
		return myCells.clone();
	}
	
	/**
	 * This method returns an ArrayList of cells that are adjacent to the cell indexed by the 
	 * two parameters. This is used to find the neighbors of each cell for the cell-updating 
	 * process. Each simulation might have its own ways of determining neighbors so this method 
	 * should be set to abstract. 
	 * @param row
	 * @param col
	 * @return
	 */
	protected abstract ArrayList<Cell> findAdjacentCells(int row, int col);
	
	/**
	 * This method has similar functionality with the above method, but with more specifications - 
	 * only the adjacent cells with its current property equal to the given parameter will be 
	 * added to this ArrayList. 
	 * @param row
	 * @param col
	 * @param property
	 * @return
	 */
	protected abstract ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property);
	
	/**
	 * This method has similar functionality with the above method, but with more specifications - 
	 * only the adjacent cells with its future property equal to the given parameter will be 
	 * added to this ArrayList. 
	 * @param row
	 * @param col
	 * @param property
	 * @return
	 */
	protected abstract ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property);
	
	/**
	 * This method iterates through all the cells in the grid and calls the updateCell method to set 
	 * the future states of the cells. After one whole iteration, it updates the grid by setting the 
	 * cell's current property to its future property. 
	 */
	public void iterate(){
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				updateCell(myCalculator.calculation(findAdjacentCells(i,j), getCell(i,j)),i,j);
			}
		}
		update();
	}
	
	/**
	 * This is a helper method used by the iterate method to update all the cells' properties. 
	 */
	protected void update(){
		for (int i = 0; i < myRowNum; i++)
			for (int j = 0; j < myColNum; j++){
				myCells[i][j].update();
			}
		
	}
	
	/**
	 * This method checks whether the simulation is terminated or not. As explained earlier, 
	 * this might be useful under the circumstances where the simulations require to stop given 
	 * certain conditions. 
	 * @return
	 */
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
