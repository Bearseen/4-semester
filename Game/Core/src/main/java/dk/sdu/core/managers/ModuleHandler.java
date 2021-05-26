/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.core.managers;

import dk.sdu.core.gameStates.Component;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Samuel
 */
public class ModuleHandler {
    private static ModuleHandler singleton_instance = null;

    private static String updateXML = "/Users/Samuel/NetBeansProjects/4-semester/netbeans_site/updates.xml";
    private String moduleInclude = "dk.sdu";
    private static NodeList nodeList;
    private static ArrayList<Component> components;
    private static Document doc;

    private ModuleHandler() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(this.updateXML);
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//module[contains(@codenamebase, '" + this.moduleInclude + "')]");
        ModuleHandler.nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        ModuleHandler.components = new ArrayList<>();

        for (int i = 0; i < ModuleHandler.nodeList.getLength(); i++) {
            if (!isNodeInvalid(ModuleHandler.nodeList.item(i))) {

                Element e = (Element) ModuleHandler.nodeList.item(i);
                String name = e.getAttribute("codenamebase").replace("dk.sdu.", "");
                Node node = ModuleHandler.nodeList.item(i);
                Component component = new Component(name, true, node, node.getParentNode());
                ModuleHandler.components.add(component);
            }
        }
    }

    public static ArrayList<Component> getComponents() {
        return ModuleHandler.components;
    }

    public static ModuleHandler getInstance() {
        if (singleton_instance == null) {
            try {
                singleton_instance = new ModuleHandler();
            } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        return singleton_instance;
    }

    public static void unloadComponent(Component component) {
        System.out.println("Unloading component : " + component.getName());
        component.setActive(false);

        Node node = component.getNode().cloneNode(true);
        component.getParent().removeChild(component.getNode());
        component.setNode(node);
        saveXMLFile();

    }

    public static void loadComponent(Component component) {
        System.out.println("Loading component : " + component.getName());
        component.setActive(true);

        Node node = component.getNode();
        component.getParent().appendChild(node);
        saveXMLFile();
    }

    private static void saveXMLFile() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Source source = new DOMSource(doc);
            Result result = new StreamResult(new FileOutputStream(updateXML));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            Exceptions.printStackTrace(e);
        } catch (TransformerException | FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private static boolean isNodeInvalid(Node node) {
        String[] excludedModules = {"Common", "SilentUpdate", "branding", "Map", "Core"};
        Element e = (Element) node;
        String name = e.getAttribute("codenamebase");

        for (String string : excludedModules) {
            if (name.contains(string)) {
                return true;
            }
        }
        return false;
    }
    
}
