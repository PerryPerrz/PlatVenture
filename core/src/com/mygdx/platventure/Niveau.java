package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Niveau {
    private int largeur;
    private int hauteur;
    private int temps;
    private String imageDeFond;

    public Niveau(String nomDeNiveau) {
        FileHandle file = Gdx.files.internal(nomDeNiveau);
        String text = file.readString();

        System.out.println(text);
    }
}
