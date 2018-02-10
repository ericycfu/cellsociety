package cellsociety_team06;

import java.util.ArrayList;

public class Grid_SugarScape extends Grid{
	private static int maxSugar = 4;
	private static int sugarGrowRate = 1;
	private int sugarGrowBackInterval;
	private double sugarMetabolism;
	
public Grid_SugarScape(int rownum, int colnum, Calculator myCalculator, Cell cellType) {
	 super(rownum, colnum, myCalculator, cellType);
}
 
public Grid_SugarScape(int rownum, int colnum, Calculator myCalculator, Cell cellType, int sugarinterval, double sugarmeta) {
	super(rownum, colnum, myCalculator, cellType);
	sugarGrowBackInterval = sugarinterval;
	sugarMetabolism = sugarmeta;
}
 
public void updateCell(double prob, int centerCellRow, int centerCellCol){
	
	if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Agent")){
		myCells[centerCellRow][centerCellCol].setEnergy(myCells[centerCellRow][centerCellCol].showEnergy() - sugarMetabolism);
		if (myCells[centerCellRow][centerCellCol].showEnergy() < 0){
			agentDies(myCells[centerCellRow][centerCellCol]);
		}
		else {
			myCells[centerCellRow][centerCellCol].updateChronon(myCells[centerCellRow][centerCellCol].showChronon() + 1); // agent age increased by 1
			ArrayList<Cell> adjacentPatches = findAdjacentCells(centerCellRow, centerCellCol);
			Cell patchTaken = getMaxSugarCells(adjacentPatches);
			eatsPatch(myCells[centerCellRow][centerCellCol], patchTaken, centerCellRow, centerCellCol);
		}
	}
	else {
		myCells[centerCellRow][centerCellCol].updateChronon(myCells[centerCellRow][centerCellCol].showChronon() + 1);
		if ((myCells[centerCellRow][centerCellCol].showEnergy() < maxSugar) && (myCells[centerCellRow][centerCellCol].showChronon() >= sugarGrowBackInterval)){
			myCells[centerCellRow][centerCellCol].setEnergy(myCells[centerCellRow][centerCellCol].showEnergy() + sugarGrowRate);
			myCells[centerCellRow][centerCellCol].resetChronon();
		}
		updatePatch(myCells[centerCellRow][centerCellCol]);
	}
	
}

private void updatePatch(Cell cell){
	if (cell.showEnergy()==0){
		cell.setFutureState(myCalculator.getState("ZeroPatch"));
	}
	if (cell.showEnergy()==1){
		cell.setFutureState(myCalculator.getState("OnePatch"));
	}
	if (cell.showEnergy()==2){
		cell.setFutureState(myCalculator.getState("TwoPatch"));
	}
	if (cell.showEnergy()==3){
		cell.setFutureState(myCalculator.getState("ThreePatch"));
	}
	if (cell.showEnergy()==4){
		cell.setFutureState(myCalculator.getState("FourPatch"));
	}
}

private void agentDies(Cell agent){
	agent.setFutureState(myCalculator.getState("ZeroPatch"));
	agent.resetChronon();
	agent.setEnergy(0);
}

private void eatsPatch(Cell agent, Cell patch, int centerCellRow, int centerCellCol){
	agent.setEnergy(agent.showEnergy() + patch.showEnergy());
	patch.setEnergy(agent.showEnergy());
	patch.setFutureState(myCalculator.getState("Agent"));
	patch.updateChronon(agent.showChronon());
	patch.setVision(agent.showVision());
	agent.setFutureState(myCalculator.getState("ZeroPatch"));
	agent.resetChronon();
	agent.setEnergy(0);
	if (agent.showChronon()>=myCalculator.showParameter()){
		checkMating(agent, patch, centerCellRow, centerCellRow);
	}
}

private void checkMating(Cell agent, Cell patch, int centerCellRow, int centerCellCol){
	ArrayList<Cell> matingCells = findAdjacentCellsWithCurrentProperty(centerCellRow, centerCellCol, "Agent");
	if (matingCells.size()!=0){
		for (int i = 0; i < matingCells.size(); i++){
			if (matingCells.get(i).showChronon()<myCalculator.showParameter()){
				matingCells.remove(i);
			}
		}
		if (matingCells.size()!=0){
			int random = getRandomNumberInRange(0, matingCells.size()-1);
			Cell matingCell = matingCells.get(random);
			agent.setFutureState(myCalculator.getState("Agent"));
			inherit(patch, matingCell, agent);
		}
	}
}

private void inherit(Cell parent1, Cell parent2, Cell children){
	int random = getRandomNumberInRange(0,1);
	if (random==0){
		children.setVision(parent1.showVision());
	}
	else {
		children.setVision(parent2.showVision());
	}
	parent1.resetChronon();
	parent2.resetChronon();
	parent1.setEnergy(parent1.showEnergy()/2);
	parent2.setEnergy(parent2.showEnergy()/2);
	children.setEnergy(parent1.showEnergy()/2 + parent2.showEnergy()/2);
}

private Cell getMaxSugarCells(ArrayList<Cell> adjacentpatches){
	double max = 0;
	ArrayList<Cell> maxSugarCells = new ArrayList<Cell>();
	for (int i = 0; i < adjacentpatches.size(); i++){
		if (adjacentpatches.get(i).showEnergy()>=max){
			max = adjacentpatches.get(i).showEnergy();
		}
	}
	for (int j = 0; j < adjacentpatches.size(); j++){
		if (adjacentpatches.get(j).showEnergy()==max){
			maxSugarCells.add(adjacentpatches.get(j));
		}
	}
	int random = getRandomNumberInRange(0, maxSugarCells.size()-1);
	return maxSugarCells.get(random);
	
}

 @Override
 protected ArrayList<Cell> findAdjacentCells(int row, int col){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		Cell currentCell = myCells[row][col];
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				if (currentCell.showVision()){
					if (!myCells[i][j].showCurrentProperty().equals("Agent") && (currentCell.checkSideAdjacency(myCells[i][j]) || currentCell.checkDiagonalAdjacency(myCells[i][j]))){
						adjacentCells.add(myCells[i][j]);
					}
				}
				else {
					if (!myCells[i][j].showCurrentProperty().equals("Agent") && currentCell.checkSideAdjacency(myCells[i][j])){
						adjacentCells.add(myCells[i][j]);
					}	
				}
			}
		}
		return adjacentCells;
	}
 
 protected ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property){
	 ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		Cell currentCell = myCells[row][col];
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				if (currentCell.showVision()){
					if (myCells[i][j].showCurrentProperty().equals(property) && (currentCell.checkSideAdjacency(myCells[i][j]) || currentCell.checkDiagonalAdjacency(myCells[i][j]))){
						adjacentCells.add(myCells[i][j]);
					}
				}
				else {
					if (myCells[i][j].showCurrentProperty().equals(property) && currentCell.checkSideAdjacency(myCells[i][j])){
						adjacentCells.add(myCells[i][j]);
					}	
				}
			}
		}
		return adjacentCells;
 }
 
 protected ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property){
		return new ArrayList<Cell>();
 }
 /*protected ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property){
  ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
  if (checkBoundary(row-1,col) && myCells[row-1][col].showCurrentProperty().equals(property)) adjacentCells.add(getCell(row-1,col));
  if (checkBoundary(row,col-1) && myCells[row][col-1].showCurrentProperty().equals(property)) adjacentCells.add(getCell(row,col-1));
  if (checkBoundary(row,col+1) && myCells[row][col+1].showCurrentProperty().equals(property)) adjacentCells.add(getCell(row,col+1));
  if (checkBoundary(row+1,col) && myCells[row+1][col].showCurrentProperty().equals(property)) adjacentCells.add(getCell(row+1,col));
  return adjacentCells;
 }
 
 protected ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property){
  ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
  if (checkBoundary(row-1,col) && myCells[row-1][col].showFutureProperty().equals(property)) adjacentCells.add(getCell(row-1,col));
  if (checkBoundary(row,col-1) && myCells[row][col-1].showFutureProperty().equals(property)) adjacentCells.add(getCell(row,col-1));
  if (checkBoundary(row,col+1) && myCells[row][col+1].showFutureProperty().equals(property)) adjacentCells.add(getCell(row,col+1));
  if (checkBoundary(row+1,col) && myCells[row+1][col].showFutureProperty().equals(property)) adjacentCells.add(getCell(row+1,col));
  return adjacentCells;
 }*/
 
 
}

