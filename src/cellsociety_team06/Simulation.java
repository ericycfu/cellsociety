package cellsociety_team06;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
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

public abstract class Simulation {
	
	public static int celllength = 10;
	
	public static int[] dimension;
	public static double GAP;
	public static ArrayList<String> States;
	public static ArrayList<Float> Probabilities;
	public static HashMap<String,Float> getProb;
	
	public static int MILLISECOND_DELAY = 1000 / 60;
	public static double SECOND_DELAY = 1.0 / 60;
	public static Paint BACKGROUND = Color.AZURE;
	
	public static String filename = "test.txt";
	public static Group root;
	
	public static void filereader() throws IOException{
		
		String filepath = filename;
		File filename = new File(filepath);
		
		InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        line = br.readLine();
        int width =Integer.parseInt(line);
        line = br.readLine();
        int height = Integer.parseInt(line);
        dimension[1] = width;
        dimension[2] = height;
        
        line = br.readLine();
        String[] St = line.split(",");
        for (int i=0;i<St.length;i++){
        	States.add(St[i]);
        }
        
        line = br.readLine();
        String[] Prob = line.split(",");
        for (int i=0;i<Prob.length;i++){
        	Probabilities.add(Float.parseFloat(Prob[i]));
        }
        
        for (int i=0;i<States.size();i++){
        	getProb.put(States.get(i), Probabilities.get(i));
        }
		
	}
	
	public static void setupVisual(){
		
		int LENGTH = 2 * 30 + dimension[1] * celllength;
		int WIDTH = 2 * 70 + dimension[2] * celllength;
		
		Scene myscene = creator(LENGTH, WIDTH, BACKGROUND);
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> Step(SECOND_DELAY));
		Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
		
	}
	
	public static Scene creator(int L, int W, Paint background){
		
		Scene newscene = new Scene(root, L, W, background);
		newscene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return newscene;
		
	}
	
	public static void Step(double gap){
		
	}
	
	public static void handleKeyInput(KeyCode keyCode){
		
	}
	
	public static void main (String[] args) throws IOException {
        
		filereader();
        setupVisual();
		
    }
	
}
