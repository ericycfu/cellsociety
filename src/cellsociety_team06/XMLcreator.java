package cellsociety_team06;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLcreator {
	
	private String myFilepath;
	private Grid myGrid;
	
	public XMLcreator(String filepath, Grid grid) {
		myFilepath = filepath;
		myGrid = grid;
	}
	
	public void saveState() throws TransformerException, ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(myFilepath);

		NodeList cells = doc.getElementsByTagName("cell");
		NodeList energies = doc.getElementsByTagName("energy");
		Node myCell;
		Element el;
		int ctr = 0;
		for (int times = 0; times < myGrid.showRowNum()*myGrid.showColNum(); times += 1) {
			for (int i = 0; i < myGrid.showRowNum(); i += 1) {
				for (int j = 0; j < myGrid.showColNum(); j+=1) {
					myCell = cells.item(ctr);
					if (myCell instanceof Element) {
						el = (Element) myCell;
						if (el.getAttribute("x").equals(Integer.toString(j)) && el.getAttribute("y").equals(Integer.toString(i))) {
							el.getFirstChild().setNodeValue(Integer.toString(myGrid.getCell(i, j).showCurrentState()));
							ctr += 1;
						}
					}
				}
			}
		}
		ctr= 0;
		for (int times = 0; times < myGrid.showRowNum()*myGrid.showColNum(); times += 1) {
			for (int i = 0; i < myGrid.showRowNum(); i += 1) {
				for (int j = 0; j < myGrid.showColNum(); j+=1) {
					myCell = energies.item(ctr);
					if (myCell instanceof Element) {
						el = (Element) myCell;
						if (el.getAttribute("x").equals(Integer.toString(j)) && el.getAttribute("y").equals(Integer.toString(i))) {
							el.getFirstChild().setNodeValue(Integer.toString((int) myGrid.getCell(i, j).showEnergy()));
						}
					}
				}
			}
		}
		

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("lib/current.xml"));
		transformer.transform(source, result);

		System.out.println("Done");
	}
}