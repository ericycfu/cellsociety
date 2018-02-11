package cellsociety_team06;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.io.File;  
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Simulation{
	
	protected Grid currentGrid;
	protected Calculator currentCalculator;
	protected XMLReader thisreader;
	
	protected ArrayList<String> basicInfo = new ArrayList<String>();
	protected ArrayList<String> globalSettings = new ArrayList<String>();
	protected ArrayList<Integer> gridConfig = new ArrayList<Integer>();
	
	protected String mySimu;
	protected String Shape;
	
	protected int width;
	protected int height;
	protected double celllength;
	protected List<Double> probabilities;
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
		probabilities = thisreader.showmyPercentages();
		globalSettings = (ArrayList<String>) thisreader.showglobalSettings();
		gridConfig = (ArrayList<Integer>) thisreader.showgridConfig();
		
		properties = globalSettings.get(0).split(",");
		Shape = globalSettings.get(2);
		COLORS = globalSettings.get(1).split(",");
		Shape = globalSettings.get(2);
		useProb = Integer.parseInt(globalSettings.get(3));
		
		height = gridConfig.get(0);
		width = gridConfig.get(1);
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
	
	public void cellGenerator(){
		
	}
	
	public Grid getGrid(){
		return currentGrid;
	}
	
	
	
}
