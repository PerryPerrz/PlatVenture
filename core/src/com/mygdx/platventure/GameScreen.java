package com.mygdx.platventure;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    PlatVenture platVenture;
    Texture texture;

    public GameScreen(PlatVenture platVenture) {
        this.platVenture = platVenture;
    }

    @Override
    public void show() {
        //Initialisation des données d'affichages.
        this.texture = new Texture("images/Back.png");

    }

    @Override
    public void render(float delta) {
        //Raffraichissement de l'affichage.
        ScreenUtils.clear(0, 0, 0, 0);
        this.platVenture.batch.begin();
        this.platVenture.batch.draw(this.texture, 0, 0);
        this.platVenture.batch.end();
    }
}
