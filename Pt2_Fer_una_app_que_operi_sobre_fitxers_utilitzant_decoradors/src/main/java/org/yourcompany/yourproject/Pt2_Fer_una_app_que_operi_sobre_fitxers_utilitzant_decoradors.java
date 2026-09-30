package org.yourcompany.yourproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Pt2_Fer_una_app_que_operi_sobre_fitxers_utilitzant_decoradors {

    private static String resoldreRutaFitxer(String nomFitxer) {
        File fitxerDirecte = new File(nomFitxer);
        if (fitxerDirecte.exists()) {
            return fitxerDirecte.getAbsolutePath();
        }

        String nomProjecte = "Pt2_Fer_una_app_que_operi_sobre_fitxers_utilitzant_decoradors";
        File fitxerProjecte = new File(System.getProperty("user.dir"), nomProjecte + File.separator + nomFitxer);
        if (fitxerProjecte.exists()) {
            return fitxerProjecte.getAbsolutePath();
        }

        File fitxerActual = new File(System.getProperty("user.dir"), nomFitxer);
        if (fitxerActual.exists()) {
            return fitxerActual.getAbsolutePath();
        }

        return new File(nomProjecte, nomFitxer).getAbsolutePath();
    }

    public static void xifrarFitxer(String inputFile, String outputFile, int desplaçament) {
        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String linia;
            System.out.println("[INFO] Iniciant procés de xifrat...");
            
            while ((linia = br.readLine()) != null) {
                String liniaInvertida = new StringBuilder(linia).reverse().toString();
                
                StringBuilder liniaXifrada = new StringBuilder();
                for (int i = 0; i < liniaInvertida.length(); i++) {
                    char c = liniaInvertida.charAt(i);
                    liniaXifrada.append((char) (c + desplaçament));
                }
                
                bw.write(liniaXifrada.toString());
                bw.newLine();
            }
            System.out.println("[SUCCESS] Fitxer xifrat correctament a: " + outputFile);
            
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] Fitxer no trobat: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[ERROR] Error d'entrada/sortida: " + e.getMessage());
        }
    }

    public static void desxifrarFitxer(String inputFile, String outputFile, int desplaçament) {
        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String linia;
            System.out.println("[INFO] Iniciant procés de desxifrat...");
            
            while ((linia = br.readLine()) != null) {
                StringBuilder liniaDesplaçada = new StringBuilder();
                for (int i = 0; i < linia.length(); i++) {
                    char c = linia.charAt(i);
                    liniaDesplaçada.append((char) (c - desplaçament));
                }
                
                String liniaOriginal = liniaDesplaçada.reverse().toString();
                
                bw.write(liniaOriginal);
                bw.newLine();
            }
            System.out.println("[SUCCESS] Fitxer desxifrat correctament a: " + outputFile);
            
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] Fitxer no trobat: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[ERROR] Error d'entrada/sortida: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String entrada = resoldreRutaFitxer("entrada.txt");
        String xifrat = resoldreRutaFitxer("xifrat.txt");
        String desxifrat = resoldreRutaFitxer("desxifrat.txt");

        System.out.print("Introdueix el nombre de desplaçament (clau Cèsar, ex: 3): ");
        int clau = 3;
        if (scanner.hasNextInt()) {
            clau = scanner.nextInt();
        } else {
            System.out.println("[WARN] Valor invàlid. S'utilitzarà la clau per defecte: 3");
        }
        scanner.close();

        xifrarFitxer(entrada, xifrat, clau);

        desxifrarFitxer(xifrat, desxifrat, clau);
    }
}