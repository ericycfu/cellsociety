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
	private Document doc;
	
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

		Element root = doc.getDocumentElement();
		
		NodeList myCategories = root.getChildNodes();
		
		Element basicinfo = (Element) myCategories.item(0);
		NodeList basicinfos = basicinfo.getChildNodes();
		for (int i = 0; i < basicinfos.getLength(); i += 1) {
			basicInfo.add(basicinfos.item(i).getFirstChild().getNodeValue());
		}
		mySimu = basicInfo.get(0);
		
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
		try {
			NodeList cellList = doc.getElementsByTagName("cell");
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
						//add cell constructor
						myCells[x][y] = state;
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
	
	public List<String> showgridConfig(){
		return Collections.unmodifiableList(gridConfig);
	}
	
	public int[][] showmyCells(){
		return myCells.clone();
	}
	
	
}
