package cellsociety_team06;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
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
	
	private Grid currentGrid;
	private Calculator currentCalc;
	
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
	private Button SAVE = new Button("Save");
	private TilePane tilePane = new TilePane();
	private Button FASTER = new Button("Faster");
	private Button SLOWER = new Button("Slower");
	private TilePane speeder = new TilePane();
	private Label timedisplay;
	private FileChooser fileChooser;
	private String[] properties;
	private Scene scene;
	private String shape;
	private Simulation currentSim;
	
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private LineChart theChart;
	private XYChart.Series data0;
	private XYChart.Series data1;
	private XYChart.Series data2;
	private double Number0 = 0;
	private double Number1 = 0;
	private double Number2 = 0;

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Application.launch(args);
    }
	
	private void ButtonStyler(){
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
		SAVE.setStyle("-fx-text-fill: #228B22; -fx-border-color: #228B22; -fx-border-width: 2px;");
		SAVE.setMinWidth(80);
		
		tilePane = new TilePane();
		tilePane.getChildren().add(PAUSE);
		tilePane.getChildren().add(FINISH);
		tilePane.getChildren().add(STEP);
		tilePane.getChildren().add(SWITCH);
		tilePane.getChildren().add(SAVE);
		        
		STEP.setDisable(true);
		SAVE.setDisable(true);
		        
		tilePane.setHgap(10);
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
		speeder.getChildren().add(EXIT);
		speeder.setHgap(40);
		        
		speeder.setLayoutX(100);
		speeder.setLayoutY(700);
		        
		root.getChildren().add(speeder);
	}
	
	private void UIStyler(){
		ButtonStyler();
		        
		timedisplay = new Label("Frame passed: " + Integer.toString(timer));
		timedisplay.setLayoutX(width * celllength - 20);
		timedisplay.setLayoutY(20);
		timedisplay.setStyle("-fx-text-fill: #ffffff");
		root.getChildren().add(timedisplay);
	}
	
	private Scene sceneCreator(int L, int W, Paint background){
		shape = myReader.showglobalSettings().get(2);
		pauser = false;
		root = new Group();
		shape = myReader.showglobalSettings().get(2);
		properties = myReader.showglobalSettings().get(0).split(",");
		switch (shape){
			case "square":{
				currentSim = new Simulation_Square(myReader, root);
				break;
			}
			case "triangle":{
				currentSim = new Simulation_Triangle(myReader, root);
				break;
			}
			case "hexagon":{
				currentSim = new Simulation_Hexagon(myReader, root);
				break;
			}
		}
		currentSim.gridGenerator();
		currentCalc = currentSim.getCalc();
		currentGrid = currentSim.getGrid();
		
		UIStyler();
		implementChart();
		
		Scene newscene = new Scene(root, L, W, background);
		newscene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return newscene;
	}
	
	private void handleKeyInput(KeyCode keyCode){
		
		if (keyCode == KeyCode.ENTER){
			
			switch (SimType){
				case "Fire":{
					TextField inputs = new TextField();
					Button button = new Button("Change rate of catching fire");
					HBox hbox = new HBox(inputs, button);
					button.setOnAction(action -> {
						double newrate = Double.parseDouble(inputs.getText());
						currentCalc.resetParameter(newrate);
						root.getChildren().remove(hbox);
		            });
					hbox.setLayoutX(300);hbox.setLayoutY(520);
					root.getChildren().add(hbox);
					break;
				} 
				case "Wator":{
					TextField inputs = new TextField();
					Button energy = new Button("Change energy gain");
					Button reproduce = new Button("Change reproduction time");
					HBox hbox = new HBox(inputs, energy, reproduce);
					energy.setOnAction(action -> {
						double newenergygain = Double.parseDouble(inputs.getText());
						currentGrid.resetEnergyGain(newenergygain);
						root.getChildren().remove(hbox);
		            });
					reproduce.setOnAction(action -> {
						double newreproduce = Double.parseDouble(inputs.getText());
						currentCalc.resetParameter(newreproduce);
						root.getChildren().remove(hbox);
		            });
					hbox.setLayoutX(250);hbox.setLayoutY(520);
					root.getChildren().add(hbox);
					break;
				}
				case "Segregation":{
					TextField inputs = new TextField();
					Button button = new Button("Change satisfaction");
					HBox hbox = new HBox(inputs, button);
					button.setOnAction(action -> {
						double newsatisfactory = Double.parseDouble(inputs.getText());
						currentCalc.resetParameter(newsatisfactory);
						root.getChildren().remove(hbox);
		            });
					hbox.setLayoutX(300);hbox.setLayoutY(520);
					root.getChildren().add(hbox);
					break;
				}
				case "SugarScape":{
					TextField inputs = new TextField();
					Button interval = new Button("Change interval");
					Button matabolism = new Button("Change matabolism");
					Button reproduce = new Button("Change reproduction time");
					HBox hbox = new HBox(inputs, interval, matabolism, reproduce);
					interval.setOnAction(action -> {
						int newinterval = Integer.parseInt(inputs.getText());
						currentGrid.resetSugarInterval(newinterval);
						root.getChildren().remove(hbox);
		            });
					matabolism.setOnAction(action -> {
						int newmata = Integer.parseInt(inputs.getText());
						currentGrid.resetSugarMetabolism(newmata);
						root.getChildren().remove(hbox);
		            });
					reproduce.setOnAction(action -> {
						double newreproduce = Double.parseDouble(inputs.getText());
						currentCalc.resetParameter(newreproduce);
						root.getChildren().remove(hbox);
		            });
					hbox.setLayoutX(200);hbox.setLayoutY(520);
					root.getChildren().add(hbox);
					break;
				}
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
	
	private void setPAUSE(){
		PAUSE.setOnAction(value ->  {
            pauser = true;
            PAUSE.setText("Resume");
            pausing();
        });
	}
	
	private void pausing(){
		  pauser = true;
		  SAVE.setDisable(false);
		  PAUSE.setOnAction(valuevalue ->  {
		   resuming();
		        });
		  PAUSE.setText("Resume");
		  STEP.setDisable(false);
		  FASTER.setDisable(true);
		  SLOWER.setDisable(true);
	}
		 
	private void resuming(){
		  pauser = false;
		  SAVE.setDisable(true);
		  PAUSE.setOnAction(valuevalue ->  {
		   pausing();
		        });
		  PAUSE.setText("Pause");
		  STEP.setDisable(true);
		  FASTER.setDisable(false);
		  SLOWER.setDisable(false);
	}
	
	private void setSAVE(){
		SAVE.setOnAction(value ->  {
            XMLcreator myCreator = new XMLcreator("lib/"+filename, currentGrid);
            try {
				myCreator.saveState();
			} catch (TransformerException e1) {
				System.out.println("Something went wrong with the transformation process. Current state not saved.");
			} catch (ParserConfigurationException e1) {
				System.out.println("Something went wrong with creating the document. Current state not saved.");
			} catch (SAXException e1) {
				System.out.println("Something went wrong with creating the document. Unable to save current state.");
			}
        });
	}
	
	private void setFINISH(){
		FINISH.setOnAction(value ->  {
        	FASTER.setDisable(true);
        	SLOWER.setDisable(true);
        	SAVE.setDisable(false);
        	pauser = true;
        	PAUSE.setDisable(true);
        	FINISH.setText("Fnished");
        	FINISH.setDisable(true);
        });
	}
	
	private void setSTEP(){
		STEP.setOnAction(value ->  {
        	pauser = true;
        	mover(currentGrid,currentCalc);
        });
	}
	
	private void setEXIT(Stage primaryStage){
		EXIT.setOnAction(value ->  {
        	primaryStage.close();
        });
	}
	
	private void initialButtons(){
		SWITCH.setDisable(false);
		PAUSE.setDisable(false);
  		SAVE.setDisable(true);
  		FINISH.setDisable(false);
  		STEP.setDisable(true);
  		FASTER.setDisable(false);
  		SLOWER.setDisable(false);
	}
	
	private void setSWITCH(Stage primaryStage){
		SWITCH.setOnAction(value ->  {
	      	   pauser = true;
	      	   FileChooser newfileChooser = new FileChooser();
	      	            FileChooser.ExtensionFilter newextFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	      	            fileChooser.getExtensionFilters().add(extFilter);
	      	            File newfile = fileChooser.showOpenDialog(primaryStage);
	      	            filename = newfile.getPath();
	      	            root.getChildren().clear();
	      	            try {
	      	            	readFile(filename);
	      	            	} catch (IOException e1) {
	      	            		System.out.println("File not found!");
	      	            	} catch (SAXException e1) {
	      	            		System.out.println("Cannot create document. Configuration not read.");
	      	            	} catch (ParserConfigurationException e1) {
	      	            		System.out.println("Cannot create document. Configuration not read.");
	      	            	}
	      	            Scene newscene = sceneCreator(1200,900,BACKGROUND);
	      	            myPrimaryStage.setScene(newscene);
	      	            myPrimaryStage.show();
	      	            currentSim.cellGenerator();
	      	            int previoustime = timer;
	      	            timer = 0;
	      	            root.getChildren().remove(timedisplay);
	      	            timedisplay = new Label("Frame passed: " + Integer.toString(timer));
	      	    		timedisplay.setLayoutX(width * celllength - 20);
	      	    		timedisplay.setLayoutY(20);
	      	    		timedisplay.setStyle("-fx-text-fill: #ffffff");
	      	    		root.getChildren().add(timedisplay);
	      	    		initialButtons();
	      });
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		chooseFile(primaryStage);
        readFile("lib/"+filename);
		pauser = false;
		celllength = 500/width;
		
        primaryStage.setTitle(SIMUL);
        scene = sceneCreator(1200,900,BACKGROUND);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        setPAUSE();
        setSAVE();
        setFINISH();
        setSTEP();
        setEXIT(primaryStage);
        setSWITCH(primaryStage);
     
        KeyFrame frame = new KeyFrame(Duration.millis(1000),
                e -> Step(currentGrid, currentCalc));
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
	
	private void readFile(String thisfile) throws IOException, SAXException, ParserConfigurationException{
		myReader = new XMLReader(thisfile);
		myReader.read();
		SimType = myReader.showbasicInfo().get(0);
		SimTitle = myReader.showbasicInfo().get(1);
		SimAuthors = myReader.showbasicInfo().get(2);
		height = myReader.showgridConfig().get(0);
		width = myReader.showgridConfig().get(1);
		
	}
	
	private void Step(Grid myGrid, Calculator myCalc){
		if (pauser==false){
		mover(myGrid,myCalc);
		}
	}
	
	private void mover(Grid myGrid, Calculator myCalc){
		myGrid.iterate();
		
		timer++;
		timedisplay.setText("Frame passed: " + Integer.toString(timer));
		updateChart();

	}
	
	private void implementChart(){
		
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		
		theChart = new LineChart(xAxis, yAxis);
		data0 = new XYChart.Series();
		data1 = new XYChart.Series();
		data2 = new XYChart.Series();
		
		Number0 = 0;
		Number1 = 0;
		Number2 = 0;
		
		theChart.getData().remove(data0);
		theChart.getData().remove(data1);
		theChart.getData().remove(data2);
		
		xAxis.setLabel("Time");
		yAxis.setLabel("Ratio");
		
		theChart.getData().add(data0);
		theChart.getData().add(data1);
		
		theChart.setLayoutX(600);
		theChart.setLayoutY(100);
		
		if (properties.length>2){
			data2.setName(properties[2]);
			theChart.getData().add(data2);
		}
		
		data0.setName(properties[0]);
		data1.setName(properties[1]);
		
		
		root.getChildren().add(theChart);
		
	}
	
	private void updateChart(){
		
		Number0 = 0;
		Number1 = 0;
		Number2 = 0;
		
		for (int i=0;i<height;i++){
			for (int j=0;j<width;j++){
				switch(currentGrid.getCell(i, j).showCurrentState()){
					case 0:{Number0++;break;}
					case 1:{Number1++;break;}
					case 2:{Number2++;break;}
				}
			}
		}
		
		
		Number0 = Number0/(height*width);
		Number1 = Number1/(height*width);
		data0.getData().add(new XYChart.Data(timer, Number0));
		data1.getData().add(new XYChart.Data(timer, Number1));
		if (properties.length>2){
			Number2 = Number2/(height*width);
			data2.getData().add(new XYChart.Data(timer, Number2));
			data2.setName(properties[2]+": "+Number2);
		}
		data0.setName(properties[0]+": "+Number0);
		data1.setName(properties[1]+": "+Number1);
	}
	
}
