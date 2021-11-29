package com.mygdx.platventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class EcranJeu extends ScreenAdapter {
    private final PlatVenture platVenture;
    private Texture texture;
    private OrthographicCamera camera;
    private FitViewport vp;

    public EcranJeu(PlatVenture platVenture) {
        this.platVenture = platVenture;
        this.platVenture.setNiveau(new Niveau("levels/level_001.txt"));

        int imL = Gdx.graphics.getWidth();
        int imH = Gdx.graphics.getHeight();

        //Initialisation de la Caméra et du ViewPort
        camera = new OrthographicCamera();
        vp = new FitViewport(16f, (16f * imH) / imL, camera);
        //On met ce ViewPort sur la Caméra
        vp.apply();
    }

    @Override
    public void show() {
        //Initialisation des données d'affichages.
        this.texture = new Texture("images/Back.png");
    }

    @Override
    public void render(float delta) {
        //Mise en place de la caméra
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        this.platVenture.getBatch().setProjectionMatrix(camera.combined);

        //Raffraichissement de l'affichage.
        ScreenUtils.clear(0, 0, 0, 0);
        this.platVenture.getBatch().begin();
        this.platVenture.getBatch().draw(this.texture, 0, 0, platVenture.getNiveau().getLargeur(), platVenture.getNiveau().getHauteur());
        this.platVenture.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        vp.update(width, height); //Maj ViewPort
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); //On met la position de la cam
        camera.update(); //Maj Caméra

        platVenture.getBatch().setProjectionMatrix(camera.combined); //Ré-applique la cam au spriteBatch
    }
}
