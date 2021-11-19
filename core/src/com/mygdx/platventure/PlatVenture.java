package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlatVenture extends Game {
    //Classe qui gère les données communes à tous les écrans et lance le jeu.
    SpriteBatch batch;
    Niveau niveau;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new EcranTitre(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
}
