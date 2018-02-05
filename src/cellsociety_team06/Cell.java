package cellsociety_team06;

public class Cell {

	private int currentState;
	private int futureState;
	private String[] myProperties;
	private double myChronon;
	private double myEnergy;
	
	public Cell(String[] properties, int initialState){
		myProperties = properties;
		currentState = initialState;
		futureState = currentState;
		myChronon = 0;
	}
	
	public Cell(String[] properties, int initialState, double initialEnergy){
		myProperties = properties;
		currentState = initialState;
		futureState = currentState; // initialize futureState to be the same with currentState
		myChronon = 0;
		myEnergy = initialEnergy;
	}
	
	public void setEnergy(double value){
		myEnergy = value;
	}
	
	public void changeEnergy(double value){
		myEnergy = myEnergy + value;
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
	}
	
	public void setFutureState(int nextState){
		futureState = nextState;
	}
	
}
