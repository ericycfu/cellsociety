package cellsociety_team06;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException; 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class FileReader {
	private String myFile;
	private ArrayList<String> basicInfo = new ArrayList<String>();
	private ArrayList<String> globalSettings = new ArrayList<String>();
	private ArrayList<String> gridConfig = new ArrayList<String>();
	private Document doc;
	private String mySimu;
	
	public FileReader(String filename){
		myFile = filename;
	}
	
	public void read() throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder(); 
		doc = db.parse(new File(myFile));
		doc.normalize();
		mySimu = getNodeData("simulationName");
		switch (mySimu) {
			case "Game of Life": {
				basicInfo.add(getNodeData("simulationName"));
				globalSettings.add(getNodeData("propertyString"));
				gridConfig.add(getNodeData("gridheight"));
				gridConfig.add(getNodeData("gridwidth"));
				gridConfig.add(getNodeData("livecellpercent"));
			}
			case "Segregation": {
				basicInfo.add(getNodeData("simulationName"));
				globalSettings.add(getNodeData("propertyString"));
				gridConfig.add(getNodeData("gridheight"));
				gridConfig.add(getNodeData("gridwidth"));
				gridConfig.add(getNodeData("xpercentage"));
				gridConfig.add(getNodeData("ypercentage"));
			}
			case "Wator": {
				basicInfo.add(getNodeData("simulationName"));
				globalSettings.add(getNodeData("propertyString"));
				globalSettings.add(getNodeData("initialenergy"));
				globalSettings.add(getNodeData("energygain"));
				globalSettings.add(getNodeData("reproducetime"));
				gridConfig.add(getNodeData("gridheight"));
				gridConfig.add(getNodeData("gridwidth"));
				gridConfig.add(getNodeData("sharkpercentage"));
				gridConfig.add(getNodeData("fishpercentage"));
			}
			case "Fire": {
				basicInfo.add(getNodeData("simulationName"));
				globalSettings.add(getNodeData("propertyString"));
				globalSettings.add(getNodeData("probcatch"));
				gridConfig.add(getNodeData("gridheight"));
				gridConfig.add(getNodeData("gridwidth"));
				gridConfig.add(getNodeData("treepercentage"));
				gridConfig.add(getNodeData("burningpercentage"));
			}
		}
	}
	
	private String getNodeData(String nodeName){
		NodeList nodeList = doc.getDocumentElement().getElementsByTagName(nodeName);
		Node subnode = nodeList.item(0);
		Element nodeinfo = (Element) subnode;
		return nodeinfo.getTextContent();
	}
	
	public ArrayList<String> showbasicInfo(){
		return basicInfo;
	}
	
	public ArrayList<String> showglobalSettings(){
		return globalSettings;
	}
	
	public ArrayList<String> showgridConfig(){
		return gridConfig;
	}
	
	public void reset(){
		
	}
	
	
	
	
	
}
