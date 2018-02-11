package cellsociety_team06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Simulation_Triangle extends Simulation{
	
	private String TRANGLE = "trangle";	
	private boolean upup = true;
	private double sidelength = celllength * 2;

	public Simulation_Triangle(XMLReader reader, Group sceneroot){
		super(reader, sceneroot);
	}
	
	public void cellGenerator(){
		
		Color[] lifeColor = colorGnerator(COLORS);
		if (useProb == 0){
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					int index = i+j;
					upup = (index % 2 == 0);
					Cell currentCell = new Cell_Triangle(TRANGLE, i*0.5*sidelength+100, j*Math.sqrt(3)/2*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], upup);
					currentGrid.createCells(i, j, currentCell);
					Polygon cellVisual = currentCell.showPolygon();
					root.getChildren().add(cellVisual);
				}
			}
		} else {
			ArrayList<Integer> States = new ArrayList<Integer>();
			for (int i=0;i<probabilities.size();i++){
				int number = (int) (probabilities.get(i) * height * width);
				for (int j=0;j<number;j++){
					States.add(i);
				}
			}
			for (int i = States.size(); i < height*width; i++){
				States.add(probabilities.size()-1);
			}
			Collections.shuffle(States);
			int arranger = 0;
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					int index = i+j;
					upup = (index % 2 == 0);
					Cell currentCell = new Cell_Triangle(TRANGLE, i*0.5*sidelength+100, j*Math.sqrt(3)/2*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), upup);
					currentGrid.createCells(i, j, currentCell);
					Polygon cellVisual = currentCell.showPolygon();
					root.getChildren().add(cellVisual);
				}
			}
			
		}
		
	}
	
}
