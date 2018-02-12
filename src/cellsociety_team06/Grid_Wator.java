package cellsociety_team06;

import java.util.ArrayList;

/**
 * This is the grid sub-class for the Wator simulation. 
 * @author Frank Yin
 *
 */
public class Grid_Wator extends Grid{
	private double energyGain;
	private static double defaultEnergyGain = 3;

public Grid_Wator(int rownum, int colnum, Calculator myCalculator) {
	 super(rownum, colnum, myCalculator);
	 energyGain = defaultEnergyGain;
 }
 
/**
 * This constructor takes in one more parameter that represents the reproduce rate 
 * for fish and shark. 
 * @param rownum
 * @param colnum
 * @param myCalculator
 * @param parameter
 */
public Grid_Wator(int rownum, int colnum, Calculator myCalculator, double parameter) {
	super(rownum, colnum, myCalculator);
	energyGain = parameter;
}
 
/**
 * This method is implemented to include the specifications of the Wator simulation. 
 */
public void updateCell(double prob, int centerCellRow, int centerCellCol){
	if (!myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Unoccupied")){
		myCells[centerCellRow][centerCellCol].updateChronon(myCells[centerCellRow][centerCellCol].showChronon()+1);
		if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Shark"))
			myCells[centerCellRow][centerCellCol].setEnergy(myCells[centerCellRow][centerCellCol].showEnergy()-1);
		if (prob==1){ 
			if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Fish")){
				fishBehavior(centerCellRow,centerCellCol);
			}
			else if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Shark")){
				sharkBehavior(centerCellRow,centerCellCol);
			}
		}
		else if (prob==0){ 
			if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Shark") && myCells[centerCellRow][centerCellCol].showEnergy()<0){ 
				sharkDies(myCells[centerCellRow][centerCellCol]);
			}
		}
	}
}
 
private void fishBehavior(int centerCellRow, int centerCellCol){
	ArrayList<Cell> freespace = findAdjacentCellsWithFutureProperty(centerCellRow, centerCellCol, "Unoccupied");
	if (!freespace.isEmpty()){
		int random = getRandomNumberInRange(0, freespace.size()-1);
		freespace.get(random).setFutureState(myCalculator.getState("Fish"));
		freespace.get(random).updateChronon(myCells[centerCellRow][centerCellCol].showChronon()); 
		if (myCells[centerCellRow][centerCellCol].showChronon() >= myCalculator.showParameter()){
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Fish"));
			freespace.get(random).resetChronon();
			myCells[centerCellRow][centerCellCol].resetChronon();
		}
		else {
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Unoccupied"));
			myCells[centerCellRow][centerCellCol].resetChronon();
		}
	}
}
 
private void sharkDies(Cell shark){
	shark.setFutureState(myCalculator.getState("Unoccupied"));
	shark.resetChronon();
	shark.setEnergy(0);
}
 
private void sharkBehavior(int centerCellRow, int centerCellCol){
	ArrayList<Cell> fishspace = findAdjacentCellsWithFutureProperty(centerCellRow, centerCellCol, "Fish");
	ArrayList<Cell> freespace = findAdjacentCellsWithFutureProperty(centerCellRow, centerCellCol, "Unoccupied");
	if (!fishspace.isEmpty()){
		movetoFish(fishspace,centerCellRow,centerCellCol);
	}
	else if (!freespace.isEmpty()){
		movetoWater(freespace,centerCellRow,centerCellCol);
	}
	else {
		if (myCells[centerCellRow][centerCellCol].showEnergy()<0) 
			sharkDies(myCells[centerCellRow][centerCellCol]);
	}
	
}

private void movetoFish(ArrayList<Cell> fishspace, int centerCellRow, int centerCellCol){
	int randomfish = getRandomNumberInRange(0, fishspace.size()-1);
	fishspace.get(randomfish).setFutureState(myCalculator.getState("Shark"));
	fishspace.get(randomfish).updateChronon(myCells[centerCellRow][centerCellCol].showChronon()); 
	fishspace.get(randomfish).setEnergy(myCells[centerCellRow][centerCellCol].showEnergy() + energyGain);
	if (fishspace.get(randomfish).showChronon()>=myCalculator.showParameter()){
		myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Shark"));
		fishspace.get(randomfish).resetChronon();
		myCells[centerCellRow][centerCellCol].resetChronon();
		myCells[centerCellRow][centerCellCol].resetEnergy();
	}
	if (fishspace.get(randomfish).showEnergy()<0) 
		sharkDies(fishspace.get(randomfish));
}

private void movetoWater(ArrayList<Cell> freespace, int centerCellRow, int centerCellCol){
	int randomwater = getRandomNumberInRange(0, freespace.size()-1);
	freespace.get(randomwater).setFutureState(myCalculator.getState("Shark"));
	freespace.get(randomwater).updateChronon(myCells[centerCellRow][centerCellCol].showChronon()); 
	myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Unoccupied"));
	if (freespace.get(randomwater).showChronon()>=myCalculator.showParameter()){
		myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Shark"));
		freespace.get(randomwater).resetChronon();
		myCells[centerCellRow][centerCellCol].resetChronon();
	}
	if (freespace.get(randomwater).showEnergy()<0) 
		sharkDies(freespace.get(randomwater));
}

public void resetEnergyGain(double newEnergyGain){
	energyGain = newEnergyGain;
}

/**
 * The Water simulation checks side adjacency. 
 */
@Override
protected ArrayList<Cell> findAdjacentCells(int row, int col){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		Cell currentCell = myCells[row][col];
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				if (currentCell.checkSideAdjacency(myCells[i][j]))
					adjacentCells.add(myCells[i][j]);
			}
		}
		return adjacentCells;
	}
 
/**
 * This checks for adjacent cells with a specific current property. 
 */
protected ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property){
		 ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
			Cell currentCell = myCells[row][col];
			for (int i = 0; i < myRowNum; i++){
				for (int j = 0; j < myColNum; j++){
					if (currentCell.checkSideAdjacency(myCells[i][j]) && myCells[i][j].showCurrentProperty().equals(property))
						adjacentCells.add(myCells[i][j]);
				}
			}
		return adjacentCells;
 }
 
/**
 * This checks for adjacent cells with a specific future property. 
 */
protected ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property){
		 ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
			Cell currentCell = myCells[row][col];
			for (int i = 0; i < myRowNum; i++){
				for (int j = 0; j < myColNum; j++){
					if (currentCell.checkSideAdjacency(myCells[i][j]) && myCells[i][j].showFutureProperty().equals(property))
						adjacentCells.add(myCells[i][j]);
				}
			}
		return adjacentCells;
 }
 
}

