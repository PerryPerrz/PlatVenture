package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.platventure.Monde;
import com.mygdx.platventure.PlatVenture;
import com.mygdx.platventure.ecouteurs.EcouteurEcranJeu;

public class EcranJeu extends ScreenAdapter {
    private final PlatVenture platVenture;
    private Texture texture;
    private final OrthographicCamera camera;
    private final FitViewport vp;
    private final Monde monde;
    private final Box2DDebugRenderer debug;
    private final EcouteurEcranJeu mouvementJoueur;

    public EcranJeu(PlatVenture platVenture) {
        this.platVenture = platVenture;
        this.monde = new Monde();
        this.debug = new Box2DDebugRenderer();
        this.mouvementJoueur = new EcouteurEcranJeu();

        int imL = Gdx.graphics.getWidth();
        int imH = Gdx.graphics.getHeight();

        //Initialisation de la Caméra et du ViewPort
        camera = new OrthographicCamera();
        vp = new FitViewport(16f, (16f * imH) / imL, camera);
        //On met ce ViewPort sur la Caméra
        vp.apply();
    }

    @Override
    public void show() { //Quand les données sur l'écran apparaissent, au début de l'appli (ce fait une seule fois)
        //Initialisation des données d'affichages.
        this.texture = new Texture("images/Back.png");

        //On dit à l'écran de jeu que cet écouteur l'écoute.
        Gdx.input.setInputProcessor(this.mouvementJoueur);
    }

    @Override
    public void render(float delta) { //Raffraichit l'affichage tous les x temps
        //On applique la force sur le personnage.
        this.monde.getPersonnage().appliquerForce(this.mouvementJoueur.getForce());

        //On met à jour les élements graphiques du monde par rapport à leurs données
        this.monde.update();
        //Définition du step du monde
        monde.getMonde().step(Gdx.graphics.getDeltaTime(), 6, 2);

        //Mise en place de la caméra.
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        this.platVenture.getBatch().setProjectionMatrix(camera.combined);

        //Raffraichissement de l'affichage.
        ScreenUtils.clear(0, 0, 0, 0);
        this.platVenture.getBatch().begin();
        //this.platVenture.getBatch().draw(this.texture, 0, 0, this.niveau.getLargeur(), this.niveau.getHauteur());

        //Mode debug
        this.debug.render(this.monde.getMonde(), camera.combined);

        this.platVenture.getBatch().end();
    }

    @Override
    public void resize(int width, int height) { //Fonction appelée lorsque l'utilisateur change la taille de la fenêtre, et ce fait tout au début, lors de la création de la fenêtre.
        vp.update(width, height); //Maj ViewPort
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); //On met la position de la cam
        camera.update(); //Maj Caméra

        platVenture.getBatch().setProjectionMatrix(camera.combined); //Ré-applique la cam au spriteBatch
    }
}
