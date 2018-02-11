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

<<<<<<< HEAD
public abstract class Simulation{
=======
public abstract class Simulation {

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
>>>>>>> ab73babb85065016a6e05c8160651ca215ad7cab
	
	private Grid thisGrid;
	private String Shape;
	
	public Simulation(Grid simulGrid, String simulShape){
		thisGrid = simulGrid;
		Shape = simulShape;
	}
	
	public Cell[][] cellGenerator(int width,int height,String Shape){
		
	}
	
<<<<<<< HEAD
=======
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
		
		Rectangle R = new Rectangle(x,y,celllength,celllength);
		R.setFill(Color.PLUM);
		root.getChildren().add(R);
		
	}*/
	
	
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
	
>>>>>>> ab73babb85065016a6e05c8160651ca215ad7cab
}
