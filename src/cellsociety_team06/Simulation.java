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
	public static ArrayList<String> States;
	public static ArrayList<Float> Probabilities;
	public static HashMap<String,Float> getProb;
	
	public static int MILLISECOND_DELAY = 1000 / 60;
	public static double SECOND_DELAY = 1.0 / 60;
	public static Paint BACKGROUND = Color.GREY;
	
	public static String TITLE = "CellSociety_team06";
	
	public static String filename = "test.txt";
	public static Group root;
	public static Rectangle[][] thegrid;
	
	static Grid lifeGrid;
	static Calculator lifecalc;
	
	public static void filereader() throws IOException{
		
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
		
	}
	
	
	@Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Test");


        Scene scene = creator(width * celllength + 100,height * celllength + 100,BACKGROUND);
        primaryStage.setScene(scene);
        primaryStage.show();

        String[] properties = new String[States.size()];
        for (int i=0;i<States.size();i++){
        	properties[i] = States.get(i);
        }
		Calculator lifecalc = new Calculator_Life(properties);
		Grid lifeGrid = new Grid_Life(height,width,lifecalc);
		
		Cell thiscell = new Cell(properties,0);
		
		for (int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				
				if (thegrid[i][j].getFill() == Color.BLACK){
					thiscell = new Cell(properties, 0);
					lifeGrid.createCells(j, i, thiscell);
				} else {
					thiscell = new Cell(properties, 1);
					lifeGrid.createCells(j, i, thiscell);
				}
				
			}
		}
    	
		for(int i=0;i<10;i++){
			try {
				Thread.currentThread().sleep(1000);
				Step();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("now is " + System.currentTimeMillis());
		}
		
	}
	
	
	
	public static Scene creator(int L, int W, Paint background){
		
		root = new Group();
		
		thegrid = new Rectangle[width][height];
		
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				Rectangle R = new Rectangle(i,j,celllength-1,celllength-1);
				R.setX(i * celllength + 50);
				R.setY(j * celllength + 50);
				thegrid[i][j] = R;
				if (Math.random() > Probabilities.get(0)){
					R.setFill(Color.WHITE);
				} else {
					R.setFill(Color.BLACK);
				}
				
				root.getChildren().add(R);
			}
		}
		
		Scene newscene = new Scene(root, L, W, background);
		
		newscene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return newscene;
		
	}
	
	public static void cellgenerator(int x, int y){
		
		Rectangle R = new Rectangle(x,y,celllength,celllength);
		R.setFill(Color.PLUM);
		root.getChildren().add(R);
		
	}
	
	
	
	public static void Step(){
		lifeGrid.iterate();
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				if (lifeGrid.getCell(i, j).showCurrentState()==0){
					thegrid[i][j].setFill(Color.BLACK);
				} else {
					thegrid[i][j].setFill(Color.WHITE);
				}
			}
		}
		
	}
	
	public static void handleKeyInput(KeyCode keyCode){
		
	}
	
	
	public static void main(String[] args) throws IOException {
		filereader();
        Application.launch(args);
    }
	
}
