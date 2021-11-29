package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.platventure.PlatVenture;
import com.mygdx.platventure.ecrans.EcranJeu;

public class EcranTitre extends ScreenAdapter {
    PlatVenture platVenture;
    private Texture texture;

    public EcranTitre(PlatVenture platVenture) {
        this.platVenture = platVenture;
    }

    @Override
    public void show() {
        //Initialisation des données d'affichages.
        this.texture = new Texture("images/Intro.png");
        Music musiqueIntro = Gdx.audio.newMusic(Gdx.files.internal("sounds/win.ogg"));
        musiqueIntro.play();
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
        this.platVenture.getBatch().begin();
        this.platVenture.getBatch().draw(this.texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.platVenture.getBatch().end();
    }
}
