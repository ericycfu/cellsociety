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

/**
 * Reads in the selected XML file and returns the information which can be used
 * to load the simulation.
 * @author Eric Fu
 *
 */
public class XMLReader {
	private String myFile;
	private ArrayList<String> basicInfo = new ArrayList<String>();
	private ArrayList<String> globalSettings = new ArrayList<String>();
	private ArrayList<String> gridParameters = new ArrayList<String>();
	private ArrayList<String> cellParameters = new ArrayList<String>();
	private ArrayList<String> calculatorParameters = new ArrayList<String>();
	private ArrayList<String> gridConfig = new ArrayList<String>();
	private ArrayList<String> myPercentages = new ArrayList<String>();
	private String mySimu;
	private int myCells[][];
	private int myEnergy[][];
	private Document doc;
	/**
	 * Creates the XMLReader
	 * @param filename is the path of the xml file to be read in.
	 */
	public XMLReader(String filename){
		myFile = filename;
	}
	/**
	 * reads in the data from the XML file and adds the data to its respective list.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
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
		System.out.println(Arrays.toString(myCells[0]));
		System.out.println(Arrays.toString(myCells[1]));
		System.out.println(Arrays.toString(myCells[2]));
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
				x = Integer.valueOf(el.getAttribute("x"));
				y = Integer.valueOf(el.getAttribute("y"));
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
	/**
	 * Gets the name of the simulation
	 * @return a string representing the type of simulation being run
	 */
	public String getSimType(){
		return mySimu;
	}
	/**
	 * Gets the information stored under the basicinfo tag
	 * @return a list containing the information stored in each child node of the basicinfo tag
	 */
	public List<String> showbasicInfo(){
		return Collections.unmodifiableList(basicInfo);
	}
	/**
	 * Gets the information stored under the globalsettings tag
	 * @return a list containing the information stored in each child node of the globalsettings tag
	 */
	public List<String> showglobalSettings(){
		return Collections.unmodifiableList(globalSettings);
	}
	/**
	 * Gets the information stored under the gridparameters tag
	 * @return a list containing the information stored in each child node of the gridparameters tag
	 */
	public List<String> showgridParameters(){
		return Collections.unmodifiableList(gridParameters);
	}
	/**
	 * Gets the information stored under the cellparameters tag
	 * @return a list containing the information stored in each child node of the cellparameters tag
	 */
	public List<String> showcellParameters(){
		return Collections.unmodifiableList(cellParameters);
	}
	/**
	 * Gets the information stored under the calculatorparameters tag
	 * @return a list containing the information stored in each child node of the calculatorparameters tag
	 */
	public List<String> showcalculatorParameters(){
		return Collections.unmodifiableList(calculatorParameters);
	}
	/**
	 * Gets the information stored under the gridconfig tag
	 * @return a list containing the information stored in each child node of the gridconfig tag
	 */
	public List<Integer> showgridConfig(){
		ArrayList<Integer> myIntegerCopy = new ArrayList<Integer>();
		for (String s : gridConfig) myIntegerCopy.add(Integer.valueOf(s));
		return Collections.unmodifiableList(myIntegerCopy);
	}
	/**
	 * Gets the information stored under the percentages tag
	 * @return a list containing the information stored in each child node of the percentages tag
	 */
	public List<String> showmyPercentages(){
		return Collections.unmodifiableList(myPercentages);
	}
	/**
	 * Gets the state for each cell
	 * @return a 2d array with each element representing the state of the cell in that position
	 */
	public int[][] showmyEnergy(){
		return myEnergy.clone();
	}
	/**
	 * Gets the energy for each cell
	 * @return a 2d array with each element representing the energy of the cell in that position
	 */
	public int[][] showmyCells(){
		return myCells.clone();
	}
	
	
}
