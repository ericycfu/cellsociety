package cellsociety_team06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Simulation_Square extends Simulation{
	
	private String SQUARE = "square";	
	private double sidelength = celllength;
	Cell currentCell;

	public Simulation_Square(XMLReader reader, Group sceneroot){
		super(reader, sceneroot);
	}
	
	public void cellGenerator(){
		
		Color[] lifeColor = colorGnerator(COLORS);
		if (useProb == 0){
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					switch (cellParameters.size()){
						case 0:{
							currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j]);
						}
						case 1:{
							currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], Double.parseDouble(cellParameters.get(0)));
						}
						case 2:{
							boolean visual = (Integer.parseInt(cellParameters.get(1)) == 1);
							currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], Double.parseDouble(cellParameters.get(0)), visual);
						}
					}
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
					switch (cellParameters.size()){
					case 0:{
						currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, States.get(arranger));
					}
					case 1:{
						currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), Double.parseDouble(cellParameters.get(0)));
					}
					case 2:{
						boolean visual = (Integer.parseInt(cellParameters.get(1)) == 1);
						currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), Double.parseDouble(cellParameters.get(0)), visual);
					}
				}
					currentGrid.createCells(i, j, currentCell);
					Polygon cellVisual = currentCell.showPolygon();
					root.getChildren().add(cellVisual);
				}
			}
			
		}
		
	}
	
}
