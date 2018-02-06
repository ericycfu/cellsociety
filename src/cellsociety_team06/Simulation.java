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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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

public class Simulation extends Application{
	
	public static int celllength = 10;
	
	public static String SIMUL;
	
	public static int width;
	public static int height;
	public static double GAP;
	public static ArrayList<String> States = new ArrayList<String>();
	public static ArrayList<Float> Probabilities = new ArrayList<Float>();
	public static ArrayList<Float> CellParameters = new ArrayList<Float>();
	public static HashMap<String,Float> getProb;
	
	public static int MILLISECOND_DELAY = 1000 / 60;
	public static double SECOND_DELAY = 1.0 / 60;
	public static Paint BACKGROUND = Color.GREY;
	
	public static String TITLE = "CellSociety_team06";
	
	public static String filename = "test.txt";
	public static Group root;
	public Rectangle[][] thegrid;
	
	public static Grid lifeGrid;
	public static Calculator lifecalc;
	
	 
	public static String SimType;
	public static String SimTitle;
	public static String SimAuthors;
	public static XMLReader myReader;
	/*public static void filereader() throws IOException{
		
		String filepath = filename;
		File filename = new File(filepath);
		
		InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        
        line = br.readLine();
        SIMUL = line;
        line = br.readLine();
        width =Integer.parseInt(line);
        line = br.readLine();
        height = Integer.parseInt(line);
        
        line = br.readLine();
        String[] St = line.split(",");
        States = new ArrayList<String>();
        for (int i=0;i<St.length;i++){
        	States.add(St[i]);
        }
        
        line = br.readLine();
        String[] Prob = line.split(",");
        Probabilities = new ArrayList<Float>();
        for (int i=0;i<Prob.length;i++){
        	Probabilities.add(Float.parseFloat(Prob[i]));
        }
        
        getProb = new HashMap<String,Float>();
        for (int i=0;i<States.size();i++){
        	getProb.put(States.get(i), Probabilities.get(i));
        }
		
	}*/
	
	public void readFile(String thisfile) throws IOException, SAXException, ParserConfigurationException{
		myReader = new XMLReader(thisfile);
		myReader.read();
		//System.out.println(myReader.showbasicInfo().size());
		SimType = myReader.showbasicInfo().get(0);
		//System.out.println(SimType);
		SimTitle = myReader.showbasicInfo().get(1);
		SimAuthors = myReader.showbasicInfo().get(2);
		height = Integer.parseInt(myReader.showgridConfig().get(0));
		width = Integer.parseInt(myReader.showgridConfig().get(1));
		switch (SimType){
			case "Game of Life":{
				String[] myproperties = myReader.showglobalSettings().get(0).split(",");
				States = new ArrayList<String>(Arrays.asList(myproperties));
				//System.out.println(States.get(0));
				//System.out.println(States.get(1));
				Probabilities.add(Float.parseFloat(myReader.showgridConfig().get(2)));
				break;
			}
			case "Segregation":{
				String[] myproperties = myReader.showglobalSettings().get(0).split(",");
				States = new ArrayList<String>(Arrays.asList(myproperties));
				Probabilities.add(Float.parseFloat(myReader.showgridConfig().get(2)));
				Probabilities.add(Float.parseFloat(myReader.showgridConfig().get(3)));
				CellParameters.add(Float.parseFloat(myReader.showglobalSettings().get(1)));
				break;
			}
			case "Wator":{
				String[] myproperties = myReader.showglobalSettings().get(0).split(",");
				States = new ArrayList<String>(Arrays.asList(myproperties));
				Probabilities.add(Float.parseFloat(myReader.showgridConfig().get(2)));
				Probabilities.add(Float.parseFloat(myReader.showgridConfig().get(3)));
				CellParameters.add(Float.parseFloat(myReader.showglobalSettings().get(1)));
				CellParameters.add(Float.parseFloat(myReader.showglobalSettings().get(2)));
				CellParameters.add(Float.parseFloat(myReader.showglobalSettings().get(3)));
				break;
			}
			case "Fire":{
				String[] myproperties = myReader.showglobalSettings().get(0).split(",");
				States = new ArrayList<String>(Arrays.asList(myproperties));
				Probabilities.add(Float.parseFloat(myReader.showgridConfig().get(2)));
				Probabilities.add(Float.parseFloat(myReader.showgridConfig().get(3)));
				CellParameters.add(Float.parseFloat(myReader.showglobalSettings().get(1)));
				break;
			}
		}
	}
	@Override
    public void start(Stage primaryStage) throws Exception {
        readFile("lib/segregation.xml");
		primaryStage.setTitle("Test");
        
        Scene scene = creator(width * celllength + 100,height * celllength + 100,BACKGROUND);
        primaryStage.setScene(scene);
        primaryStage.show();

        String[] properties = new String[States.size()];
        for (int i=0;i<States.size();i++){
        	properties[i] = States.get(i);
        }
        
        switch (SimType) {
        
	        case "Game of Life":{
	         lifecalc = new Calculator_Life(properties);
	         lifeGrid = new Grid_Life(height, width, lifecalc);
	         break;
	        }
	        case "Fire":{
	         lifecalc = new Calculator_Fire(properties, CellParameters.get(0));
	         lifeGrid = new Grid_Fire(height, width, lifecalc);
	         break;
	        }
	        case "Wator":{
	         lifecalc = new Calculator_Wator(properties,CellParameters.get(2));
	         lifeGrid = new Grid_Wator(height,width,lifecalc,CellParameters.get(1));
	         break;
	        }
	        case "Segregation":{
	         lifecalc = new Calculator_Segregation(properties, 0.6);
	         System.out.println(CellParameters.get(0));
	         lifeGrid = new Grid_Segregation(height, width, lifecalc);
	         break;
	        }
       }

		
        Cell thiscell = new Cell(properties,0);
        switch (SimType){
	    	case "Game of Life":{
	    		for (int i=0;i<height;i++){
	    			for (int j=0;j<width;j++){
	    				if (thegrid[i][j].getFill() == Color.BLACK){
	    					thiscell = new Cell(properties, 0);
	    					lifeGrid.createCells(i, j, thiscell);
	    				} else {
	    					thiscell = new Cell(properties, 1);
	    					lifeGrid.createCells(i, j, thiscell);
	    				}
	    			}
	    		}
	    		break;
	    	}
	    	case "Fire":{
	            for (int i=0;i<height;i++){
	            	for (int j=0;j<width;j++){
	            		if (thegrid[i][j].getFill() == Color.GREEN){
	            			thiscell = new Cell(properties, 0);
	            			lifeGrid.createCells(i, j, thiscell);
	            		} else if (thegrid[i][j].getFill() == Color.RED){
	            			thiscell = new Cell(properties, 1);
	            			lifeGrid.createCells(i, j, thiscell);
	            		} else {
	            			thiscell = new Cell(properties, 2);
	            			lifeGrid.createCells(i, j, thiscell);
	            		}
	            	}
	            	}
	            break;
	        }
	    	case "Wator":{
	            for (int i=0;i<height;i++){
	            	for (int j=0;j<width;j++){
	            		if (thegrid[i][j].getFill() == Color.GREEN){
	            			thiscell = new Cell(properties, 0);
	            			lifeGrid.createCells(i, j, thiscell);
	            		} else if (thegrid[i][j].getFill() == Color.BLUE){
	            			thiscell = new Cell(properties, 1, CellParameters.get(0));
	            			lifeGrid.createCells(i, j, thiscell);
	            		} else {
	            			thiscell = new Cell(properties, 2);
	            			lifeGrid.createCells(i, j, thiscell);
	            		}
	            	}
	            	}
	            	break;
	           	}
	    	case "Segregation":{
	            for (int i=0;i<height;i++){
	            	for (int j=0;j<width;j++){
	            		if (thegrid[i][j].getFill() == Color.RED) {
	            			thiscell = new Cell(properties, 0);
	            			lifeGrid.createCells(i, j, thiscell);
	            		} else if (thegrid[i][j].getFill() == Color.BLUE){
	            			thiscell = new Cell(properties, 1);
	            			lifeGrid.createCells(i, j, thiscell);
	            		} else {
	            			thiscell = new Cell(properties, 2);
	            			lifeGrid.createCells(i, j, thiscell);
	            		}
	            	}
	            }
	            break;
	    	}
        }
		KeyFrame frame = new KeyFrame(Duration.millis(1000),
                e -> Step(lifeGrid, lifecalc));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
	}
	
	
	
	public Scene creator(int L, int W, Paint background){
		
		root = new Group();
		
		thegrid = new Rectangle[height][width];
		
		for (int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				Rectangle R = new Rectangle(i,j,celllength-1,celllength-1);
				R.setX(i * celllength + 50);
				R.setY(j * celllength + 50);
				thegrid[i][j] = R;
				double p = Math.random();
				switch (SimType){
					case "Game of Life":{
						if (p < Probabilities.get(0))
							R.setFill(Color.BLACK);
						else R.setFill(Color.WHITE);
						break;
					}
					case "Segregation":{
						if (p < Probabilities.get(0))
							R.setFill(Color.RED);
						else if (p < Probabilities.get(1)) 
							R.setFill(Color.BLUE);
						else R.setFill(Color.WHITE);
						break;
					}
					case "Wator":{
						if (p < Probabilities.get(0))
							R.setFill(Color.GREEN);
						else if (p < Probabilities.get(1)) 
							R.setFill(Color.BLUE);
						else R.setFill(Color.WHITE);
						break;
					}
					case "Fire":{
						if (p < Probabilities.get(1))
							R.setFill(Color.RED);
						else if ( Probabilities.get(1)<= p && p < Probabilities.get(0)) 
							R.setFill(Color.GREEN);
						else R.setFill(Color.YELLOW);
						break;
					}
				}
				
				
				root.getChildren().add(R);
			}
		}
		
		Scene newscene = new Scene(root, L, W, background);
		
		newscene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return newscene;
		
	}
	
	public void cellgenerator(int x, int y){
		
		Rectangle R = new Rectangle(x,y,celllength,celllength);
		R.setFill(Color.PLUM);
		root.getChildren().add(R);
		
	}
	
	
	
	public void Step(Grid myGrid, Calculator myCalc){
		
			myGrid.iterate();
			
			switch (SimType){
				case "Game of Life":{
					System.out.println(SimType);
					for (int i=0;i<height;i++){
						for (int j=0;j<width;j++){
							if (myGrid.getCell(i, j).showCurrentState()==0){
								thegrid[i][j].setFill(Color.BLACK);
							} 
							else {
								thegrid[i][j].setFill(Color.WHITE);
							}
						}
					}
					break;
				}
				case "Segregation":{
					for (int i=0;i<height; i++){
						for (int j=0;j<width;j++){
							if (myGrid.getCell(i, j).showCurrentState()==0){
								thegrid[i][j].setFill(Color.RED);
							} else if (myGrid.getCell(i, j).showCurrentState()==1){
								thegrid[i][j].setFill(Color.BLUE);
							} else {
								thegrid[i][j].setFill(Color.WHITE);
							}
						}
					}
					break;
				}
				case "Wator":{
					for (int i=0;i<height; i++){
						for (int j=0;j<width;j++){
							if (myGrid.getCell(i, j).showCurrentState()==0){
								thegrid[i][j].setFill(Color.GREEN);
							} else if (myGrid.getCell(i, j).showCurrentState()==1){
								thegrid[i][j].setFill(Color.BLUE);
							} else {
								thegrid[i][j].setFill(Color.WHITE);
							}
						}
					}
					break;
				}
				case "Fire":{
					for (int i=0;i<height; i++){
						for (int j=0;j<width;j++){
							if (myGrid.getCell(i, j).showCurrentState()==0){
								thegrid[i][j].setFill(Color.GREEN);
							} else if (myGrid.getCell(i, j).showCurrentState()==1){
								thegrid[i][j].setFill(Color.RED);
							} else {
								thegrid[i][j].setFill(Color.YELLOW);
							}
						}
					}
					break;
				}
			}
			
		
	}
	
	public static void handleKeyInput(KeyCode keyCode){
		
	}
	
	
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Application.launch(args);
    }
	
}
