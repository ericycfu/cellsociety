package cellsociety_team06;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.*;

import org.w3c.dom.Document;

public abstract class Simulation{
	
	private Grid currentGrid;
	private Calculator currentCalculator;
	private XMLReader thisreader;
	
	private ArrayList<String> basicInfo = new ArrayList<String>();
	private ArrayList<String> globalSettings = new ArrayList<String>();
	private ArrayList<String> gridConfig = new ArrayList<String>();
	
	private String mySimu;
	private String Shape;
	
	private int width;
	private int height;
	private double celllength;
	private float[] probabilities;
	private String[] properties;
	private String[] COLORS;
	private int useProb; //0 for no, 1 for yes
	private ArrayList<Float> cellParameters = new ArrayList<Float>();
	private ArrayList<Float> gridParameters = new ArrayList<Float>();
	private int[][] cellstates;
	
	
	public Simulation(XMLReader reader, int gridwidth, int gridheight, double theCelllength){
		thisreader = reader;
		mySimu = thisreader.getSimType();
		basicInfo = (ArrayList<String>) thisreader.showbasicInfo();
		//probabilities = thisreader.getProbs;
		globalSettings = (ArrayList<String>) thisreader.showglobalSettings();
		gridConfig = (ArrayList<String>) thisreader.showgridConfig();
		//Shape = thisreader.getShape;
		
		properties = globalSettings.get(0).split(",");
		COLORS = globalSettings.get(1).split(",");
		Shape = globalSettings.get(2);
		useProb = Integer.parseInt(globalSettings.get(3));
		
		height = Integer.parseInt(gridConfig.get(0));
		width = Integer.parseInt(gridConfig.get(1));
	}
	
	public void gridGenerator(){
		
		switch (mySimu) {
        
	        case "Game of Life":{
	        	currentCalculator = new Calculator_Life(properties);
	        	currentGrid = new Grid_Life(height, width, currentCalculator);
	        	break;
	        }
	        case "Fire":{
	        	currentCalculator = new Calculator_Fire(properties, cellParameters.get(0));
	        	currentGrid = new Grid_Fire(height, width, currentCalculator);
	        	break;
	        }
	        case "Wator":{
	        	currentCalculator = new Calculator_Wator(properties,cellParameters.get(0));
	        	currentGrid = new Grid_Wator(height,width,currentCalculator);
	        	break;
	        }
	        case "Segregation":{
	        	currentCalculator = new Calculator_Segregation(properties, cellParameters.get(0));
	        	currentGrid = new Grid_Segregation(height, width, currentCalculator);
	        	break;
	        }
	        
		}
		
	}
	
	public void cellGenerator(){
		
		switch (SimType){
    	case "Game of Life":{
    		Color[] LIVES = new Color[2];
    		LIVES[0] = Color.BLACK;
    		LIVES[1] = Color.WHITE;
    		for (int i=0;i<height;i++){
    			for (int j=0;j<width;j++){
    				if (thegrid[i][j].getFill() == Color.BLACK){
    					thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, LIVES, 1);
    					lifeGrid.createCells(i, j, thiscell);
    				} else {
    					thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, LIVES, 0);
    					lifeGrid.createCells(i, j, thiscell);
    				}
    			}
    		}
    		break;
    	}
    	case "Fire":{
    		Color[] FIRES = new Color[3];
    		FIRES[0] = Color.GREEN;
    		FIRES[1] = Color.RED;
    		FIRES[2] = Color.YELLOW;
            for (int i=0;i<height;i++){
            	for (int j=0;j<width;j++){
            		if (thegrid[i][j].getFill() == Color.GREEN){
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, FIRES, 0);
            			lifeGrid.createCells(i, j, thiscell);
            		} else if (thegrid[i][j].getFill() == Color.RED){
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, FIRES, 1);
            			lifeGrid.createCells(i, j, thiscell);
            		} else {
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, FIRES, 2);
            			lifeGrid.createCells(i, j, thiscell);
            		}
            	}
            	}
            break;
        }
    	case "Wator":{
    		Color[] WATORS = new Color[3];
    		WATORS[0] = Color.GREEN;
    		WATORS[1] = Color.BLUE;
    		WATORS[2] = Color.WHITE;
            for (int i=0;i<height;i++){
            	for (int j=0;j<width;j++){
            		if (thegrid[i][j].getFill() == Color.GREEN){
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, WATORS, 0);
            			lifeGrid.createCells(i, j, thiscell);
            		} else if (thegrid[i][j].getFill() == Color.BLUE){
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, WATORS, 1);
            			lifeGrid.createCells(i, j, thiscell);
            		} else {
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, WATORS, 2);
            			lifeGrid.createCells(i, j, thiscell);
            		}
            	}
            	}
            	break;
           	}
    	case "Segregation":{
    		Color[] SEGS = new Color[3];
    		SEGS[0] = Color.RED;
    		SEGS[1] = Color.BLUE;
    		SEGS[2] = Color.WHITE;
            for (int i=0;i<height;i++){
            	for (int j=0;j<width;j++){
            		if (thegrid[i][j].getFill() == Color.RED) {
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, SEGS, 0);
            			lifeGrid.createCells(i, j, thiscell);
            		} else if (thegrid[i][j].getFill() == Color.BLUE){
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, SEGS, 1);
            			lifeGrid.createCells(i, j, thiscell);
            		} else {
            			thiscell = new Cell_Square(cellType, i*celllength+100, j*celllength+50, celllength, properties, SEGS, 2);
            			lifeGrid.createCells(i, j, thiscell);
            		}
            	}
            }
            break;
    	}
    }
		
	}
	
}
