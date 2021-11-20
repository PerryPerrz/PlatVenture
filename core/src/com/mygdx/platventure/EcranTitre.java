package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

public class EcranTitre extends ScreenAdapter {
    PlatVenture platVenture;
    Texture texture;

    public EcranTitre(PlatVenture platVenture) {
        this.platVenture = platVenture;
        Music musiqueIntro = Gdx.audio.newMusic(Gdx.files.internal("sounds/win.ogg"));
        musiqueIntro.play();
    }

    @Override
    public void show() {
        //Initialisation des données d'affichages.
        this.texture = new Texture("images/Intro.png");
        //Initialisation du timer
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                platVenture.setScreen(new EcranJeu(platVenture));
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
