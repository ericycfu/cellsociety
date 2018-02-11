package cellsociety_team06;

import org.w3c.dom.Document;

public abstract class Simulation{
	
	protected Grid currentGrid;
	protected Calculator currentCalculator;
	protected XMLReader thisreader;
	
	protected ArrayList<String> basicInfo = new ArrayList<String>();
	protected ArrayList<String> globalSettings = new ArrayList<String>();
	protected ArrayList<String> gridConfig = new ArrayList<String>();
	
	protected String mySimu;
	protected String Shape;
	
	protected int width;
	protected int height;
	protected double celllength;
	protected double[] probabilities;
	protected String[] properties;
	protected String[] COLORS;
	protected int useProb; //0 for no, 1 for yes
	protected ArrayList<Float> cellParameters = new ArrayList<Float>();
	protected ArrayList<Float> gridParameters = new ArrayList<Float>();
	protected int[][] cellstates;
	
	protected Group root;
	
	public Simulation(XMLReader reader, Group sceneroot){
		
		root = sceneroot;
		
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
	
	
	
}
