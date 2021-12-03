package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GestionnaireSons {
    private final Sound sonWin;
    private final Sound sonGemmes;
    private final Sound sonAlert;
    private final Sound sonCollision;
    private final Sound sonDefaite;
    private final Sound sonPlouf;

    public GestionnaireSons() {
        this.sonWin = Gdx.audio.newSound(Gdx.files.internal("sounds/win.ogg"));
        this.sonGemmes = Gdx.audio.newSound(Gdx.files.internal("sounds/gem.ogg"));
        this.sonAlert = Gdx.audio.newSound(Gdx.files.internal("sounds/alert.ogg"));
        this.sonCollision = Gdx.audio.newSound(Gdx.files.internal("sounds/collision.ogg"));
        this.sonDefaite = Gdx.audio.newSound(Gdx.files.internal("sounds/loose.ogg"));
        this.sonPlouf = Gdx.audio.newSound(Gdx.files.internal("sounds/plouf.ogg"));
    }

    public void jouerSonWin() {
        this.sonWin.play();
    }

    public void jouerSonGemmes() {
        this.sonGemmes.play();
    }

    public void jouerSonAlert() {
        this.sonAlert.play();
    }

    public void jouerSonCollision() {
        this.sonCollision.play();
    }

    public void jouerSonDefaite() {
        this.sonDefaite.play();
    }

    public void jouerSonPlouf() {
        this.sonPlouf.play();
    }
}
