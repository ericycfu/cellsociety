package cellsociety_team06;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLcreator {
	private String myFile;
	private Grid myGrid;
	
	public XMLcreator(String filename, Grid grid) {
		myFile = filename;
		myGrid = grid;
	}

	public void writeToXML() {

		  try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("simulation");
			doc.appendChild(rootElement);

			// four main categories
			Element basicinfo = doc.createElement("basicinfo");
			rootElement.appendChild(basicinfo);
			Element globalsettings = doc.createElement("globalsettings");
			rootElement.appendChild(globalsettings);
			Element gridconfig = doc.createElement("gridconfig");
			rootElement.appendChild(gridconfig);
			Element cellstates = doc.createElement("cellstates");
			rootElement.appendChild(cellstates);
			
			//basic info subcategories
			Element simName = doc.createElement("simulationName");
			basicinfo.appendChild(simName);
			Element simTitle = doc.createElement("simulationTitle");
			basicinfo.appendChild(simTitle);
			Element simAuthor = doc.createElement("simulationAuthor");
			basicinfo.appendChild(simAuthor);
			
			//global settings subcategories
			Element properties = doc.createElement("properties");
			globalsettings.appendChild(properties);
			
			//grid config subcategories
			Element gridheight = doc.createElement("gridheight");
			gridconfig.appendChild(gridheight);
			Element gridwidth = doc.createElement("gridwidth");
			gridconfig.appendChild(gridwidth);
			
			//cellstates
			Cell myCells = myGrid.showMyCells();
			for (int i = 0; i < myGrid.showRowNum(); i+= 1) {
				for (int j = 0; j < myGrid.showColNum(); j += 1) {
					Element cell = doc.createElement("cell");
					cellstates.appendChild(cell);
					cell.setAttribute("x", Integer.toString(i));
					cell.setAttribute("y", Integer.toString(j));
					cell.appendChild(doc.createTextNode(myCells[i][j].showCurrentState()));
				}
			}
			
			//adding to basicinfo
			simName.appendChild(doc.createTextNode("Life"));
			simTitle.appendChild(doc.createTextNode("Our Great Game"));
			simAuthor.appendChild(doc.createTextNode("Eric Fu, Frank Yin, Jing Yang"));
			
			//adding to globalsettings
			properties.appendChild(doc.createTextNode(myCells[0][0].showProperties())); //need to change properties to live,dead format
			
			//adding to grid config
			gridheight.appendChild(doc.createTextNode("3"));
			gridwidth.appendChild(doc.createTextNode("3"));

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\file.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		  	} 
		  	catch (ParserConfigurationException pce) {
		  		pce.printStackTrace();
		  	} 
		  	catch (TransformerException tfe) {
		  		tfe.printStackTrace();
		  	}
	}
}
