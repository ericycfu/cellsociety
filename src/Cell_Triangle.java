package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cell_Triangle extends Cell{
	private double[] myThreePoints;
	private boolean upwardTriangle;
	
	public Cell_Triangle(String cellType, double centerXLocation, double centerYLocation, double sideLength, String[] properties, Color[] colors, int initialState, boolean upward){
		super(cellType, centerXLocation, centerYLocation, sideLength, properties, colors, initialState);
		upwardTriangle = upward;
		makePolygon();
	}
	
	protected void makePolygon(){
		if (upwardTriangle){
			myThreePoints = new double[]
					{	
						myCenterXLocation, myCenterYLocation - Math.sqrt(3)*mySideLength/4, // very top point
						myCenterXLocation - mySideLength/2, myCenterYLocation + Math.sqrt(3)*mySideLength/4, // left point 
						myCenterXLocation + mySideLength/2, myCenterYLocation + Math.sqrt(3)*mySideLength/4 // right point
					};
		}
		else {
			myThreePoints = new double[]
					{	
						myCenterXLocation, myCenterYLocation + Math.sqrt(3)*mySideLength/4, // very bottom point
						myCenterXLocation - mySideLength/2, myCenterYLocation - Math.sqrt(3)*mySideLength/4, // left point 
						myCenterXLocation + mySideLength/2, myCenterYLocation - Math.sqrt(3)*mySideLength/4 // right point
					};
		}
		myPolygon = new Polygon(myThreePoints);
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
		if (upwardTriangle)
			return ((myCenterXLocation == (cell.showCenterXLoc() ) && myCenterYLocation == (cell.showCenterYLoc() - Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() - mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc())) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc()))
					);
		else 
			return ((myCenterXLocation == (cell.showCenterXLoc() ) && myCenterYLocation == (cell.showCenterYLoc() + Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() - mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc())) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc()))
					);
	}
	
	@Override
	public boolean checkDiagonalAdjacency(Cell cell){
		if (upwardTriangle)
			return ((myCenterXLocation == (cell.showCenterXLoc() - mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc() + Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc() + Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == cell.showCenterXLoc() && myCenterYLocation == (cell.showCenterYLoc() + Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() - mySideLength) && myCenterYLocation == cell.showCenterYLoc()) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength) && myCenterYLocation == cell.showCenterYLoc()) ||
					(myCenterXLocation == (cell.showCenterXLoc() - mySideLength) && myCenterYLocation == (cell.showCenterYLoc() - Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() - mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc() - Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc() - Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength) && myCenterYLocation == (cell.showCenterYLoc() - Math.sqrt(3)*mySideLength/2))
					);
		else 
			return ((myCenterXLocation == (cell.showCenterXLoc() - mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc() - Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc() - Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == cell.showCenterXLoc() && myCenterYLocation == (cell.showCenterYLoc() - Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() - mySideLength) && myCenterYLocation == cell.showCenterYLoc()) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength) && myCenterYLocation == cell.showCenterYLoc()) ||
					(myCenterXLocation == (cell.showCenterXLoc() - mySideLength) && myCenterYLocation == (cell.showCenterYLoc() + Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() - mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc() + Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength/2) && myCenterYLocation == (cell.showCenterYLoc() + Math.sqrt(3)*mySideLength/2)) ||
					(myCenterXLocation == (cell.showCenterXLoc() + mySideLength) && myCenterYLocation == (cell.showCenterYLoc() + Math.sqrt(3)*mySideLength/2))
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
