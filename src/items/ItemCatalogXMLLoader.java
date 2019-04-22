package items;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ItemCatalogXMLLoader {
    public HashMap<String, Item> parse(HashMap<String, Item> items)
    {
        try {

            // Open file path to xml
            String fileName = "resources/ItemCatalog.xml";
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

            NodeList itemEntries = document.getElementsByTagName("Item");

            // Parse xml and build data structure from it
            for (int i = 0; i < itemEntries.getLength(); i++) {

                Node node = itemEntries.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    String itemId = element.getAttribute("Id");

                    String itemPrice = element.getElementsByTagName("price").item(0).getTextContent();
                    double price = Double.parseDouble(itemPrice);

                    // Create facility based on location and load into list
                    Item currentItem = ItemFactory.createItem(itemId, price);

                    items.put(itemId, currentItem);
                }
            }
            return items;

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
        return null;
    }
}
