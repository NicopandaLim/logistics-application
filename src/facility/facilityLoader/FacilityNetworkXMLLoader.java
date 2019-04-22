package facility.facilityLoader;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import exceptions.DataValidationException;
import exceptions.NullFacilityException;
import facility.facilityOperation.neighbors.FacilityEdge;
import facility.facilityOperation.Facility;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class FacilityNetworkXMLLoader {
    public void parse(HashMap<String, Facility> facilities)
            throws DataValidationException, NullFacilityException
    {
        try {

            // Open file path to xml
            String fileName = "resources/FacilityNetwork.xml";
            File xml = new File(fileName);
            if (!xml.exists()) {
                System.err.println("**** XML File '" + fileName + "' cannot be found");
                System.exit(-1);
            }

            // Build parser and parse
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xml);

            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("Facility");

            // Parse xml and build data structure from it
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                // System.out.println("\nCurrent Element : " + node.getNodeName());
                // System.out.println("");

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    String facilityLocation = element.getAttribute("Location");
                    Facility currentFacility = facilities.get(facilityLocation);

                     if (currentFacility == null) {
                         throw new NullFacilityException("Null facility passed into FacilityNetwork"
                                 + "XMLLoader");
                    }


                    // get all links from a facility
                    NodeList facilityLinks = element.getElementsByTagName("link");

                    for (int j = 0; j < facilityLinks.getLength(); j++) {

                        Element linkElement = (Element) facilityLinks.item(j);

                        String linkLocation = linkElement.getAttribute("Location");
                        String linkDistanceString = linkElement.getElementsByTagName("distance").item(0).getTextContent();
                        long linkDistance = Long.parseLong(linkDistanceString);

                        // System.out.println("Link Location : " + linkLocation);
                        // System.out.println("Distance : " + linkDistanceString);


                        Facility linkFacility = facilities.get(linkLocation);
                        if (linkFacility == null) {
                            throw new NullFacilityException("Null linkFacility passed into FacilityNetwork"
                                    + "XMLLoader");
                        }

                        // Add neighbors to Facility
                        FacilityEdge facilityEdge = new FacilityEdge(linkFacility, linkDistance);
                        currentFacility.addNeighbor(facilityEdge);

                    }
                    // System.out.println("");
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
    }
}
