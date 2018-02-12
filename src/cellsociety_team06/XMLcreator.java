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

/**
 * Saves the current configuration of the simulation in an xml file.
 * Depends on the Grid class obtained from the simulation class.
 * @author Eric Fu
 * 
 */
public class XMLcreator {
	
	private String myFilepath;
	private Grid myGrid;
	/**
	 * creates an XMLcreator
	 * @param filepath The filepath of the xml file of the current simulation
	 * @param grid The grid that contains the cells of the current simulation
	 */
	public XMLcreator(String filepath, Grid grid) {
		myFilepath = filepath;
		myGrid = grid;
	}
	/**
	 * writes the cell states into a new xml file
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void saveState() throws TransformerException, ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(myFilepath);

		NodeList cellstates = doc.getElementsByTagName("cellstates");
		NodeList energystates = doc.getElementsByTagName("energystates");
		Node mycellstates = null;
		Node myenergystates = null;
		System.out.println(cellstates.getLength());
		System.out.println(energystates.getLength());
		for (int i = 0; i < cellstates.getLength(); i +=1) {
			if (cellstates.item(i) instanceof Element) {
				mycellstates = cellstates.item(i);
			}
		}		
		for (int i = 0; i < energystates.getLength(); i +=1) {
			if (energystates.item(i) instanceof Element) {
				myenergystates = energystates.item(i);
			}
		}
		removeChildNodes(mycellstates);
		removeChildNodes(myenergystates);
		
		addNodes(mycellstates, doc, "cell");
		addNodes(myenergystates, doc, "energy");
		
		NodeList useprob = doc.getElementsByTagName("useprobability");
		try {
		Node useprobability= null;
		for (int i = 0; i < useprob.getLength(); i+=1) {
			if (useprob.item(i) instanceof Element)
				useprobability = useprob.item(i);
		}
		useprobability.setTextContent("0");
		}
		catch(NullPointerException e) {
			System.out.println("node by name of useprobability doesn't exist");
			return;
		}
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
				x.setValue(Integer.toString(i));
				cell.setAttributeNode(x);
				Attr y = doc.createAttribute("y");
				y.setValue(Integer.toString(j));
				cell.setAttributeNode(y);
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