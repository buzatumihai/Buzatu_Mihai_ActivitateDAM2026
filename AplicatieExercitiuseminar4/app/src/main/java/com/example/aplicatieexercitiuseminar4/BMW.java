package com.example.aplicatieexercitiuseminar4;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class BMW implements Serializable {
    private String nume;
    private int kg;
    private int an;
    private boolean esteSport;
    private String combustibil;

    public BMW() { }

    public BMW(String nume, int kg, int an, boolean esteSport, String combustibil) {
        this.nume = nume;
        this.kg = kg;
        this.an = an;
        this.esteSport = esteSport;
        this.combustibil = combustibil;
    }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public int getKg() { return kg; }
    public void setKg(int kg) { this.kg = kg; }

    public int getAn() { return an; }
    public void setAn(int an) { this.an = an; }

    public boolean isEsteSport() { return esteSport; }
    public void setEsteSport(boolean esteSport) { this.esteSport = esteSport; }

    public String getCombustibil() { return combustibil; }
    public void setCombustibil(String combustibil) { this.combustibil = combustibil; }

    @Override
    public String toString() {
        return "BMW adaugat:\n" +
                "Nume: " + nume +
                "\n" + "Kg: " + kg +
                "\n" + "An: " + an +
                "\n" + "Este Sport: " + esteSport +
                "\n" + "Combustibil: " + combustibil;
    }
}