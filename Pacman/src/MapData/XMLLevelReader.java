/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapData;

import Game.Level;
import Model.Tile;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Regi
 */
public class XMLLevelReader {

    private final String standardLevelName = "levels.xml";
    private final String standardLevelPath = "levels/standardLevels/";

    private final String randomLevelPath = "levels/savedLevels/";

    public static void main(String[] args){
        Tile[][] tiles = new XMLLevelReader().loadRandomLevel("test1247").getCellData();
        for(Tile[] t : tiles){
            for(Tile tile : t){
                System.out.print(tile.name() + "\t");
            }
            System.out.println("");
        }
    }
    
    public LevelData loadNormalLevel(Level level) {
        File file = new File(standardLevelPath + standardLevelName);
        Document dom = readDocument(file);
        Tile[][] unTransposed = parseDocument(dom, level.name());
        Tile[][] transposed = new Tile[unTransposed[0].length][unTransposed.length];
        for (int i = 0; i < unTransposed.length; i++) {
            for (int j = 0; j < unTransposed[0].length; j++) {
                transposed[j][i] = unTransposed[i][j];
            }
        }
        return new LevelData(transposed);
    }

    public LevelData loadRandomLevel(String name) {
        String fileName = name + ".xml";
        File file = new File(randomLevelPath + fileName);
        Document dom = readDocument(file);
        Tile[][] unTransposed = parseDocument(dom, name);
        Tile[][] transposed = new Tile[unTransposed[0].length][unTransposed.length];
        for (int i = 0; i < unTransposed.length; i++) {
            for (int j = 0; j < unTransposed[0].length; j++) {
                transposed[j][i] = unTransposed[i][j];
            }
        }
        return new LevelData(transposed);
    }

    private Document readDocument(File file) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom = null;
        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse(file);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return dom;
    }

    private Tile[][] parseDocument(Document dom, String levelName) {
        Element rootElement = dom.getDocumentElement();
        Element levelElement = null;
        System.out.println(rootElement.getNodeName());
        if (!rootElement.getNodeName().equals(levelName)) {
            NodeList levelElements = rootElement.getElementsByTagName(levelName);
            if (levelElements == null) {
                throw new RuntimeException("Corrupt levelfile -- no such level");
            }

            if (levelElements.getLength() != 1) {
                throw new RuntimeException("Corrupt levelfile -- multiple levels");
            }

            levelElement = (Element) levelElements.item(0);
        } else {
            levelElement = rootElement;
        }

        return parseLevel(levelElement);
    }

    private Tile[][] parseLevel(Element level) {
        Tile[][] levelData;
        int xSize = getIntValue(level, "xSize");
        int ySize = getIntValue(level, "ySize");
        NodeList levelRows = level.getElementsByTagName("row");
        levelData = new Tile[ySize][xSize];
        for (int i = 0; i < levelRows.getLength(); i++) {
            levelData[i] = getRowData((Element) levelRows.item(i));
        }
        return levelData;
    }

    private Tile[] getRowData(Element row) {
        String[] tileElements = getTextValues(row, "tile");
        Tile[] tiles = new Tile[tileElements.length];
        for (int i = 0; i < tileElements.length; i++) {
            tiles[i] = Tile.fromString(tileElements[i]);
        }
        return tiles;
    }

    private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    private String[] getTextValues(Element ele, String tagName) {
        String[] textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            textVal = new String[nl.getLength()];
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                textVal[i] = el.getFirstChild().getNodeValue();
            }
        }

        return textVal;
    }

    /**
     * I take a xml element and the tag name, look for the tag and get the text
     * content i.e for <employee><name>John</name></employee> xml snippet if the
     * Element points to employee node and tagName is 'name' I will return John
     */
    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

}
