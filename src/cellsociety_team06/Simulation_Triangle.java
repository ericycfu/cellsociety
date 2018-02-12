package cellsociety_team06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Simulation_Triangle extends Simulation{
	
	private String TRIANGLE = "triangle";	
	private boolean upup = true;
	Cell currentCell;
	private double sidelength = celllength;

	public Simulation_Triangle(XMLReader reader, Group sceneroot){
		super(reader, sceneroot);
	}
	
	@Override
	protected void cellGenerator(){
		
		Color[] lifeColor = colorGnerator(COLORS);
		if (useProb == 0){
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					int index = i+j;
					upup = (index % 2 == 0);
					switch (cellParameters.size()){
						case 0:{
							currentCell = new Cell_Triangle(TRIANGLE, j*0.5*sidelength+100+celllength/2, i*Math.sqrt(3)/2*sidelength+50+celllength/2, sidelength, properties, lifeColor, cellstates[i][j], upup);
							break;
						}
						case 1:{
							currentCell = new Cell_Triangle(TRIANGLE, j*0.5*sidelength+100+celllength/2, i*Math.sqrt(3)/2*sidelength+50+celllength/2, sidelength, properties, lifeColor, cellstates[i][j], Double.parseDouble(cellParameters.get(0)), upup);
							break;
						}
						case 2:{
							double visual = Double.parseDouble(cellParameters.get(1));
							if (Math.random()<visual){
								currentCell = new Cell_Triangle(TRIANGLE, j*sidelength+100+celllength/2, i*sidelength+50+celllength/2, sidelength, properties, lifeColor, cellstates[i][j], Double.parseDouble(cellParameters.get(0)), true);
							}
							else {
								currentCell = new Cell_Triangle(TRIANGLE, j*sidelength+100+celllength/2, i*sidelength+50+celllength/2, sidelength, properties, lifeColor, cellstates[i][j], Double.parseDouble(cellParameters.get(0)), false);
							}
							break;
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
				double prob = Double.parseDouble(probabilities.get(i));
				int number = (int) (prob * height * width);
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
					switch (cellParameters.size()){
					case 0:{
						currentCell = new Cell_Triangle(TRIANGLE, j*0.5*sidelength+100+celllength/2, i*Math.sqrt(3)/2*sidelength+50+celllength/2, sidelength, properties, lifeColor, States.get(arranger), upup);
						break;
					}
					case 1:{
						currentCell = new Cell_Triangle(TRIANGLE, j*0.5*sidelength+100+celllength/2, i*Math.sqrt(3)/2*sidelength+50+celllength/2, sidelength, properties, lifeColor, States.get(arranger), Double.parseDouble(cellParameters.get(0)), upup);
						break;
					}
					case 2:{
						double visual = Double.parseDouble(cellParameters.get(1));
						if (Math.random()<visual){
							currentCell = new Cell_Triangle(TRIANGLE, j*sidelength+100+celllength/2, i*sidelength+50+celllength/2, sidelength, properties, lifeColor, States.get(arranger), Double.parseDouble(cellParameters.get(0)), true);
						}
						else {
							currentCell = new Cell_Triangle(TRIANGLE, j*sidelength+100+celllength/2, i*sidelength+50+celllength/2, sidelength, properties, lifeColor, States.get(arranger), Double.parseDouble(cellParameters.get(0)), false);
						}
						break;
					}
				}
					currentGrid.createCells(i, j, currentCell);
					Polygon cellVisual = currentCell.showPolygon();
					root.getChildren().add(cellVisual);
					arranger++;
				}
			}
			
		}
		
	}
	
}
