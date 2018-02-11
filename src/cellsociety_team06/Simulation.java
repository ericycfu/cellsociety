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

public abstract class Simulation{
	
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
	
	private String cellType;
	
	
	
	
	
	
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
			/*case "Segregation":{
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
			}*/
		}
		timer++;
		timedisplay.setText("Frame passed: " + Integer.toString(timer));

	}
	
	
	
	
	
}
