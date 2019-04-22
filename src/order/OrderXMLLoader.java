package order;

import exceptions.DataValidationException;
import order.orderItem.OrderItem;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrderXMLLoader {
    public ArrayList<Order> parse(ArrayList<Order> orders)
            throws DataValidationException{
        try {

            // Open file path to xml
            String fileName = "resources/Orders.xml";
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

            NodeList nodeList = document.getElementsByTagName("Order");

            // Parse xml and build data structure from it
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    String orderId = element.getAttribute("Id");
                    String orderTime = element.getElementsByTagName("OrderTime").item(0).getTextContent();
                    String destination = element.getElementsByTagName("Destination").item(0).getTextContent();

                    // Create Order based on Id and load into list
                    Order currentOrder = OrderFactory.createOrder(orderId, orderTime, destination);
                    orders.add(currentOrder);

                    NodeList orderItemList = element.getElementsByTagName("OrderItem");
                    for (int j = 0; j < orderItemList.getLength(); j++){
                        if (orderItemList.item(j).getNodeType() == Node.TEXT_NODE){
                            continue;
                        }
                        //get orderItems
                        Element elem = (Element) orderItemList.item(j);
                        String itemId = elem.getElementsByTagName("Item").item(0).getTextContent();
                        String itemQuantity =  elem.getElementsByTagName("quantity").item(0).getTextContent();
                        int quantity = Integer.parseInt(itemQuantity);

                        OrderItem currOrderItem = new OrderItem(itemId, quantity);
                        currentOrder.addOrderItem(itemId, currOrderItem);

                    }
                }
            }

            return orders;

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
        return null;
    }

}
