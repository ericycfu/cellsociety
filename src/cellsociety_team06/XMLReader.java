package cellsociety_team06;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException; 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLReader {
	private String myFile;
	private ArrayList<String> basicInfo = new ArrayList<String>();
	private ArrayList<String> globalSettings = new ArrayList<String>();
	private ArrayList<String> gridConfig = new ArrayList<String>();
	private Cell myCells[][];
	private Document doc;
	private String mySimu;
	
	public XMLReader(String filename){
		myFile = filename;
	}
	
	public void read() throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		try {
		doc = db.parse(new File(myFile));
		}
		catch (Exception e) {
			System.out.println("Filename incorrect or file not found");
		}
		doc.normalize();
		mySimu = getNodeData("simulationName");
		try {
			switch (mySimu) {
				case "Game of Life": {
					//System.out.println(mySimu+"!!!");
					basicInfo.add(getNodeData("simulationName"));
					basicInfo.add(getNodeData("simulationTitle"));
					basicInfo.add(getNodeData("simulationAuthor"));
					
					globalSettings.add(getNodeData("propertystring"));
					gridConfig.add(getNodeData("livecellpercent"));
					break;
				} 
				case "Segregation": {
					//System.out.println(mySimu+"!!!");
					basicInfo.add(getNodeData("simulationName"));
					basicInfo.add(getNodeData("simulationTitle"));
					basicInfo.add(getNodeData("simulationAuthor"));
					
					globalSettings.add(getNodeData("propertystring"));
					globalSettings.add(getNodeData("satisfylevel"));
					gridConfig.add(getNodeData("xpercentage"));
					gridConfig.add(getNodeData("opercentage"));
					break;
				} 
				case "Wator": {
					//System.out.println(mySimu+"!!!");
					basicInfo.add(getNodeData("simulationName"));
					basicInfo.add(getNodeData("simulationTitle"));
					basicInfo.add(getNodeData("simulationAuthor"));
					
					globalSettings.add(getNodeData("propertystring"));
					globalSettings.add(getNodeData("initialenergy"));
					globalSettings.add(getNodeData("energygain"));
					globalSettings.add(getNodeData("reproducetime"));
					gridConfig.add(getNodeData("sharkpercentage"));
					gridConfig.add(getNodeData("fishpercentage"));
					break;
				}
				case "Fire": {
					basicInfo.add(getNodeData("simulationName"));
					basicInfo.add(getNodeData("simulationTitle"));
					basicInfo.add(getNodeData("simulationAuthor"));
					
					globalSettings.add(getNodeData("propertystring"));
					globalSettings.add(getNodeData("probcatch"));
					gridConfig.add(getNodeData("treepercentage"));
					gridConfig.add(getNodeData("burningpercentage"));
					break;
				}
			}
		}
		catch(Exception e) {
			System.out.println("data was missing from xml file");
		}
		gridConfig.add(getNodeData("gridheight"));
		gridConfig.add(getNodeData("gridwidth"));
		
		int height = Integer.valueOf(getNodeData("gridheight"));
		int width = Integer.valueOf(getNodeData("gridwidth"));
		myCells = new Cell[height][width];
		try {
			NodeList cellList = doc.getElementsByTagName("cell");
			Node n;
			Element el;
			String state;
			int ctr = 0;
			int x;
			int y;
			for (int i = 0; i < height; i+=1) {
				for (int j = 0; j < width; j += 1) {
					n = cellList.item(ctr);
					if (n instanceof Element) {
						el = (Element) n;
						x = Integer.valueOf(el.getAttribute("x"));
						y = Integer.valueOf(el.getAttribute("y"));
						state = el.getNodeValue();
						//add cell constructor
						myCells[x][y] = new Cell();
						
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("specific cell data not found or not enough data");
		}
	}
	
	public String getSimType(){
		return mySimu;
	}
	
	private String getNodeData(String nodeName){
		NodeList nodeList = doc.getElementsByTagName(nodeName);
		Node subnode = nodeList.item(0);
		//System.out.println(subnode.getFirstChild().getNodeValue());
		return subnode.getFirstChild().getNodeValue();
	}
	
	public List<String> showbasicInfo(){
		return Collections.unmodifiableList(basicInfo);
	}
	
	public List<String> showglobalSettings(){
		return Collections.unmodifiableList(globalSettings);
	}
	
	public List<String> showgridConfig(){
		return Collections.unmodifiableList(gridConfig);
	}
	
	public Cell[][] showmyCells(){
		return myCells.clone();
	}
	
	
}
