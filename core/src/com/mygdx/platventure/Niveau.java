package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Niveau {
    private final int largeur;
    private final int hauteur;
    private final int temps;
    private String imageDeFond;
    private final char[][] tabNiveau;

    public Niveau(String nomDeNiveau) {
        FileHandle file = Gdx.files.internal(nomDeNiveau);

        String[] data = file.readString().split("\n"); //On split par ligne.
        String[] text = data[0].split(" "); //Tableau qui récupère la largeur la hauteur et le temps. (On split la première ligne avec l'espace)

        this.largeur = Integer.parseInt(text[0]);
        this.hauteur = Integer.parseInt(text[1]);
        this.temps = Integer.parseInt(text[2]);

        tabNiveau = new char[largeur][hauteur];
        for (int i = 1; i < hauteur + 1; i++) {
            for (int j = 0; j < largeur; j++) {
                tabNiveau[j][i - 1] = data[i].charAt(j);
            }
        }
    }

    public int getLargeur() {
        return this.largeur;
    }

    public int getHauteur() {
        return this.hauteur;
    }

    public char[][] getTabNiveau() {
        return this.tabNiveau;
    }

    public int getTemps() {
        return this.temps;
    }

    public void dispose() {

    }
}
