package cellsociety_team06;

public class Cell {
    
	private int currentState;
	private int futureState;
	private String[] myProperties;
	
	public Cell(String[] properties, int initialState){
		myProperties = properties;
		currentState = initialState;
	}
	
	public int showCurrentState(){
		return currentState;
	}
	
	public String showCurrentProperty(){
		return myProperties[currentState];
	}
	
	public void update(){
		currentState = futureState;
	}
	
	public void setFutureState(int nextState){
		futureState = nextState;
	}
	

}
