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
	private ArrayList<Integer> gridConfig = new ArrayList<Integer>();
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
		System.out.println(root.getNodeType());
		NodeList myCategories = root.getChildNodes();
		
		System.out.println(myCategories.item(0).getNodeName());
		Node basicinfo = myCategories.item(0);
		NodeList basicinfos = basicinfo.getChildNodes();
		for (int i = 0; i < basicinfos.getLength(); i += 1) {
			basicInfo.add(basicinfos.item(i).getFirstChild().getNodeValue());
		}
		mySimu = basicInfo.get(0);
		String[] mySims = {"Fire", "Wator", "Segregation", "Game of Life", "SugarScape"};
		if (!Arrays.asList(mySims).contains(mySimu)) {
			throw new IOException("Simulation Type not found"); //needs to be handled in launcher class
		}

		
		Element globalsetting = (Element) myCategories.item(1);
		NodeList globalsettings = globalsetting.getChildNodes();
		for (int i = 0; i < globalsettings.getLength(); i += 1) {
			globalSettings.add(globalsettings.item(i).getFirstChild().getNodeValue());
		}
		
		Element gridparameter = (Element) myCategories.item(2);
		NodeList gridparameters = gridparameter.getChildNodes();
		for (int i = 0; i < gridparameters.getLength(); i+=1) {
			gridParameters.add(gridparameters.item(i).getFirstChild().getNodeValue());
		}
		
		Element cellparameter = (Element) myCategories.item(3);
		NodeList cellparameters = cellparameter.getChildNodes();
		for (int i = 0; i < cellparameters.getLength(); i+=1) {
			cellParameters.add(cellparameters.item(i).getFirstChild().getNodeValue());
		}
		
		Element calculatorparameter = (Element) myCategories.item(4);
		NodeList calculatorparameters = calculatorparameter.getChildNodes();
		for (int i = 0; i < calculatorparameters.getLength(); i+=1) {
			calculatorParameters.add(calculatorparameters.item(i).getFirstChild().getNodeValue());
		}

		Element gridconfig = (Element) myCategories.item(5);
		NodeList gridconfigs = gridconfig.getChildNodes();
		for (int i = 0; i < gridconfigs.getLength(); i +=1) {
			gridConfig.add(Integer.valueOf(gridconfigs.item(i).getFirstChild().getNodeValue()));
		}
		
		Element percentage = (Element) myCategories.item(6);
		NodeList percentages = percentage.getChildNodes();
		for (int i = 0; i < percentages.getLength(); i+=1) {
			myPercentages.add(Double.valueOf(percentages.item(i).getFirstChild().getNodeValue()));
		}
		
		int height = Integer.valueOf(gridConfig.get(0));
		int width = Integer.valueOf(gridConfig.get(1));
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
	
	private void getCellData(String type, int height, int width, int data[][]) {
		NodeList cellList = doc.getElementsByTagName(type);
		Node n;
		Element el;
		int state;
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
					state = Integer.valueOf(el.getNodeValue());
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
		return Collections.unmodifiableList(gridConfig);
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
