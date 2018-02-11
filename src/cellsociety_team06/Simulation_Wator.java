package cellsociety_team06;

import org.xml.sax.SAXException;

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

public class Simulation_Wator extends Simulation{

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
