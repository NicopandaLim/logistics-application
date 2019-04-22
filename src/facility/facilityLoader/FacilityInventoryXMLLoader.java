package facility.facilityLoader;



import exceptions.*;
import facility.facilityOperation.Facility;
import facility.facilityOperation.inventory.Inventory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FacilityInventoryXMLLoader {
    public void parse(HashMap<String, Facility> facilities)
            throws DataValidationException, NullFacilityException {
        try {

            // Open file path to xml
            String fileName = "resources/FacilityInventory.xml";
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

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    String facilityLocation = element.getAttribute("Location");
                    Facility currentFacility = facilities.get(facilityLocation);

                     if (currentFacility == null) {
                        throw new NullFacilityException("Null facility passed into FacilityInventory"
                                 + "XMLLoader");
                    }


                    // get all links from a facility
                    NodeList inventoryLinks = element.getElementsByTagName("Item");

                    for (int j = 0; j < inventoryLinks.getLength(); j++) {

                        Element inventoryElement = (Element) inventoryLinks.item(j);

                    //    String linkLocation = inventoryElement.getAttribute("Location");
                        String itemId = inventoryElement.getElementsByTagName("Id").item(0).getTextContent();
                        String itemQuantity = inventoryElement.getElementsByTagName("Quantity").item(0).getTextContent();
                        int quantity = Integer.parseInt(itemQuantity);
                        //String item = ItemManager.getInstance().getItem(itemId);

                        // Add inventory to Facility
                        Inventory currentItem = new Inventory(itemId, quantity);

                       currentFacility.addInventory(currentItem);

                    }
                    // System.out.println("");
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
    }
}
