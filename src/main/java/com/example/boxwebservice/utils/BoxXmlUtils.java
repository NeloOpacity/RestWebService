package com.example.boxwebservice.utils;

import com.example.boxwebservice.model.Box;
import com.example.boxwebservice.model.Item;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class BoxXmlUtils {
    public static List<Box> getBoxes(NodeList nodeList) {
        List<Box> boxes = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node boxNode = nodeList.item(i);
            Node parentBoxNode = boxNode.getParentNode();
            Integer containedIn = null;
            Integer boxId = null;
            if (parentBoxNode != null && parentBoxNode.getNodeName().equals("Box")) {
                if (parentBoxNode.hasAttributes())
                    containedIn = Integer.parseInt(parentBoxNode.getAttributes().getNamedItem("id").getTextContent());
                else
                    throw new RuntimeException("No such attribute as 'id' in node 'Box'");
            }
            if (boxNode.hasAttributes())
                boxId = Integer.parseInt(boxNode.getAttributes().getNamedItem("id").getTextContent());
            else
                throw new RuntimeException("No such attribute as 'id' in node 'Box'");
            Box box = new Box(boxId, containedIn);
            boxes.add(box);
        }
        return boxes;
    }

    public static List<Item> getItems(NodeList nodeList) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node itemNode = nodeList.item(i);
            Node parentItemNode = itemNode.getParentNode();
            Integer containedIn = null;
            Integer itemId = null;
            String color = null;

            if (parentItemNode != null && parentItemNode.getNodeName().equals("Box")) {
                if (parentItemNode.hasAttributes())
                    containedIn = Integer.parseInt(parentItemNode.getAttributes().getNamedItem("id").getTextContent());
                else
                    throw new RuntimeException("No such attribute as 'id' in node 'Box'");
            }

            if (itemNode.hasAttributes()) {
                itemId = Integer.parseInt(itemNode.getAttributes().getNamedItem("id").getTextContent());
                if (itemNode.getAttributes().getNamedItem("color") != null)
                    color = itemNode.getAttributes().getNamedItem("color").getTextContent();
            } else
                throw new RuntimeException("No such attribute as 'id' in node 'Box'");
            Item item = new Item(itemId, color, containedIn);
            items.add(item);
        }
        return items;
    }
}
