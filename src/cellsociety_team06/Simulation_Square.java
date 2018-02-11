package cellsociety_team06;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	@Override
	public void cellGenerator(){
		
		Color[] lifeColor = colorGnerator(COLORS);
		if (useProb == 0){
			//System.out.println(useProb);
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					switch (cellParameters.size()){
						case 0:{
							//System.out.println(cellstates[i][j]);
							currentCell = new Cell_Square(SQUARE, j*sidelength+100, i*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j]);
							break;
						}
						case 1:{
							//System.out.println(cellParameters.size());
							//System.out.println(Arrays.toString(cellstates[0]));
							//System.out.println(Arrays.toString(cellstates[1]));
							//System.out.println(Arrays.toString(cellstates[2]));
							currentCell = new Cell_Square(SQUARE, j*sidelength+100, i*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], Double.parseDouble(cellParameters.get(0)));
							
							break;
						}
						case 2:{
							boolean visual = (Integer.parseInt(cellParameters.get(1)) == 1);
							currentCell = new Cell_Square(SQUARE, j*sidelength+100, i*sidelength+50, sidelength, properties, lifeColor, cellstates[i][j], Double.parseDouble(cellParameters.get(0)), visual);
							break;
						}
					}
					currentGrid.createCells(i, j, currentCell);
					//System.out.println(currentGrid.getCell(0, 0).showCurrentProperty());
					root.getChildren().add(currentCell.showPolygon());
				}
			}
		} else {
			ArrayList<Integer> States = new ArrayList<Integer>();
			System.out.println(probabilities.size());
			for (int i=0; i<probabilities.size(); i++){
				//System.out.println(probabilities.size());
				double prob = Double.parseDouble(probabilities.get(i));
				int number = (int) (prob * height * width);
				//System.out.println(number);
				for (int j=0;j<number;j++){
					States.add(i);
					//System.out.println(States.toString());
				}
			}
			//System.out.println(States.size());
			System.out.println("!!!");
			for (int i = States.size(); i < height*width; i++){
				States.add(probabilities.size()-1);
			}
			Collections.shuffle(States);
			int arranger = 0;
			//System.out.println(cellParameters.size());
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					switch (cellParameters.size()){
					case 0:{
						
						currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, States.get(arranger));
						//System.out.println(currentCell.showCurrentProperty());
						break;
					}
					case 1:{
						currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), Double.parseDouble(cellParameters.get(0)));
						break;
					}
					case 2:{
						boolean visual = (Integer.parseInt(cellParameters.get(1)) == 1);
						
						currentCell = new Cell_Square(SQUARE, i*sidelength+100, j*sidelength+50, sidelength, properties, lifeColor, States.get(arranger), Double.parseDouble(cellParameters.get(0)), visual);
						System.out.println(currentCell.showCurrentProperty());
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
