package cellsociety_team06;


import javafx.scene.Group;

import javafx.scene.paint.Color;

import java.util.*;



public abstract class Simulation{
	
	protected Grid currentGrid;
	protected Calculator currentCalculator;
	protected XMLReader thisreader;
	
	protected List<String> basicInfo = new ArrayList<String>();
	protected List<String> globalSettings = new ArrayList<String>();
	protected List<Integer> gridConfig = new ArrayList<Integer>();
	
	protected String mySimu;
	protected String Shape;
	
	protected int width;
	protected int height;
	protected double celllength;
	protected List<String> probabilities;
	protected String[] properties;
	protected String[] COLORS;
	protected int useProb; //0 for no, 1 for yes
	protected List<String> cellParameters = new ArrayList<String>();
	protected List<String> gridParameters = new ArrayList<String>();
	protected List<String> calcParameters = new ArrayList<String>();
	protected int[][] cellstates;
	
	protected Group root;
	
	public Simulation(XMLReader reader, Group sceneroot){
		
		root = sceneroot;
		
		thisreader = reader;
		mySimu = thisreader.getSimType();
		basicInfo = thisreader.showbasicInfo();
		probabilities = thisreader.showmyPercentages();
		globalSettings = thisreader.showglobalSettings();
		gridConfig = thisreader.showgridConfig();
		
		properties = globalSettings.get(0).split(",");
		Shape = globalSettings.get(2);
		COLORS = globalSettings.get(1).split(",");
		Shape = globalSettings.get(2);
		useProb = Integer.parseInt(globalSettings.get(3));
		
		height = gridConfig.get(0);
		width = gridConfig.get(1);
		cellstates = thisreader.showmyCells();
		celllength = 500/width;
		cellParameters = thisreader.showcellParameters();
		
		gridParameters = thisreader.showgridParameters();
		calcParameters = thisreader.showcalculatorParameters();
	}
	
	public Color[] colorGnerator(String[] ColorString){
		
		Color[] cellColors = new Color[COLORS.length];
		for (int i=0;i<COLORS.length;i++){
			cellColors[i] = Color.web(COLORS[i]);
		}
		return cellColors;
		
	}
	
	public void gridGenerator(){
		
		switch (mySimu) {
        	
	        case "Game of Life":{
	        	System.out.println("GOL create");
	        	currentCalculator = new Calculator_Life(properties);
	        	currentGrid = new Grid_Life(height, width, currentCalculator);
	        	break;
	        }
	        case "Fire":{
	        	currentCalculator = new Calculator_Fire(properties, Float.parseFloat(calcParameters.get(0)));
	        	currentGrid = new Grid_Fire(height, width, currentCalculator);
	        	break;
	        }
	        case "Wator":{
	        	currentCalculator = new Calculator_Wator(properties,Float.parseFloat(calcParameters.get(0)));
	        	currentGrid = new Grid_Wator(height,width,currentCalculator);
	        	break;
	        }
	        case "Segregation":{
	        	currentCalculator = new Calculator_Segregation(properties, Float.parseFloat(calcParameters.get(0)));
	        	currentGrid = new Grid_Segregation(height, width, currentCalculator);
	        	break;
	        }
	        
		}
		cellGenerator();
	}
	
	public abstract void cellGenerator();
	
	public Calculator getCalc(){
		return currentCalculator;
	}
	
	public Grid getGrid(){
		return currentGrid;
	}
	
	
	
}
