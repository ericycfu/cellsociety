package cellsociety_team06;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

import java.io.File;  
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Simulation extends Application{
	
	public static int celllength = 10;
	public static int buttonwidth = 40;
	public static int buttonheight = 20;
	
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
	public static int gaps = 500;
	
	public static String TITLE = "CellSociety_team06";
	
	public static String filename = "test.txt";
	public Rectangle[][] thegrid;
	
	public static Grid lifeGrid;
	public static Calculator lifecalc;
	
	public static int timer = 0;
	public static boolean pauser = false;
	
	public static Button PAUSE = new Button("Pause");
	public static Button STEP = new Button("Step");
	public static Button FINISH = new Button("Finish");
	public static Button EXIT = new Button("EXIT");
	public static Button SWITCH = new Button("Switch");
	public static TilePane tilePane = new TilePane();
	public static Button FASTER = new Button("Faster");
	public static Button SLOWER = new Button("Slower");
	public static TilePane speeder = new TilePane();
	public static Label timedisplay;
	
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
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
		
		pauser = false;
		
        primaryStage.setTitle(SIMUL);
        
        celllength = 900/width;
        
        Scene scene = creator(1100,1100,BACKGROUND);
        primaryStage.setScene(scene);
        primaryStage.show();

        String[] properties = new String[States.size()];
        for (int i=0;i<States.size();i++){
        	properties[i] = States.get(i);
        }
		
        /*Calculator lifecalc = new Calculator_Wator(properties,10000);
		Grid lifeGrid = new Grid_Wator(height,width,lifecalc,100);
		
		Cell thiscell = new Cell(properties,0);
		
		for (int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				
				if (thegrid[i][j].getFill() == Color.GREEN){
					thiscell = new Cell(properties, 0, 100);
					lifeGrid.createCells(i, j, thiscell);
				} else if (thegrid[i][j].getFill() == Color.BLUE){
					thiscell = new Cell(properties, 1);
					lifeGrid.createCells(i, j, thiscell);
				} else {
					thiscell = new Cell(properties, 2);
					lifeGrid.createCells(i, j, thiscell);
				}
				
			}
		}*/
		
		PAUSE.setOnAction(value ->  {
        	pauser = true;
        	PAUSE.setText("Resume");
        	pausing();
        });
		
		SWITCH.setOnAction(value ->  {
        	try {
				restart();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		
		KeyFrame frame = new KeyFrame(Duration.millis(gaps),
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
	
	public static void restart() throws IOException{
        //用一条指定的命令去构造一个进程生成器
        ProcessBuilder pb=new ProcessBuilder("java","-jar","Simulation.jar");
        //让这个进程的工作区空间改为F:\dist
        //这样的话,它就会去F:\dist目录下找Test.jar这个文件
        pb.directory(new File("Desktop\\Compsci308\\cellsociety_team06\\src\\cellsociety_team06"));
        //得到进程生成器的环境 变量,这个变量我们可以改,
        //改了以后也会反应到新起的进程里面去
        Map<String,String> map=pb.environment();
        Process p=pb.start();
        //然后就可以对p做自己想做的事情了
        //自己这个时候就可以退出了
        System.exit(0);
    }
	
	public static void pausing(){
		pauser = true;
		PAUSE.setOnAction(valuevalue ->  {
			resuming();
        });
		PAUSE.setText("Resume");
		STEP.setDisable(false);
		FASTER.setDisable(true);
		SLOWER.setDisable(true);
	}
	
	public static void resuming(){
		pauser = false;
		PAUSE.setOnAction(valuevalue ->  {
			pausing();
        });
		PAUSE.setText("Pause");
		STEP.setDisable(true);
		FASTER.setDisable(false);
		SLOWER.setDisable(false);
	}
	
	public Scene creator(int L, int W, Paint background){
		
		Group root = new Group();
		
		pauser = false;
		
		thegrid = new Rectangle[height][width];
		
		for (int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				Rectangle R = new Rectangle(i,j,celllength-1,celllength-1);
				R.setX(i * celllength + 100);
				R.setY(j * celllength + 50);
				thegrid[i][j] = R;
				double p = Math.random();
				if (p < Probabilities.get(0)){
					R.setFill(Color.GREEN);
				} else if (p < Probabilities.get(1)){
					R.setFill(Color.BLUE);
				} else {
					R.setFill(Color.WHITE);
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
        
        tilePane.getChildren().add(PAUSE);
        tilePane.getChildren().add(FINISH);
        tilePane.getChildren().add(STEP);
        tilePane.getChildren().add(SWITCH);
        tilePane.getChildren().add(EXIT);
        
        STEP.setDisable(true);
        
        tilePane.setHgap(70);
        tilePane.setVgap(10);
        tilePane.setLayoutX(200);
        tilePane.setLayoutY(970);
        
        root.getChildren().add(tilePane);
        
        FASTER.setStyle("-fx-text-fill: #ff4500; -fx-border-color: #ff4500; -fx-border-width: 1px;");
        SLOWER.setStyle("-fx-text-fill: #ff4500; -fx-border-color: #ff4500; -fx-border-width: 1px;");
        FASTER.setMinSize(100, 50);
        SLOWER.setMinSize(100, 50);
        
        speeder.getChildren().add(FASTER);
        speeder.getChildren().add(SLOWER);
        speeder.setHgap(80);
        
        speeder.setLayoutX(400);
        speeder.setLayoutY(1020);
        
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
	
	public void Step(Grid myGrid, Calculator myCalc){
		if (pauser == false){
			mover(myGrid, myCalc);
		}
	}
	
	public void mover(Grid myGrid, Calculator myCalc){
		myGrid.iterate();
		
		for (int i=0;i<height;i++){
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
		
		timer++;
		timedisplay.setText("Frame passed: " + Integer.toString(timer));
	}
	
	public static void handleKeyInput(KeyCode keyCode){
		
		
		
	}
	
	
	public static void main(String[] args) throws IOException {
		filereader();
        Application.launch(args);
    }
	
}
