package facility.facilityLoader;


import exceptions.DataValidationException;
import facility.FacilityFactory;
import facility.facilityOperation.Facility;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class FacilityXMLLoader {
    public HashMap<String, Facility> parse(HashMap<String, Facility> facilities)
            throws DataValidationException {
        try {

            // Open file path to xml
            String fileName = "resources/Facilities.xml";
            File xml = new File(fileName);
            if (!xml.exists()) {
                System.err.println("**** XML File '" + fileName + "' cannot be found");
                System.exit(-1);
            }
            // System.out.println("File found...");

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

                    String rate = element.getElementsByTagName("Rate").item(0).getTextContent();
                    int ratePerDay = Integer.parseInt(rate);
                    String cost = element.getElementsByTagName("CostPerDay").item(0).getTextContent();
                    int costPerDay = Integer.parseInt(cost);


                    // Create facility based on location and load into list
                    Facility currentFacility = FacilityFactory.createFacility(facilityLocation, ratePerDay, costPerDay);
                    facilities.put(facilityLocation, currentFacility);
                    // System.out.println("");
                }
            }

            return facilities;

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
        return null;
    }
}
