package cellsociety_team06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public abstract class Simulation {
	
	public static int[] dimension;
	public static double GAP;
	public static ArrayList<String> States;
	public static ArrayList<Float> Probabilities;
	public static HashMap<String,Float> getProb;
	
	public static String filename;
	
	public static void filereader() throws IOException{
		
		String filepath = "Simulation\\" + filename;
		File filename = new File(filepath);
		
		InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        line = br.readLine();
        int width =Integer.parseInt(line);
        line = br.readLine();
        int height = Integer.parseInt(line);
        
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
	
}
