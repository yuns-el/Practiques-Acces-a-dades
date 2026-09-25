package org.yourcompany.yourproject;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Pt1_Fer_una_app_que_operi_sobre_fitxers_de_text {
    public static void main(String[] args) {
        String fitxer = "text.txt";
        
        int totalCaracters = 0;
        int totalLinies = 0;
        int totalParaules = 0;
        
        Map<Character, Integer> frequencia = new HashMap<>();
        
        boolean dinsParaula = false;
        boolean fitxerBuit = true;

        try (FileReader fr = new FileReader(fitxer)) {
            int valor;
            
            while ((valor = fr.read()) != -1) {
                char c = (char) valor;
                fitxerBuit = false;
                
                if (c == '\n') {
                    totalLinies++;
                }
                
                if (c != '\n' && c != '\r') {
                    totalCaracters++;
                    
                    if (c != ' ' && c != '\t') {
                        frequencia.put(c, frequencia.getOrDefault(c, 0) + 1);
                    }
                }
                
                if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                    dinsParaula = false;
                } else {
                    if (!dinsParaula) {
                        totalParaules++;
                        dinsParaula = true;
                    }
                }
            }
            
            if (!fitxerBuit) {
                totalLinies++; 
            }

        } catch (IOException e) {
            System.out.println("Error en llegir el fitxer: " + e.getMessage());
        }

        char caracterMesRepetit = ' ';
        int maxFreq = 0;
        for (Map.Entry<Character, Integer> entry : frequencia.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                caracterMesRepetit = entry.getKey();
            }
        }

        System.out.println("Nombre de caràcters: " + totalCaracters);
        System.out.println("Nombre de línies: " + totalLinies);
        System.out.println("Nombre de paraules: " + totalParaules);
        if (!fitxerBuit && maxFreq > 0) {
            System.out.println("Caràcter més repetit: " + caracterMesRepetit);
        } else {
            System.out.println("El fitxer està buit o no conté caràcters vàlids.");
        }
    }
}
