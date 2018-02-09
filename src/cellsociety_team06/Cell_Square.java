package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cell_Square extends Cell{
	
	public Cell_Square(String cellType, double centerXLocation, double centerYLocation, double sideLength, String[] properties, Color[] colors, int initialState){
		super(cellType, centerXLocation, centerYLocation, sideLength, properties, colors, initialState);
		makePolygon();
	}
	
	protected void makePolygon(){
		double[] myFourPoints = new double[]
				{	
					myCenterXLocation - mySideLength/2, myCenterYLocation - mySideLength/2, // top left corner 
					myCenterXLocation + mySideLength/2, myCenterYLocation - mySideLength/2, // top right corner
					myCenterXLocation - mySideLength/2, myCenterYLocation + mySideLength/2, // bottom left corner
					myCenterXLocation + mySideLength/2, myCenterYLocation + mySideLength/2  // bottom right corner
				};
		
		myPolygon = new Polygon(myFourPoints);
		myPolygon.setFill(myColors[currentState]);
	}
	
	public void resetEnergy(){
		myEnergy = initialEnergy;
	}
	
	public void setEnergy(double value){
		myEnergy = value;
	}
	
	public void changeEnergy(double value){
		myEnergy = myEnergy + value;
	}
	
	@Override
	public boolean checkTopAdjacency(Cell cell){
		return ((locationMatch(myCenterXLocation, cell.showCenterXLoc()) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() + mySideLength))));
	}
	
	@Override 
	public boolean checkLeftAdjacency(Cell cell){
		return ((locationMatch(myCenterXLocation, (cell.showCenterXLoc() + mySideLength)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc()))));
	}
	
	@Override
	public boolean checkRightAdjacency(Cell cell){
		return ((locationMatch(myCenterXLocation, (cell.showCenterXLoc() - mySideLength)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc()))));
	}
	
	@Override
	public boolean checkBotAdjacency(Cell cell){
		return ((locationMatch(myCenterXLocation, cell.showCenterXLoc()) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() - mySideLength))));
	}
	
	@Override
	public boolean checkSideAdjacency(Cell cell){
		return (checkTopAdjacency(cell) ||
				checkBotAdjacency(cell) ||
				checkLeftAdjacency(cell) ||
				checkRightAdjacency(cell)
		       );
	}
	
	private boolean checkSquareTopDiagonal(Cell cell){
		return ((locationMatch(myCenterXLocation, (cell.showCenterXLoc() + mySideLength)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() + mySideLength))) ||
				(locationMatch(myCenterXLocation, (cell.showCenterXLoc() - mySideLength)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() + mySideLength))));
	}
	
	private boolean checkSquareBotDiagonal(Cell cell){
		return ((locationMatch(myCenterXLocation, (cell.showCenterXLoc() + mySideLength)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() - mySideLength)))||
				(locationMatch(myCenterXLocation, (cell.showCenterXLoc() - mySideLength)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() - mySideLength))));
	}
	@Override
	public boolean checkDiagonalAdjacency(Cell cell){
		return (checkSquareTopDiagonal(cell) || checkSquareBotDiagonal(cell));		
	}
	
	public double showEnergy(){
		return myEnergy;
	}
	
	public void resetChronon(){
		myChronon = 0;
	}
	
	public void updateChronon(double value){
		myChronon = value;
	}
	
	public double showChronon(){
		return myChronon;
	}
	
	public int showCurrentState(){
		return currentState;
	}
	
	public String showCurrentProperty(){
		return myProperties[currentState];
	}
	
	public int showFutureState(){
		return futureState;
	}
	
	public String showFutureProperty(){
		return myProperties[futureState];
	}
	
	public void update(){
		currentState = futureState;
		myPolygon.setFill(myColors[currentState]);
	}
	
	public void setFutureState(int nextState){
		futureState = nextState;
	}
	
}
