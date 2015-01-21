/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapData;

import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Regi
 */
public class XMLLevelWriter {

    private final String writePath = "levels/savedLevels/";

    public static void main(String[] args) {
        Tile[][] tiles = {{Tile.EMPTY, Tile.WALL},
        {Tile.GHOST, Tile.SUPER_DOT}};

        Random rand = new Random();

        new XMLLevelWriter().writeLevel(new LevelData(tiles), "test" + rand.nextInt(10000));
    }

    public void writeLevel(LevelData levelData, String name) {
        String fileName = name + ".xml";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        boolean succes = true;
        try {
            builder = docFactory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLLevelWriter.class
                    .getName()).log(Level.SEVERE, null, ex);
            succes = false;
        }

        Document doc = null;
        Transformer transformer = null;

        if (succes) {
            doc = builder.newDocument();
            createTileDocument(levelData.getCellData(), doc, name   );
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException ex) {
                succes = false;
                Logger
                        .getLogger(XMLLevelWriter.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (succes) {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(writePath + fileName));
            try {
                transformer.transform(source, result);

            } catch (TransformerException ex) {
                Logger.getLogger(XMLLevelWriter.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void createTileDocument(Tile[][] tiles, Document doc, String name) {
        Element rootElement = doc.createElement(name);
        doc.appendChild(rootElement);
        Element xSize = doc.createElement("xSize");
        Element ySize = doc.createElement("ySize");
        rootElement.appendChild(xSize);
        rootElement.appendChild(ySize);

        Tile[][] cells = new Tile[tiles[0].length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                cells[j][i] = tiles[i][j];
            }
        }
        xSize.appendChild(doc.createTextNode(String.valueOf(cells.length)));
        ySize.appendChild(doc.createTextNode(String.valueOf(cells[0].length)));

        for (Tile[] t : cells) {
            Element row = doc.createElement("row");
            rootElement.appendChild(row);

            for (Tile tile : t) {
                Element tileElement = doc.createElement("tile");
                row.appendChild(tileElement);
                tileElement.appendChild(doc.createTextNode(tile.name()));
            }
        }
    }
}
