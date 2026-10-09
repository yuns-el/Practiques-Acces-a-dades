package org.yourcompany.yourproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Pràctica 3: Persistència d'objectes en fitxers binaris.
 * Programa per a la gestió d'un catàleg de videojocs (CRUD complet).
 * 
 * Autor: Younes
 * 
 * --- INVESTIGACIÓ SOBRE LES PROBLEMÀTIQUES DELS FITXERS BINARIS I ObjectOutputStream ---
 * 
 * 1. Incompatibilitat de versions (Serialització):
 *    Si canviem l'estructura de la classe (per exemple, afegim o esborrem un atribut)
 *    després d'haver guardat les dades al fitxer, Java no podrà deserialitzar els objectes
 *    i llançarà una excepció 'InvalidClassException'. Això passa si el 'serialVersionUID' canvia.
 * 
 * 2. Inseguretat:
 *    Deserialitzar fitxers binaris provinents de fonts desconegudes o no de fiar és un risc
 *    de seguretat greu (vulnerabilitat d'execució de codi remot / RCE), ja que durant la
 *    deserialització s'instancien objectes a la memòria.
 * 
 * 3. Manca d'interoperabilitat:
 *    Els fitxers binaris generats amb ObjectOutputStream utilitzen un format específic de Java.
 *    Altres llenguatges de programació (com Python, C++ o JavaScript) no poden llegir directament
 *    aquests fitxers sense llibreries especials o conversions.
 * 
 * 4. Difícil lectura i depuració (No és llegible per humans):
 *    A diferència de formats com JSON, XML o TXT, un fitxer .dat binari no es pot obrir amb un
 *    editor de text normal (com el Bloc de Notes) per veure o modificar les dades manualment.
 */
public class Pt3_Fer_una_app_que_que_permeti_la_persistencia_objectes {

    // Nom del fitxer binari on guardarem la llista de videojocs
    private static final String NOM_FITXER = "videojocs.dat";
    
    // Llista principal on guardarem els videojocs en memòria
    private static ArrayList<Videojoc> llistaVideojocs = new ArrayList<>();
    
    // Scanner per llegir dades de l'usuari des de la consola
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Carreguem els videojocs des del fitxer binari a l'iniciar el programa
        carregarVideojocs();

        boolean sortir = false;
        
        while (!sortir) {
            mostrarMenu();
            System.out.print("Tria una opció (1-6): ");
            String opcioStr = scanner.nextLine().trim();

            switch (opcioStr) {
                case "1":
                    afegirVideojoc();
                    break;
                case "2":
                    llistarVideojocs();
                    break;
                case "3":
                    cercarVideojocPerTitol();
                    break;
                case "4":
                    actualitzarVideojoc();
                    break;
                case "5":
                    eliminarVideojoc();
                    break;
                case "6":
                    // Guardem totes les dades i sortim
                    guardarVideojocs();
                    System.out.println("\nS'han guardat tots els canvis. Fins aviat!");
                    sortir = true;
                    break;
                default:
                    System.out.println("Opció no vàlida! Si us plau, tria un número de l'1 al 6.");
            }
            System.out.println();
        }
    }

    /**
     * Mostra el menú principal de l'aplicació per pantalla.
     */
    private static void mostrarMenu() {
        System.out.println("==========================================");
        System.out.println("      GESTIÓ DE CATÀLEG DE VIDEOJOCS      ");
        System.out.println("==========================================");
        System.out.println("1. Afegir videojoc");
        System.out.println("2. Llistar tots els videojocs");
        System.out.println("3. Cercar videojocs per títol");
        System.out.println("4. Actualitzar un videojoc");
        System.out.println("5. Eliminar un videojoc");
        System.out.println("6. Sortir del programa");
        System.out.println("------------------------------------------");
    }

    /**
     * 1. Afegir un nou videojoc a la llista i desar els canvis al fitxer binari.
     */
    private static void afegirVideojoc() {
        System.out.println("\n--- AFEGIR NOU VIDEOJOC ---");
        
        System.out.print("Títol: ");
        String titol = scanner.nextLine().trim();
        
        System.out.print("Gènere (ex: Acció, Rol, Esport...): ");
        String genere = scanner.nextLine().trim();

        int any = llegirEnter("Any de llançament: ");
        
        System.out.print("Plataforma (ex: PC, PlayStation, Xbox, Switch): ");
        String plataforma = scanner.nextLine().trim();

        double preu = llegirDouble("Preu (€): ");

        // Creem el nou objecte Videojoc
        Videojoc nouVideojoc = new Videojoc(titol, genere, any, plataforma, preu);
        
        // Afegim a la llista
        llistaVideojocs.add(nouVideojoc);
        
        // Desem els canvis directament al fitxer binari
        guardarVideojocs();
        
        System.out.println("-> Videojoc afegit i guardat correctament!");
    }

    /**
     * 2. Llistar tots els videojocs del catàleg.
     */
    private static void llistarVideojocs() {
        System.out.println("\n--- LLISTAT DE VIDEOJOCS ---");
        if (llistaVideojocs.isEmpty()) {
            System.out.println("El catàleg està buit. No hi ha cap videojoc registrat.");
        } else {
            for (int i = 0; i < llistaVideojocs.size(); i++) {
                System.out.println((i + 1) + ". " + llistaVideojocs.get(i));
            }
        }
    }

    /**
     * 3. Cercar videojocs que conguin un text al títol.
     */
    private static void cercarVideojocPerTitol() {
        System.out.println("\n--- CERCAR VIDEOJOC PER TÍTOL ---");
        if (llistaVideojocs.isEmpty()) {
            System.out.println("El catàleg està buit. No es pot fer cap cerca.");
            return;
        }

        System.out.print("Introdueix el text a cercar al títol: ");
        String cerca = scanner.nextLine().trim().toLowerCase();

        boolean trobat = false;
        System.out.println("\nResultats de la cerca:");
        for (int i = 0; i < llistaVideojocs.size(); i++) {
            Videojoc v = llistaVideojocs.get(i);
            // Comprovem si el títol conté el text cercat (sense fer diferència entre majúscules i minúscules)
            if (v.getTitol().toLowerCase().contains(cerca)) {
                System.out.println("[" + (i + 1) + "] " + v);
                trobat = true;
            }
        }

        if (!trobat) {
            System.out.println("No s'ha trobat cap videojoc que me'n coincideixi amb el títol '" + cerca + "'.");
        }
    }

    /**
     * 4. Actualitzar les dades d'un videojoc existent.
     */
    private static void actualitzarVideojoc() {
        System.out.println("\n--- ACTUALITZAR VIDEOJOC ---");
        if (llistaVideojocs.isEmpty()) {
            System.out.println("El catàleg està buit. No hi ha cap videojoc per actualitzar.");
            return;
        }

        llistarVideojocs();
        int opcio = llegirEnter("\nIntrodueix el número del videojoc a modificar: ");

        if (opcio < 1 || opcio > llistaVideojocs.size()) {
            System.out.println("Número de videojoc no vàlid.");
            return;
        }

        Videojoc v = llistaVideojocs.get(opcio - 1);
        System.out.println("\nModificant el videojoc: " + v.getTitol());
        System.out.println("(Prem ENTER sense escriure res si vols mantenir el valor actual)");

        // Nou títol
        System.out.print("Nou títol [" + v.getTitol() + "]: ");
        String nouTitol = scanner.nextLine().trim();
        if (!nouTitol.isEmpty()) {
            v.setTitol(nouTitol);
        }

        // Nou gènere
        System.out.print("Nou gènere [" + v.getGenere() + "]: ");
        String nouGenere = scanner.nextLine().trim();
        if (!nouGenere.isEmpty()) {
            v.setGenere(nouGenere);
        }

        // Nou any
        System.out.print("Nou any de llançament [" + v.getAnyLlancament() + "]: ");
        String anyStr = scanner.nextLine().trim();
        if (!anyStr.isEmpty()) {
            try {
                v.setAnyLlancament(Integer.parseInt(anyStr));
            } catch (NumberFormatException e) {
                System.out.println("Any no vàlidd. Es manté l'any anterior.");
            }
        }

        // Nova plataforma
        System.out.print("Nova plataforma [" + v.getPlataforma() + "]: ");
        String novaPlataforma = scanner.nextLine().trim();
        if (!novaPlataforma.isEmpty()) {
            v.setPlataforma(novaPlataforma);
        }

        // Nou preu
        System.out.print("Nou preu (€) [" + v.getPreu() + "]: ");
        String preuStr = scanner.nextLine().trim();
        if (!preuStr.isEmpty()) {
            try {
                v.setPreu(Double.parseDouble(preuStr));
            } catch (NumberFormatException e) {
                System.out.println("Preu no vàlid. Es manté el preu anterior.");
            }
        }

        // Desem els canvis al fitxer binari
        guardarVideojocs();
        System.out.println("-> Videojoc actualitzat i guardat amb èxit!");
    }

    /**
     * 5. Eliminar un videojoc de la llista i actualitzar el fitxer.
     */
    private static void eliminarVideojoc() {
        System.out.println("\n--- ELIMINAR VIDEOJOC ---");
        if (llistaVideojocs.isEmpty()) {
            System.out.println("El catàleg està buit. No hi ha cap videojoc per eliminar.");
            return;
        }

        llistarVideojocs();
        int opcio = llegirEnter("\nIntrodueix el número del videojoc a eliminar: ");

        if (opcio < 1 || opcio > llistaVideojocs.size()) {
            System.out.println("Número de videojoc no vàlid.");
            return;
        }

        Videojoc eliminat = llistaVideojocs.remove(opcio - 1);
        
        // Desem els canvis al fitxer binari
        guardarVideojocs();
        System.out.println("-> S'ha eliminat el videojoc '" + eliminat.getTitol() + "' del catàleg.");
    }

    /**
     * Carrega la llista de videojocs des del fitxer binari 'videojocs.dat'.
     * Utilitza ObjectInputStream per llegir els objectes.
     */
    @SuppressWarnings("unchecked")
    private static void carregarVideojocs() {
        File fitxer = new File(NOM_FITXER);
        
        // Si el fitxer no existeix, creem una nova llista buida
        if (!fitxer.exists()) {
            System.out.println("Fitxer '" + NOM_FITXER + "' no trobat. Se'n crearà un de nou al guardar.");
            llistaVideojocs = new ArrayList<>();
            return;
        }

        // Utilitzem un bloc try-with-resources per tancar automàticament els recursos
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
            
            // Llegim l'objecte i fem un cast a ArrayList<Videojoc>
            llistaVideojocs = (ArrayList<Videojoc>) ois.readObject();
            System.out.println("Dades carregades correctament des de '" + NOM_FITXER + "' (" + llistaVideojocs.size() + " videojocs).");
            
        } catch (Exception e) {
            System.out.println("Atenció: No s'han pogut carregar les dades del fitxer binari: " + e.getMessage());
            System.out.println("S'inicialitza un catàleg buit.");
            llistaVideojocs = new ArrayList<>();
        }
    }

    /**
     * Desa la llista de videojocs completa al fitxer binari 'videojocs.dat'.
     * Utilitza ObjectOutputStream per escriure l'objecte ArrayList.
     */
    private static void guardarVideojocs() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOM_FITXER))) {
            
            // Escrivim la llista completa d'objectes Videojoc
            oos.writeObject(llistaVideojocs);
            
        } catch (IOException e) {
            System.out.println("Error en guardar les dades al fitxer binari: " + e.getMessage());
        }
    }

    /**
     * Helper per llegir un número enter de manera segura evitant la línia buida del Scanner.
     */
    private static int llegirEnter(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no vàlida. Introdueix un número enter.");
            }
        }
    }

    /**
     * Helper per llegir un número decimal (double) de manera segura.
     */
    private static double llegirDouble(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no vàlida. Introdueix un número decimal (ex: 29.99).");
            }
        }
    }
}
