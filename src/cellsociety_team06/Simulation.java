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

public class Simulation extends Application{
	
	private int celllength = 10;
	private int buttonwidth = 40;
	private int buttonheight = 20;
	
	private String SIMUL;
	private Stage myPrimaryStage;
	private int width;
	private int height;
	private ArrayList<String> States = new ArrayList<String>();
	private ArrayList<Float> Probabilities = new ArrayList<Float>();
	private ArrayList<Float> CellParameters = new ArrayList<Float>();
	
	private int MILLISECOND_DELAY = 1000 / 60;
	private double SECOND_DELAY = 1.0 / 60;
	private Paint BACKGROUND = Color.GREY;
	
	private String TITLE = "CellSociety_team06";
	
	private String filename;
	private Group root = new Group();
	private Rectangle[][] thegrid;
	
	private Grid lifeGrid;
	private Calculator lifecalc;
	
	private FileChooser.ExtensionFilter extFilter;
	private String SimType;
	private String SimTitle;
	private String SimAuthors;
	private XMLReader myReader;
	
	public static int timer = 0;
	public static boolean pauser = false;
	 
	private Button PAUSE = new Button("Pause");
	private Button STEP = new Button("Step");
	private Button FINISH = new Button("Finish");
	private Button EXIT = new Button("EXIT");
	private Button SWITCH = new Button("Switch");
	private TilePane tilePane = new TilePane();
	private Button FASTER = new Button("Faster");
	private Button SLOWER = new Button("Slower");
	private TilePane speeder = new TilePane();
	private Label timedisplay;
	private FileChooser fileChooser;
	private String[] properties ;
	private Cell thiscell;
	private Scene scene;

	public void readFile(String thisfile) throws IOException, SAXException, ParserConfigurationException{
		myReader = new XMLReader(thisfile);
		myReader.read();
		//System.out.println(myReader.showbasicInfo().size());
		SimType = myReader.showbasicInfo().get(0);
		System.out.println(SimType);
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
	private void chooseFile(Stage primaryStage){
		fileChooser = new FileChooser();
        extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        filename = file.getName();
        myPrimaryStage = primaryStage;
	}
	
	private void cellGenerate(){
		properties = new String[States.size()];
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
	}
	@Override
    public void start(Stage primaryStage) throws Exception {
		chooseFile(primaryStage);
        readFile("lib/"+filename);
		pauser = false;
		celllength = 500/width;
		
        primaryStage.setTitle(SIMUL);
        scene = sceneCreator(900,900,BACKGROUND);
        primaryStage.setScene(scene);
        primaryStage.show();

        cellGenerate();
        PAUSE.setOnAction(value ->  {
            pauser = true;
            PAUSE.setText("Resume");
            pausing();
           });
        
        SWITCH.setOnAction(value ->  {
        	   pauser = true;
        	   FileChooser newfileChooser = new FileChooser();
        	            FileChooser.ExtensionFilter newextFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        	            fileChooser.getExtensionFilters().add(extFilter);
        	            File newfile = fileChooser.showOpenDialog(primaryStage);
        	            filename = newfile.getPath();
        	            try {
        	            	readFile(filename);
        	            	} catch (IOException e1) {
        	            		e1.printStackTrace();
        	            	} catch (SAXException e1) {
        	            	} catch (ParserConfigurationException e1) {
        	            	}
        	            
        	            Scene newscene = sceneCreator(900,900,BACKGROUND);
        	            myPrimaryStage.setScene(newscene);
        	            myPrimaryStage.show();
        	            cellGenerate();
        	            
        	        });
        
     
     FINISH.setOnAction(value ->  {
      pauser = true;
      PAUSE.setDisable(true);
      FINISH.setText("Fnished");
      FINISH.setDisable(true);
           });
     
     STEP.setOnAction(value ->  {
      pauser = true;
      mover(lifeGrid,lifecalc);
           });
     
     EXIT.setOnAction(value ->  {
            primaryStage.close();
           });
		KeyFrame frame = new KeyFrame(Duration.millis(1000),
                e -> Step(lifeGrid, lifecalc));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		FASTER.setOnAction(value ->  {
			   animation.setRate(animation.getRate() * 2);
			        });
			  
			  SLOWER.setOnAction(value ->  {
			   animation.setRate(animation.getRate() * 0.5);
			        });
		
	}
	
	public void pausing(){
		  pauser = true;
		  PAUSE.setOnAction(valuevalue ->  {
		   resuming();
		        });
		  PAUSE.setText("Resume");
		  STEP.setDisable(false);
		  FASTER.setDisable(true);
		  SLOWER.setDisable(true);
		 }
		 
		 public void resuming(){
		  pauser = false;
		  PAUSE.setOnAction(valuevalue ->  {
		   pausing();
		        });
		  PAUSE.setText("Pause");
		  STEP.setDisable(true);
		  FASTER.setDisable(false);
		  SLOWER.setDisable(false);
		 }
	
	public Scene sceneCreator(int L, int W, Paint background){
		pauser = false;
		root = new Group();
		thegrid = new Rectangle[height][width];
		
		for (int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				Rectangle R = new Rectangle(i,j,celllength-1,celllength-1);
				R.setX(i * celllength + 100);
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
		
		PAUSE.setStyle("-fx-text-fill: #0000ff; -fx-border-color: #0000ff; -fx-border-width: 1px;");
		PAUSE.setMinWidth(80);
		FINISH.setStyle("-fx-text-fill: #0000ff; -fx-border-color: #0000ff; -fx-border-width: 1px;");
		FINISH.setMinWidth(80);
		EXIT.setStyle("-fx-text-fill: #8B0000; -fx-border-color: #8B0000; -fx-border-width: 5px;");
		EXIT.setMinWidth(80);
		SWITCH.setStyle("-fx-text-fill: #0000ff; -fx-border-color: #0000ff; -fx-border-width: 1px;");
		SWITCH.setMinWidth(80);
		STEP.setStyle("-fx-text-fill: #228B22; -fx-border-color: #228B22; -fx-border-width: 2px;");
		STEP.setMinWidth(80);
		
		tilePane = new TilePane();
		tilePane.getChildren().add(PAUSE);
		tilePane.getChildren().add(FINISH);
		tilePane.getChildren().add(STEP);
		tilePane.getChildren().add(SWITCH);
		tilePane.getChildren().add(EXIT);
		        
		STEP.setDisable(true);
		        
		tilePane.setHgap(70);
		tilePane.setVgap(10);
		tilePane.setLayoutX(50);
		tilePane.setLayoutY(600);
		        
		root.getChildren().add(tilePane);
		        
		FASTER.setStyle("-fx-text-fill: #ff4500; -fx-border-color: #ff4500; -fx-border-width: 1px;");
		SLOWER.setStyle("-fx-text-fill: #ff4500; -fx-border-color: #ff4500; -fx-border-width: 1px;");
		FASTER.setMinSize(100, 50);
		SLOWER.setMinSize(100, 50);
		thegrid = new Rectangle[height][width];
		
		for (int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				Rectangle R = new Rectangle(i,j,celllength-1,celllength-1);
				R.setX(i * celllength + 100);
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
		
		PAUSE.setStyle("-fx-text-fill: #0000ff; -fx-border-color: #0000ff; -fx-border-width: 1px;");
		PAUSE.setMinWidth(80);
		FINISH.setStyle("-fx-text-fill: #0000ff; -fx-border-color: #0000ff; -fx-border-width: 1px;");
		FINISH.setMinWidth(80);
		EXIT.setStyle("-fx-text-fill: #8B0000; -fx-border-color: #8B0000; -fx-border-width: 5px;");
		EXIT.setMinWidth(80);
		SWITCH.setStyle("-fx-text-fill: #0000ff; -fx-border-color: #0000ff; -fx-border-width: 1px;");
		SWITCH.setMinWidth(80);
		STEP.setStyle("-fx-text-fill: #228B22; -fx-border-color: #228B22; -fx-border-width: 2px;");
		STEP.setMinWidth(80);
		
		tilePane = new TilePane();
		tilePane.getChildren().add(PAUSE);
		tilePane.getChildren().add(FINISH);
		tilePane.getChildren().add(STEP);
		tilePane.getChildren().add(SWITCH);
		tilePane.getChildren().add(EXIT);
		        
		STEP.setDisable(true);
		        
		tilePane.setHgap(70);
		tilePane.setVgap(10);
		tilePane.setLayoutX(50);
		tilePane.setLayoutY(600);
		        
		root.getChildren().add(tilePane);
		        
		FASTER.setStyle("-fx-text-fill: #ff4500; -fx-border-color: #ff4500; -fx-border-width: 1px;");
		SLOWER.setStyle("-fx-text-fill: #ff4500; -fx-border-color: #ff4500; -fx-border-width: 1px;");
		FASTER.setMinSize(100, 50);
		SLOWER.setMinSize(100, 50);
		speeder = new TilePane();
		speeder.getChildren().add(FASTER);
		speeder.getChildren().add(SLOWER);
		speeder.setHgap(80);
		        
		speeder.setLayoutX(100);
		speeder.setLayoutY(700);
		        
		root.getChildren().add(speeder);
		        
		timedisplay = new Label("Frame passed: " + Integer.toString(timer));
		timedisplay.setLayoutX(width * celllength - 20);
		timedisplay.setLayoutY(20);
		timedisplay.setStyle("-fx-text-fill: #ffffff");
		root.getChildren().add(timedisplay);
		Scene newscene = new Scene(root, L, W, background);
		
		newscene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return newscene;
		
	}
	
	/*public void cellgenerator(int x, int y){
<<<<<<< HEAD
		
		Rectangle R = new Rectangle(x,y,celllength,celllength);
		R.setFill(Color.PLUM);
		root.getChildren().add(R);
		
	}*/
	
	
	public void mover(Grid myGrid, Calculator myCalc){
		myGrid.iterate();
		Rectangle R = new Rectangle(x,y,celllength,celllength);
		R.setFill(Color.PLUM);
		root.getChildren().add(R);

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
		timer++;
		timedisplay.setText("Frame passed: " + Integer.toString(timer));

	}
	
	public void Step(Grid myGrid, Calculator myCalc){
		if (pauser==false){
		mover(myGrid,myCalc);
		}
	}
	
	public static void handleKeyInput(KeyCode keyCode){
		
	}
	
	
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Application.launch(args);
    }
	
}
