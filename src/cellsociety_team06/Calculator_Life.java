package cellsociety_team06;

import java.util.ArrayList;

public class Calculator_Life extends Calculator{
	
	public Calculator_Life(String[] propertys) {
		super(propertys);
		// TODO Auto-generated constructor stub
	}
	
	public Calculator_Life(String[] properties, double parameter) {
		super(properties, parameter);
		// TODO Auto-generated constructor stub
	}
	
	public Calculator_Life() {
		
	}
	
	public double calculation(ArrayList<Cell> relatedCells, Cell centerCell){
		int reviveCondition = 0;
		for (Cell c : relatedCells)
			if (c.showCurrentProperty().equals("Live"))
				reviveCondition++;

		if (centerCell.showCurrentProperty().equals("Dead")){
			if (reviveCondition==3)
				return 1; // centerCell.setFutureState(getState("Live"));
		}
		else { //current property must be "Live"
			if (reviveCondition<=2)
				return 0; // centerCell.setFutureState(getState("Dead"));
			else if (reviveCondition==2 || reviveCondition==3)
				return 1; // centerCell.setFutureState(getState("Live"));
			else if (reviveCondition>3)
				return 0; // centerCell.setFutureState(getState("Dead"));
		}
		
		return 0;
	};
	
}
