package cellsociety_team06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Simulation_Square extends Simulation{
	
	private String SQUARE;	

	public Simulation_Square(XMLReader reader, Group sceneroot){
		super(reader, sceneroot);
	}
	
	public void cellGenerator(){
		
		Color[] lifeColor = colorGnerator(COLORS);
		if (useProb == 0){
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					Cell currentCell = new Cell_Square(SQUARE, i*celllength+100, j*celllength+50, celllength, properties, lifeColor, cellstates[i][j]);
					//Rectangle cellVisual = currentCell.showPolygon();
					//root.getChildren().add(cellVisual);
				}
			}
		} else {
			ArrayList<Integer> States = new ArrayList<Integer>();
			for (int i=0;i<probabilities.length;i++){
				int number = (int) (probabilities[i] * height * width);
				for (int j=0;j<number;j++){
					States.add(i);
				}
			}
			Collections.shuffle(States);
			int arranger = 0;
			for (int i=0;i<height;i++){
				for (int j=0;j<4;j++){
					Cell currentCell = new Cell_Square(SQUARE, i*celllength+100, j*celllength+50, celllength, properties, lifeColor, States.get(arranger));
					//Rectangle cellVisual = currentCell.showPolygon();
					//root.getChildren().add(cellVisual);
				}
			}
			
		}
		
	}
	
}
