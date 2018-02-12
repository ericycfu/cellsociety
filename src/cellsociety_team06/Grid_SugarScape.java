package cellsociety_team06;

import java.util.ArrayList;

/**
 * This is the grid sub-class for the SugarScape simulation. 
 * @author Frank Yin
 *
 */
public class Grid_SugarScape extends Grid{
	private static int maxSugar = 4;
	private static int sugarGrowRate = 1;
	private int sugarGrowBackInterval;
	private double sugarMetabolism;
	
public Grid_SugarScape(int rownum, int colnum, Calculator myCalculator) {
	 super(rownum, colnum, myCalculator);
}

/**
 * This constructor takes in two more parameters to allow the implementation of 
 * the SugarScape simulation. 
 * @param rownum
 * @param colnum
 * @param myCalculator
 * @param sugarinterval: the time it takes for each patch to grow back sugar 
 * @param sugarmeta: the amount of sugar each agent consumes during every iteration 
 */
public Grid_SugarScape(int rownum, int colnum, Calculator myCalculator, int sugarinterval, double sugarmeta) {
	super(rownum, colnum, myCalculator);
	sugarGrowBackInterval = sugarinterval;
	sugarMetabolism = sugarmeta;
}
 
/**
 * This is the method for the cell-updating process of the SugarScape simulation. 
 */
public void updateCell(double prob, int centerCellRow, int centerCellCol){
	if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Agent")){
		myCells[centerCellRow][centerCellCol].setEnergy(myCells[centerCellRow][centerCellCol].showEnergy() - sugarMetabolism);
		System.out.println(myCells[centerCellRow][centerCellCol].showEnergy());
		if (myCells[centerCellRow][centerCellCol].showEnergy() < 0){
			agentDies(myCells[centerCellRow][centerCellCol]);
		}
		else {
			myCells[centerCellRow][centerCellCol].updateChronon(myCells[centerCellRow][centerCellCol].showChronon() + 1); // agent age increased by 1
			ArrayList<Cell> adjacentPatches = findAdjacentCells(centerCellRow, centerCellCol);
			if (!adjacentPatches.isEmpty()){
				Cell patchTaken = getMaxSugarCells(adjacentPatches);
				eatsPatch(myCells[centerCellRow][centerCellCol], patchTaken, centerCellRow, centerCellCol);
			}
		}
	}
	else{
		myCells[centerCellRow][centerCellCol].updateChronon(myCells[centerCellRow][centerCellCol].showChronon() + 1); 
	}
	
}

/**
 * This method overrides the original update method as this simulation requires a more 
 * complicated way of handling updates. 
 */
@Override
public void update(){
	for (int i = 0; i < myRowNum; i++){
		for (int j = 0; j < myColNum; j++){
			if (myCells[i][j].showCurrentState()<maxSugar && myCells[i][j].showCurrentState()==myCells[i][j].showFutureState()){
				if (myCells[i][j].showChronon()>=sugarGrowBackInterval){
					myCells[i][j].setFutureState(myCells[i][j].showCurrentState()+sugarGrowRate);
					myCells[i][j].resetChronon();
				}
			}
		}
	}
	for (int i = 0; i < myRowNum; i++){
		for (int j = 0; j < myColNum; j++){
			myCells[i][j].update();
		}
	}
}

private void agentDies(Cell agent){
	agent.setFutureState(myCalculator.getState("ZeroPatch"));
	agent.resetChronon();
	agent.setEnergy(0);
}

private void eatsPatch(Cell agent, Cell patch, int centerCellRow, int centerCellCol){
	agent.setEnergy(agent.showEnergy() + patch.showCurrentState());
	patch.setEnergy(agent.showEnergy());
	patch.setFutureState(myCalculator.getState("Agent"));
	System.out.println(myCalculator.getState("Agent"));
	patch.updateChronon(agent.showChronon());
	patch.setVision(agent.showVision());
	agent.setFutureState(myCalculator.getState("ZeroPatch"));
	agent.resetChronon();
	agent.setEnergy(0);
	if (patch.showChronon()>=myCalculator.showParameter()){
		checkMating(patch, agent, centerCellRow, centerCellRow);
	}
}

private void checkMating(Cell patch, Cell agent, int centerCellRow, int centerCellCol){
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
		if (adjacentpatches.get(i).showCurrentState()>=max){
			max = adjacentpatches.get(i).showCurrentState();
		}
	}
	for (int j = 0; j < adjacentpatches.size(); j++){
		if (adjacentpatches.get(j).showCurrentState()==max){
			maxSugarCells.add(adjacentpatches.get(j));
		}
	}
	int random = getRandomNumberInRange(0, maxSugarCells.size()-1);
	return maxSugarCells.get(random);
	
}

/**
 * This method allows other classes to reset the parameter so that the simulation 
 * can be adjusted or modified accordingly. 
 * @param newInterval
 */
public void resetSugarInterval(int newInterval){
	sugarGrowBackInterval = newInterval;
}

/**
 * This method allows other classes to reset the parameter so that the simulation 
 * can be adjusted or modified accordingly. 
 * @param newSugarMeta
 */
public void resetSugarMetabolism(int newSugarMeta){
	sugarMetabolism = newSugarMeta;
}

/**
 * This method is implemented so that each agent only finds adjacent patches. 
 */
@Override
protected ArrayList<Cell> findAdjacentCells(int row, int col){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		Cell currentCell = myCells[row][col];
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				if (currentCell.showVision()){
					if (!myCells[i][j].showCurrentProperty().equals("Agent") && !myCells[i][j].showFutureProperty().equals("Agent") && ((currentCell.checkSideAdjacency(myCells[i][j]) || currentCell.checkDiagonalAdjacency(myCells[i][j])))){
						adjacentCells.add(myCells[i][j]);
					}
				}
				else {
					if (!myCells[i][j].showCurrentProperty().equals("Agent") && !myCells[i][j].showFutureProperty().equals("Agent") && currentCell.checkSideAdjacency(myCells[i][j])){
						adjacentCells.add(myCells[i][j]);
					}	
				}
			}
		}
		return adjacentCells;
	}

/**
 * This method is implemented so that each agent only finds adjacent patches with some property. 
 */
 protected ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property){
	 ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		Cell currentCell = myCells[row][col];
		for (int i = 0; i < myRowNum; i++){
			for (int j = 0; j < myColNum; j++){
				if (currentCell.showVision()){
					if (myCells[i][j].showCurrentProperty().equals(property) && ((currentCell.checkSideAdjacency(myCells[i][j]) || currentCell.checkDiagonalAdjacency(myCells[i][j])))){
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
 
 /**
  * This method is not used in this sub-class. 
  */
 protected ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property){
		return new ArrayList<Cell>();
 }
 
}

