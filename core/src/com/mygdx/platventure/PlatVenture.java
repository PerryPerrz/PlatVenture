package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlatVenture extends Game {
    //Classe qui gère les données communes à tous les écrans et lance le jeu.
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new EcranTitre(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }
}
