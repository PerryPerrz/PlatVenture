package com.mygdx.platventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlatVenture extends Game {
    //Classe qui gère les données communes à tous les écrans et lance le jeu.
    SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new TitleScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
