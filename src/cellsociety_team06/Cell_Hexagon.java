package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cell_Hexagon extends Cell{
	
	public Cell_Hexagon(String cellType, double centerXLocation, double centerYLocation, double sideLength, String[] properties, Color[] colors, int initialState){
		super(cellType, centerXLocation, centerYLocation, sideLength, properties, colors, initialState);
		makePolygon();
	}
	
	protected void makePolygon(){
		double[] mySixPoints = new double[]
				{	
					myCenterXLocation, myCenterYLocation - mySideLength, // very top point
					myCenterXLocation - Math.sqrt(3)*mySideLength/2, myCenterYLocation - mySideLength/2, // upper left point 
					myCenterXLocation + Math.sqrt(3)*mySideLength/2, myCenterYLocation - mySideLength/2, // upper right point
					myCenterXLocation - Math.sqrt(3)*mySideLength/2, myCenterYLocation + mySideLength/2, // lower left point 
					myCenterXLocation + Math.sqrt(3)*mySideLength/2, myCenterYLocation + mySideLength/2, // lower right point
					myCenterXLocation, myCenterYLocation + mySideLength // very bottom point
				};
		
		myPolygon = new Polygon(mySixPoints);
		myPolygon.setFill(myColors[currentState]);
	}
	
	@Override
	public boolean checkTopAdjacency(Cell cell){
		return ((locationMatch(myCenterXLocation, (cell.showCenterXLoc() + Math.sqrt(3)*mySideLength/2)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() + 1.5*mySideLength))) ||
				(locationMatch(myCenterXLocation, (cell.showCenterXLoc() - Math.sqrt(3)*mySideLength/2)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() + 1.5*mySideLength))));
	}
	
	@Override 
	public boolean checkLeftAdjacency(Cell cell){
		return ((locationMatch(myCenterXLocation, (cell.showCenterXLoc() + Math.sqrt(3)*mySideLength)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc()))));
	}
	
	@Override 
	public boolean checkRightAdjacency(Cell cell){
		return ((locationMatch(myCenterXLocation, (cell.showCenterXLoc() - Math.sqrt(3)*mySideLength)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc()))));
	}
	
	@Override 
	public boolean checkBotAdjacency(Cell cell){
		return ((locationMatch(myCenterXLocation, (cell.showCenterXLoc() + Math.sqrt(3)*mySideLength/2)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() - 1.5*mySideLength))) ||
				(locationMatch(myCenterXLocation, (cell.showCenterXLoc() - Math.sqrt(3)*mySideLength/2)) && locationMatch(myCenterYLocation, (cell.showCenterYLoc() - 1.5*mySideLength))));
	}
	
	@Override
	public boolean checkSideAdjacency(Cell cell){
		return (checkTopAdjacency(cell) ||
				checkLeftAdjacency(cell) ||
				checkRightAdjacency(cell) ||
				checkBotAdjacency(cell)
		       );
	}
	
	@Override
	public boolean checkDiagonalAdjacency(Cell cell){
		return checkDiagonalAdjacency(cell);		
	}
	
}
