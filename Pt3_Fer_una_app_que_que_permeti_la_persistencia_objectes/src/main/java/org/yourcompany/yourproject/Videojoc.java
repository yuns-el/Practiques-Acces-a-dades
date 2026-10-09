package org.yourcompany.yourproject;

import java.io.Serializable;

/**
 * Classe que representa un videojoc del catàleg.
 * Implementa la interfície Serializable per poder ser guardat en un fitxer binari.
 * 
 * @author Younes
 */
public class Videojoc implements Serializable {

    // Versió de serialització per evitar problemes de compatibilitat
    private static final long serialVersionUID = 1L;

    // Atributs principals del videojoc
    private String titol;
    private String genere;
    private int anyLlancament;
    private String plataforma;
    private double preu;

    // Constructor buit
    public Videojoc() {
    }

    // Constructor amb tots els atributs
    public Videojoc(String titol, String genere, int anyLlancament, String plataforma, double preu) {
        this.titol = titol;
        this.genere = genere;
        this.anyLlancament = anyLlancament;
        this.plataforma = plataforma;
        this.preu = preu;
    }

    // Getters i Setters
    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getAnyLlancament() {
        return anyLlancament;
    }

    public void setAnyLlancament(int anyLlancament) {
        this.anyLlancament = anyLlancament;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    // Mètode toString per mostrar la informació del videojoc d'una forma ben formatada
    @Override
    public String toString() {
        return "Títol: '" + titol + '\'' +
               ", Gènere: '" + genere + '\'' +
               ", Any: " + anyLlancament +
               ", Plataforma: '" + plataforma + '\'' +
               ", Preu: " + preu + "€";
    }
}
