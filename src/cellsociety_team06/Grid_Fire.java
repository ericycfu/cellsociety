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
				if (Math.random()<=myCalculator.showParameter()*100){
					myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Burning"));
				}
				else myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Tree"));
			}
			else myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Tree"));
		}
		if (myCells[centerCellRow][centerCellCol].showCurrentProperty().equals("Empty"))
			myCells[centerCellRow][centerCellCol].setFutureState(myCalculator.getState("Empty"));
	}

	@Override
	protected ArrayList<Cell> findAdjacentCells(int row, int col) {
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

	@Override
	protected ArrayList<Cell> findAdjacentCellsWithCurrentProperty(int row, int col, String property) {
		// TODO Auto-generated method stub
		return new ArrayList<Cell>();
	}

	@Override
	protected ArrayList<Cell> findAdjacentCellsWithFutureProperty(int row, int col, String property) {
		// TODO Auto-generated method stub
		return new ArrayList<Cell>();
	}
	
}

