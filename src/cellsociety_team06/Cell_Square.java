package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cell_Square extends Cell{
	private double[] myFourPoints;
	
	public Cell_Square(String cellType, double centerXLocation, double centerYLocation, double sideLength, String[] properties, Color[] colors, int initialState){
		super(cellType, centerXLocation, centerYLocation, sideLength, properties, colors, initialState);
		makePolygon();
	}
	
	protected void makePolygon(){
		myFourPoints = new double[]
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
	public boolean checkSideAdjacency(Cell cell){
		return ((myCenterXLocation == (cell.showCenterXLoc() + mySideLength) && myCenterYLocation == (cell.showCenterYLoc())) ||
				(myCenterXLocation == (cell.showCenterXLoc() - mySideLength) && myCenterYLocation == (cell.showCenterYLoc())) ||
				(myCenterXLocation == cell.showCenterXLoc() && myCenterYLocation == (cell.showCenterYLoc() + mySideLength)) ||
				(myCenterXLocation == cell.showCenterXLoc() && myCenterYLocation == (cell.showCenterYLoc() - mySideLength))
		       );
	}
	
	@Override
	public boolean checkDiagonalAdjacency(Cell cell){
		return ((myCenterXLocation == (cell.showCenterXLoc() + mySideLength) && myCenterYLocation == (cell.showCenterYLoc() + mySideLength))||
				(myCenterXLocation == (cell.showCenterXLoc() + mySideLength) && myCenterYLocation == (cell.showCenterYLoc() - mySideLength))||
				(myCenterXLocation == (cell.showCenterXLoc() - mySideLength) && myCenterYLocation == (cell.showCenterYLoc() - mySideLength))||
				(myCenterXLocation == (cell.showCenterXLoc() - mySideLength) && myCenterYLocation == (cell.showCenterYLoc() - mySideLength))
			   );		
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