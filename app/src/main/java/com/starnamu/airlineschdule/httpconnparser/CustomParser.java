package com.starnamu.airlineschdule.httpconnparser;

import com.starnamu.airlineschdule.comm.CommonConventions;
import com.starnamu.airlineschdule.slidinglayout.AirlineItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by starnamu on 2015-09-09.
 */
public class CustomParser implements CommonConventions {
    String AinStream;
    String ADstate;
    Element[] element;
    String[] itemStr;
    ArrayList<AirlineItem> TempList;
    ArrayList<AirlineItem> itemLists;

    CustomParser(String AinStream) throws Exception {
        this.AinStream = AinStream;
        init();
    }

    private void init() throws Exception {
        this.element = new Element[PARSERITEMGROUP.length];
        this.itemStr = new String[PARSERITEMGROUP.length];
        TempList = new ArrayList<>();
        {
            ADstate = "A";
            airportparser(AinStream);
        }
    }

    private void airportparser(String str) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document document = builder.parse(inStream);

        Document document = builder.parse(new InputSource(new StringReader(str)));

        this.itemLists = parserDocument(document);
    }

    private ArrayList<AirlineItem> parserDocument(Document document) {
        Element element = document.getDocumentElement();
        element.getElementsByTagName("item");
        NodeList nodeList = element.getElementsByTagName("item");

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (parseItemNode(nodeList, i) != null) {
                    AirlineItem item = parseItemNode(nodeList, i);
                    TempList.add(item);
                }
            }
        }
        return TempList;
    }

    private AirlineItem parseItemNode(NodeList nodeList, int index) {
        Element elem = (Element) nodeList.item(index);
        for (int i = 0; i < PARSERITEMGROUP.length; i++) {
            element[i] = (Element) elem.getElementsByTagName(PARSERITEMGROUP[i]).item(0);
            if (element[i] == null) {
                itemStr[i] = " ";
            } else if (element[i] != null) {
                Node firstchild = element[i].getFirstChild();
                if (firstchild != null) {
                    itemStr[i] = firstchild.getNodeValue();
                }
            }
        }

        if (ADstate.equals("A")) {
            itemStr[10] = "A";
        } else if (ADstate.equals("D")) {
            itemStr[10] = "D";
        } else {
            itemStr[10] = "";
        }

        AirlineItem item = new AirlineItem(itemStr);
        return item;
    }

    public ArrayList<AirlineItem> getItems() {
        return null;
    }
}







