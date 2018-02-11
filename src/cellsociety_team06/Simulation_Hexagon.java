package cellsociety_team06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Simulation_Hexagon extends Simulation{
	
	private String HEXAGON = "hexagon";	
	private boolean oddodd = true;
	Cell currentCell;

	public Simulation_Hexagon(XMLReader reader, Group sceneroot){
		super(reader, sceneroot);
	}
	
	public void cellGenerator(){
		
		Color[] lifeColor = colorGnerator(COLORS);
		if (useProb == 0){
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					if (oddodd){
						currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*celllength+100, j*1.5*celllength+50, celllength, properties, lifeColor, cellstates[i][j]);
					} else {
						currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*celllength+100-Math.sqrt(3)/3*celllength, j*1.5*celllength+50, celllength, properties, lifeColor, cellstates[i][j]);
					}
					Polygon cellVisual = currentCell.showPolygon();
					root.getChildren().add(cellVisual);
				}
				oddodd = !oddodd;
			}
		} else {
			ArrayList<Integer> States = new ArrayList<Integer>();
			for (int i=0;i<probabilities.size();i++){
				int number = (int) (probabilities.get(i) * height * width);
				for (int j=0;j<number;j++){
					States.add(i);
				}
			}
			Collections.shuffle(States);
			int arranger = 0;
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					if (oddodd){
						currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*celllength+100, j*1.5*celllength+50, celllength, properties, lifeColor, States.get(arranger));
					} else {
						currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*celllength+100-Math.sqrt(3)/3*celllength, j*1.5*celllength+50, celllength, properties, lifeColor, States.get(arranger));
					}
					Polygon cellVisual = currentCell.showPolygon();
					root.getChildren().add(cellVisual);
				}
				oddodd = !oddodd;
			}
			
		}
		
	}
	
}
