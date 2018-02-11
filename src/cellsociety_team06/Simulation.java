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
	
	private Grid thisGrid;
	private String Shape;
	
	public Simulation(Grid simulGrid, String simulShape){
		thisGrid = simulGrid;
		Shape = simulShape;
	}
	
	public Cell[][] cellGenerator(int width,int height,String Shape){
		
	}
	
}
