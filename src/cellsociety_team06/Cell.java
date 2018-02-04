package cellsociety_team06;

public class Cell {
	private int currentState;
	private int futureState;
	private String[] myPropertys;
	
	public Cell(String[] propertys, int initialState){
		myPropertys = propertys;
		currentState = initialState;
	}
	
	public int showCurrentState(){
		return currentState;
	}
	
	public String showCurrentProperty(){
		return myPropertys[currentState];
	}
	
	public void update(){
		currentState = futureState;
	}
	
	public void setFutureState(int nextState){
		futureState = nextState;
	}
	
}
