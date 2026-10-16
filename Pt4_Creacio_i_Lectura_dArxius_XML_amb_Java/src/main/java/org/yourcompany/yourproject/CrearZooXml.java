package org.yourcompany.yourproject;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CrearZooXml {

    private record Animal(int id, String nom, String especie, String aliment, int edat) {
    }

    public static void main(String[] args) throws Exception {
        Path fitxer = args.length > 0 ? Paths.get(args[0]) : Paths.get("zoo.xml");
        crearXml(fitxer);
        System.out.println("Fitxer creat: " + fitxer.toAbsolutePath());
    }

    public static void crearXml(Path fitxer) throws Exception {
        Animal[] animals = {
            new Animal(1, "Pingüí", "Aptenodytes forsteri", "Peix", 5),
            new Animal(2, "Lleó", "Panthera leo", "Carn", 8)
        };

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Document document = documentFactory.newDocumentBuilder().newDocument();

        Element zoo = document.createElement("zoo");
        document.appendChild(zoo);

        for (Animal animal : animals) {
            Element animalElement = document.createElement("animal");
            animalElement.setAttribute("id", Integer.toString(animal.id()));
            zoo.appendChild(animalElement);

            afegirElement(document, animalElement, "nom", animal.nom());
            afegirElement(document, animalElement, "especie", animal.especie());
            afegirElement(document, animalElement, "aliment", animal.aliment());
            afegirElement(document, animalElement, "edat", Integer.toString(animal.edat()));
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        var transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(document), new StreamResult(fitxer.toFile()));
    }

    private static void afegirElement(Document document, Element pare, String nom, String valor) {
        Element element = document.createElement(nom);
        element.setTextContent(valor);
        pare.appendChild(element);
    }
}
