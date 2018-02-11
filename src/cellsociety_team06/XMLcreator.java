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
import org.w3c.dom.Attr;
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

		NodeList cellstates = doc.getElementsByTagName("cellstate");
		NodeList energystates = doc.getElementsByTagName("energystates");
		Node mycellstates = null;
		Node myenergystates = null;
		for (int i = 0; i < cellstates.getLength(); i +=1) {
			if (cellstates.item(i) instanceof Element) {
				mycellstates = cellstates.item(i);
			}
			if (energystates.item(i) instanceof Element) {
				myenergystates = energystates.item(i);
			}
		}
		removeChildNodes(mycellstates);
		removeChildNodes(myenergystates);
		
		addNodes(mycellstates, doc, "cell");
		addNodes(myenergystates, doc, "energy");
		

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("lib/current.xml"));
		transformer.transform(source, result);

		System.out.println("Done");
	}
	
	private void addNodes(Node node, Document doc, String type) {
		for (int i = 0; i < myGrid.myColNum; i += 1) {
			for (int j = 0; j < myGrid.myRowNum; j += 1) {
				Element cell = doc.createElement(type);
				node.appendChild(cell);
				Attr x = doc.createAttribute("x");
				Attr y = doc.createAttribute("y");
				x.setValue(Integer.toString(i));
				y.setValue(Integer.toString(j));
				Element state = doc.createElement("state");
				state.appendChild(doc.createTextNode(Integer.toString(myGrid.getCell(i,j).showCurrentState())));
				cell.appendChild(state);
			}
		}
	}
	
	
	private void removeChildNodes(Node node) {
		while (node.hasChildNodes()) node.removeChild(node.getFirstChild());
	}
}