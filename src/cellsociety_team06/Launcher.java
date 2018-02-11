package cellsociety_team06;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Launcher extends Application{
	
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
	
	private String cellType;
	private String shape;
	
	private boolean whetherfix;
	private int[][] gridGenerator;

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Application.launch(args);
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
	
	public static void handleKeyInput(KeyCode keyCode){
		
	}
	
	
	
	private void chooseFile(Stage primaryStage){
		fileChooser = new FileChooser();
        extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        filename = file.getName();
        myPrimaryStage = primaryStage;
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
	
	public void readFile(String thisfile) throws IOException, SAXException, ParserConfigurationException{
		myReader = new XMLReader(thisfile);
		myReader.read();
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
	
	public void Step(Grid myGrid, Calculator myCalc){
		if (pauser==false){
		mover(myGrid,myCalc);
		}
	}
	
	public void mover(Grid myGrid, Calculator myCalc){
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
		timer++;
		timedisplay.setText("Frame passed: " + Integer.toString(timer));

	}
	
}
