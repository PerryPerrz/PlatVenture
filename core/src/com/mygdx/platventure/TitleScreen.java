package com.mygdx.platventure;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

public class TitleScreen extends ScreenAdapter {
    PlatVenture platVenture;
    Texture texture;

    public TitleScreen(PlatVenture platVenture) {
        this.platVenture = platVenture;
    }

    @Override
    public void show() {
        //Initialisation des données d'affichages.
        this.texture = new Texture("images/Intro.png");
        //Initialisation du timer
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                platVenture.setScreen(new GameScreen(platVenture));
            }
        }, 3);
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
