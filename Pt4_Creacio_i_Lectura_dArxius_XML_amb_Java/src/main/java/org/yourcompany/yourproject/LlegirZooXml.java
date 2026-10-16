package org.yourcompany.yourproject;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LlegirZooXml {

    public static void main(String[] args) throws Exception {
        Path fitxer = args.length > 0 ? Paths.get(args[0]) : Paths.get("zoo.xml");
        llegirXml(fitxer);
    }

    public static void llegirXml(Path fitxer) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentFactory.setXIncludeAware(false);
        documentFactory.setExpandEntityReferences(false);

        Document document = documentFactory.newDocumentBuilder().parse(fitxer.toFile());
        NodeList animals = document.getDocumentElement().getElementsByTagName("animal");

        System.out.println("Nombre total d'animals: " + animals.getLength());
        System.out.println();

        for (int i = 0; i < animals.getLength(); i++) {
            Element animal = (Element) animals.item(i);
            String aliment = obtenirText(animal, "aliment");

            System.out.println("Animal #" + animal.getAttribute("id"));
            System.out.println("Nom: " + obtenirText(animal, "nom"));
            System.out.println("Espècie: " + obtenirText(animal, "especie"));
            System.out.println("Aliment: " + aliment);
            System.out.println("Edat: " + obtenirText(animal, "edat"));
            System.out.println();
        }

        System.out.println("Animals que mengen Carn:");
        boolean hiHaCarnivors = false;
        for (int i = 0; i < animals.getLength(); i++) {
            Element animal = (Element) animals.item(i);
            if ("carn".equalsIgnoreCase(obtenirText(animal, "aliment").trim())) {
                System.out.println("- " + obtenirText(animal, "nom"));
                hiHaCarnivors = true;
            }
        }
        if (!hiHaCarnivors) {
            System.out.println("No n'hi ha cap.");
        }
    }

    private static String obtenirText(Element pare, String nomElement) {
        return pare.getElementsByTagName(nomElement).item(0).getTextContent();
    }
}
