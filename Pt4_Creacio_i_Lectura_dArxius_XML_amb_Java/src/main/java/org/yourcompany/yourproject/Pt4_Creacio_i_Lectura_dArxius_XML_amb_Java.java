package org.yourcompany.yourproject;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Pt4_Creacio_i_Lectura_dArxius_XML_amb_Java {

    public static void main(String[] args) throws Exception {
        Path fitxer = args.length > 0 ? Paths.get(args[0]) : Paths.get("zoo.xml");
        CrearZooXml.crearXml(fitxer);
        System.out.println("Fitxer creat: " + fitxer.toAbsolutePath());
        System.out.println();
        LlegirZooXml.llegirXml(fitxer);
    }
}
