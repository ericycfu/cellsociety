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
	private double sidelength = celllength / Math.sqrt(3);

	public Simulation_Hexagon(XMLReader reader, Group sceneroot){
		super(reader, sceneroot);
	}
	
	public void cellGenerator(){
		
		Color[] lifeColor = colorGnerator(COLORS);
		if (useProb == 0){
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					if (oddodd){
						switch (cellParameters.size()){
							case 0:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100, j*1.5*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j]);
							}
							case 1:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100, j*1.5*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], cellParameters.get(0));
							}
							case 2:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100, j*1.5*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], cellParameters.get(0), cellParameters.get(1));
							}
						}
					} else {
						switch (cellParameters.size()){
							case 0:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100-Math.sqrt(3)/3*sidelength, j*1.5*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j]);
							}
							case 1:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100-Math.sqrt(3)/3*sidelength, j*1.5*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], cellParameters.get(0));
							}
							case 2:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100-Math.sqrt(3)/3*sidelength, j*1.5*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], cellParameters.get(0), cellParameters.get(1));
							}
						}
					}
					currentGrid.createCells(i, j, currentCell);
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
			for (int i = States.size(); i < height*width; i++){
				States.add(probabilities.size()-1);
			}
			Collections.shuffle(States);
			int arranger = 0;
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					if (oddodd){
						switch (cellParameters.size()){
							case 0:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100, j*1.5*sidelength+50, sidelength, properties, lifeColor, States.get(arranger));
							}
							case 1:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100, j*1.5*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), cellParameters.get(0));
							}
							case 2:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100, j*1.5*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), cellParameters.get(0), cellParameters.get(1));
							}
						}
					} else {
						switch (cellParameters.size()){
							case 0:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100-Math.sqrt(3)/3*sidelength, j*1.5*sidelength+50, sidelength, properties, lifeColor, States.get(arranger));
							}
							case 1:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100-Math.sqrt(3)/3*sidelength, j*1.5*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), cellParameters.get(0));
							}
							case 2:{
								currentCell = new Cell_Hexagon(HEXAGON, i*Math.sqrt(3)*sidelength+100-Math.sqrt(3)/3*sidelength, j*1.5*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), cellParameters.get(0), cellParameters.get(1));
							}
						}
					}
					currentGrid.createCells(i, j, currentCell);
					Polygon cellVisual = currentCell.showPolygon();
					root.getChildren().add(cellVisual);
				}
				oddodd = !oddodd;
			}
			
		}
		
	}
	
}
