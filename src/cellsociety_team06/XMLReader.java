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


public class XMLReader {
	private String myFile;
	private ArrayList<String> basicInfo = new ArrayList<String>();
	private ArrayList<String> globalSettings = new ArrayList<String>();
	private ArrayList<String> gridConfig = new ArrayList<String>();
	private Document doc;
	private String mySimu;
	
	public XMLReader(String filename){
		myFile = filename;
	}
	
	public void read() throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder(); 
		doc = db.parse(new File(myFile));
		doc.normalize();
		mySimu = getNodeData("simulationName");
		//System.out.println(mySimu+"!!!");
		switch (mySimu) {
			case "Game of Life": {
				System.out.println(mySimu+"!!!");
				basicInfo.add(getNodeData("simulationName"));
				basicInfo.add(getNodeData("simulationTitle"));
				basicInfo.add(getNodeData("simulationAuthor"));
				
				globalSettings.add(getNodeData("propertystring"));
				gridConfig.add(getNodeData("gridheight"));
				gridConfig.add(getNodeData("gridwidth"));
				gridConfig.add(getNodeData("livecellpercent"));
				break;
			} 
			case "Segregation": {
				System.out.println(mySimu+"!!!");
				basicInfo.add(getNodeData("simulationName"));
				basicInfo.add(getNodeData("simulationTitle"));
				basicInfo.add(getNodeData("simulationAuthor"));
				
				globalSettings.add(getNodeData("propertystring"));
				globalSettings.add(getNodeData("satisfylevel"));
				gridConfig.add(getNodeData("gridheight"));
				gridConfig.add(getNodeData("gridwidth"));
				gridConfig.add(getNodeData("xpercentage"));
				gridConfig.add(getNodeData("opercentage"));
				break;
			} 
			case "Wator": {
				System.out.println(mySimu+"!!!");
				basicInfo.add(getNodeData("simulationName"));
				basicInfo.add(getNodeData("simulationTitle"));
				basicInfo.add(getNodeData("simulationAuthor"));
				
				globalSettings.add(getNodeData("propertystring"));
				globalSettings.add(getNodeData("initialenergy"));
				globalSettings.add(getNodeData("energygain"));
				globalSettings.add(getNodeData("reproducetime"));
				gridConfig.add(getNodeData("gridheight"));
				gridConfig.add(getNodeData("gridwidth"));
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
				gridConfig.add(getNodeData("gridheight"));
				gridConfig.add(getNodeData("gridwidth"));
				gridConfig.add(getNodeData("treepercentage"));
				gridConfig.add(getNodeData("burningpercentage"));
				break;
			}
		}
	}
	
	private String getNodeData(String nodeName){
		NodeList nodeList = doc.getElementsByTagName(nodeName);
		Node subnode = nodeList.item(0);
		//System.out.println(subnode.getFirstChild().getNodeValue());
		return subnode.getFirstChild().getNodeValue();
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
