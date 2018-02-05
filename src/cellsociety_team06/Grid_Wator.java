package cellsociety_team06;

import java.util.ArrayList;

public class Grid_Wator extends Grid{
	private double energyGain;
	private static double defaultEnergyGain = 3;
	
	public Grid_Wator(int rownum, int colnum, Calculator myCalculator) {
		super(rownum, colnum, myCalculator);
		energyGain = defaultEnergyGain;
	}
	
	public Grid_Wator(int rownum, int colnum, Calculator myCalculator, double parameter) {
		super(rownum, colnum, myCalculator);
		energyGain = parameter;
	}
	
	public void updateCell(double prob, int centerCellRow, int centerCellCol){
		if (!myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Unoccupied")){
			myCells[centerCellRow][centerCellCol].updateChronon(myCells[centerCellRow][centerCellCol].showChronon()+1);
			if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Shark"))
				myCells[centerCellRow][centerCellCol].setEnergy(myCells[centerCellRow][centerCellCol].showEnergy()-1);
			if (prob==1){ // prob = 1 means having places to move
				if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Fish")){
					fishBehavior(centerCellRow,centerCellCol);
				}
				else if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Shark")){
					sharkBehavior(centerCellRow,centerCellCol);
				}
			}
			else if (prob==0){ // prob = 0 means having no places to move
				myCells[centerCellRow][centerCellCol].setFutureState(myCells[centerCellRow][centerCellCol].showCurrentState());
				if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Shark") && myCells[centerCellRow][centerCellCol].showEnergy()<0){ 
					sharkDies(myCells[centerCellRow][centerCellCol]);
				}
			}
		}
	}
	
	private void fishBehavior(int centerCellRow, int centerCellCol){
		ArrayList<Cell> freespace = findAdjacentCellsWithFutureProperty(centerCellRow, centerCellCol, "Unoccupied");
		if (freespace!=null){
			int random = getRandomNumberInRange(0, freespace.size());
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
		if (fishspace!=null){
			int randomfish = getRandomNumberInRange(0, fishspace.size());
			fishspace.get(randomfish).setFutureState(myCalculator.getState("Shark"));
			fishspace.get(randomfish).updateChronon(myCells[centerCellRow][centerCellCol].showChronon()); 
			fishspace.get(randomfish).setEnergy(myCells[centerCellRow][centerCellCol].showEnergy() + energyGain);
			if (fishspace.get(randomfish).showChronon()>=myCalculator.showParameter()){
				myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Shark"));
				fishspace.get(randomfish).resetChronon();
				myCells[centerCellRow][centerCellCol].resetChronon();
			}
			if (fishspace.get(randomfish).showEnergy()<0) 
				sharkDies(fishspace.get(randomfish));
		}
		else if (freespace!=null){
			int randomwater = getRandomNumberInRange(0, freespace.size());
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
		else {
			if (myCells[centerCellRow][centerCellCol].showEnergy()<0) 
				sharkDies(myCells[centerCellRow][centerCellCol]);
		}
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
		
	}
	
}
