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
	private ArrayList<String> gridParameters = new ArrayList<String>();
	private ArrayList<String> cellParameters = new ArrayList<String>();
	private ArrayList<String> calculatorParameters = new ArrayList<String>();
	private ArrayList<String> gridConfig = new ArrayList<String>();
	private ArrayList<Double> myPercentages = new ArrayList<Double>();
	private String mySimu;
	private int myCells[][];
	private int myEnergy[][];
	private Document doc;
	
	public XMLReader(String filename){
		myFile = filename;
	}
	
	public void read() throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder(); //parser config exception
		try{
			doc = db.parse(new File(myFile));
		}
		catch(IOException e) {
			System.out.println("missing file or incorrect file path");
			return;
		}
		doc.normalize();

		Element root = doc.getDocumentElement();
		
		NodeList myCategories = root.getChildNodes();
		
		Node basicinfo = getElement(1, myCategories);
		
		addToArray(basicinfo, basicInfo);
		mySimu = basicInfo.get(0);
		String[] mySims = {"Fire", "Wator", "Segregation", "Game of Life", "SugarScape"};
		if (!Arrays.asList(mySims).contains(mySimu)) {
			throw new IOException("Simulation Type not found"); //needs to be handled in launcher class
		}

		
		Node globalsetting = getElement(2, myCategories);
		addToArray(globalsetting, globalSettings);		
		
		Node gridparameter = getElement(3, myCategories);
		addToArray(gridparameter, gridParameters);

		Node cellparameter = getElement(4, myCategories);
		addToArray(cellparameter, cellParameters);
		
		Node calculatorparameter = getElement(5, myCategories);
		addToArray(calculatorparameter, calculatorParameters);
		
		Node gridconfig = getElement(6, myCategories);
		addToArray(gridconfig, gridConfig);
		
		Node percentage = getElement(7, myCategories);
		addToArray(percentage, myPercentages);
		
		int height = Integer.parseInt(gridConfig.get(0));
		int width = Integer.parseInt(gridConfig.get(1));
		myCells = new int[height][width];
		myEnergy = new int [height][width];
		try {
			getCellData("cell", height, width, myCells);
			getCellData("energy", height, width, myEnergy);
		}
		catch(Exception e) {
			System.out.println("specific cell data not found or not enough data");
			return;
		}
	}
	
	private Node getElement(int index, NodeList myList) {
		int ctr = 0;
		for (int i = 0; i < myList.getLength(); i+=1) {
			if (myList.item(i) instanceof Element) {
				ctr +=1;
				if (ctr == index) {
					return myList.item(i);
				}
			}
		}
		return null;
	}
	private void addToArray(Node myNode, ArrayList myArrayList) {
		NodeList myNodeList = myNode.getChildNodes();
		for (int i = 0; i < myNodeList.getLength(); i += 1) {
			if (myNodeList.item(i) instanceof Element) {
				myArrayList.add(myNodeList.item(i).getFirstChild().getNodeValue());
			}
		}
		return;
	}
	
	private void getCellData(String type, int height, int width, int data[][]) {
		NodeList cellList = doc.getElementsByTagName(type);
		Node n;
		Element el;
		int state;
		int ctr = 1;
		int x;
		int y;
		for (int i = 0; i < height; i+=1) {
			for (int j = 0; j < width; j += 1) {
				n = getElement(ctr,cellList);
				el = (Element) n;
				NodeList elList = el.getChildNodes();
				state = Integer.valueOf(getElement(1,elList).getFirstChild().getNodeValue());
				//checks for invalid state
				if ((state-1) >globalSettings.get(0).split(",").length) {
					state = 0;
					throw new IndexOutOfBoundsException("State was not within allowable range");
				}					
				data[x][y] = state;
				ctr+=1;
			}
		}
	}
	
	public String getSimType(){
		return mySimu;
	}
	
	public List<String> showbasicInfo(){
		return Collections.unmodifiableList(basicInfo);
	}
	
	public List<String> showglobalSettings(){
		return Collections.unmodifiableList(globalSettings);
	}
	
	public List<String> showgridParameters(){
		return Collections.unmodifiableList(gridParameters);
	}
	
	public List<String> showcellParameters(){
		return Collections.unmodifiableList(cellParameters);
	}
	
	public List<String> showcalculatorParameters(){
		return Collections.unmodifiableList(calculatorParameters);
	}
	
	public List<Integer> showgridConfig(){
		ArrayList<Integer> myIntegerCopy = new ArrayList<Integer>();
		for (String s : gridConfig) myIntegerCopy.add(Integer.valueOf(s));
		return Collections.unmodifiableList(myIntegerCopy);
	}
	
	public List<Double> showmyPercentages(){
		return Collections.unmodifiableList(myPercentages);
	}
	
	public int[][] showmyEnergy(){
		return myEnergy.clone();
	}
	
	public int[][] showmyCells(){
		return myCells.clone();
	}
	
	
}
