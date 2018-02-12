package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * The purpose of this class is to define an unsubstantiatable object 
 * whose sub-class deviants can be applied to further extensions of 
 * cell-society simulation. 
 * It is assumed that all cells have predefined states and properties. 
 * @author Frank Yin
 *
 */
public abstract class Cell{

	protected int currentState;
	protected int futureState;
	protected String[] myProperties;
	protected Color[] myColors;
	protected double myChronon;
	protected double myEnergy;
	protected double initialEnergy;
	protected String myType;
	protected double myCenterXLocation;
	protected double myCenterYLocation;
	protected double mySideLength;
	protected Polygon myPolygon;
	protected boolean myVision; // cell that has vision can also check diagonal cells
	private static double delta = 0.001;
	
	/**
	 * This constructor initializes the cell with center location, initial states, and all other 
	 * important defining categories that can be applied to any extension of the simulation. It 
	 * contains the shape of itself, which carries color indicators that get updated with state 
	 * change. 
	 * @param cellType: shape of cell in String format
	 * @param centerXLocation: center point x location
	 * @param centerYLocation: center point y loaction
	 * @param sideLength: length of side 
	 * @param properties: An array of String to represent properties of the cell
	 * @param colors: An array of Color corresponding to the states
	 * @param initialState: initial state of cell
	 */
	public Cell(String cellType, double centerXLocation, double centerYLocation, double sideLength, String[] properties, Color[] colors, int initialState){
		myProperties = properties;
		currentState = initialState;
		futureState = currentState;
		myChronon = 0;
		myColors = colors;
		myCenterXLocation = centerXLocation;
		myCenterYLocation = centerYLocation;
		myType = cellType;
		mySideLength = sideLength;
	}
	
	/**
	 * The constructor that takes in one more parameter (initial energy for predator-prey type
	 * simulations)
	 * @param cellType
	 * @param centerXLocation
	 * @param centerYLocation
	 * @param sideLength
	 * @param properties
	 * @param colors
	 * @param initialState
	 * @param initialEnergyinput
	 */
	public Cell(String cellType, double centerXLocation, double centerYLocation, double sideLength, String[] properties, Color[] colors, int initialState, double initialEnergyinput){
		myProperties = properties;
		currentState = initialState;
		futureState = currentState; // initialize futureState to be the same with currentState
		myChronon = 0;
		initialEnergy = initialEnergyinput;
		myEnergy = initialEnergy;
		myColors = colors;
		myCenterXLocation = centerXLocation;
		myCenterYLocation = centerYLocation;
		myType = cellType;
		mySideLength = sideLength;
	}
	
	/**
	 * The constructor takes in two more parameters (the other one is for simulations with genetic 
	 * merging, such as SugarScape)
	 * @param cellType
	 * @param centerXLocation
	 * @param centerYLocation
	 * @param sideLength
	 * @param properties
	 * @param colors
	 * @param initialState
	 * @param initialEnergyinput
	 * @param vision
	 */
	public Cell(String cellType, double centerXLocation, double centerYLocation, double sideLength, String[] properties, Color[] colors, int initialState, double initialEnergyinput, boolean vision){
		myProperties = properties;
		currentState = initialState;
		futureState = currentState; // initialize futureState to be the same with currentState
		myChronon = 0;
		initialEnergy = initialEnergyinput;
		myEnergy = initialEnergy;
		myColors = colors;
		myCenterXLocation = centerXLocation;
		myCenterYLocation = centerYLocation;
		myType = cellType;
		mySideLength = sideLength;
		myVision = vision;
	}
	
	/**
	 * This method functions to check the adjacency between cells by comparing 
	 * the distance between their center locations. Given the locations are stored 
	 * as double and cannot be compared to each other, a small delta is used to 
	 * compare the absolute value of the difference between locations. 
	 * @param loc1
	 * @param loc2
	 * @return
	 */
	protected boolean locationMatch(double loc1, double loc2){
		return (Math.abs(loc1-loc2) < delta);
	}
	
	protected abstract void makePolygon();
	
	public void resetEnergy(){
		myEnergy = initialEnergy;
	}
	
	public void setEnergy(double value){
		myEnergy = value;
	}
	
	public void changeEnergy(double value){
		myEnergy = myEnergy + value;
	}
	
	public String[] showProperties(){
		return myProperties;
	}
	
	public Color[] showColors(){
		return myColors;
	}
	
	public abstract boolean checkTopAdjacency(Cell cell);
	
	public abstract boolean checkBotAdjacency(Cell cell);

	public abstract boolean checkLeftAdjacency(Cell cell);
	
	public abstract boolean checkRightAdjacency(Cell cell);
	
	public abstract boolean checkSideAdjacency(Cell cell);
	
	public abstract boolean checkDiagonalAdjacency(Cell cell);
	
	public boolean showVision(){
		return myVision;
	}
	
	public void setVision(boolean vis){
		myVision = vis;
	}
	
	public double showEnergy(){
		return myEnergy;
	}
	
	protected double showCenterYLoc(){
		return myCenterYLocation;
	}
	
	protected double showCenterXLoc(){
		return myCenterXLocation;
	}
	
	public void resetChronon(){
		myChronon = 0;
	}
	
	public Polygon showPolygon(){
		return myPolygon;
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
