package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.platventure.EnumTypeBody;
import com.mygdx.platventure.Monde;
import com.mygdx.platventure.PlatVenture;
import com.mygdx.platventure.ecouteurs.EcouteurEcranJeu;
import com.mygdx.platventure.elements.Element;

import java.util.Arrays;

public class EcranJeu extends ScreenAdapter {
    private final PlatVenture platVenture;
    private Texture texture;
    private final OrthographicCamera camera;
    private final FitViewport vp;
    private final Monde monde;
    //private final Box2DDebugRenderer debug;
    private final EcouteurEcranJeu mouvementJoueur;
    private BitmapFont font;

    public EcranJeu(PlatVenture platVenture) {
        this.platVenture = platVenture;
        this.monde = new Monde();
        //this.debug = new Box2DDebugRenderer();
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
        this.defineTextFont();

        //On dit à l'écran de jeu que cet écouteur l'écoute.
        Gdx.input.setInputProcessor(this.mouvementJoueur);
    }

    @Override
    public void render(float delta) { //Raffraichit l'affichage tous les x temps
        if (!this.monde.isJeuEstEnPause()) { //Dès que le joueur est en pause, on stop le monde/ne raffraichit pas le monde.
            //On applique la force sur le personnage.
            this.monde.getPersonnage().appliquerForce(this.mouvementJoueur.getForce());

            //On met à jour les élements graphiques du monde par rapport à leurs données
            this.monde.update();
            //Définition du step du monde
            monde.getMonde().step(Gdx.graphics.getDeltaTime(), 6, 2);
        }

        //On positionne la caméra selon l'emplacement du personnage.
        this.positionnerCameraPersonnage();
        //camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        this.platVenture.getBatch().setProjectionMatrix(camera.combined);

        //Raffraichissement de l'affichage.
        ScreenUtils.clear(0, 0, 0, 0);
        this.platVenture.getBatch().begin();
        this.platVenture.getBatch().draw(this.texture, 0, 0, this.monde.getNiveau().getLargeur(), this.monde.getNiveau().getHauteur());

        //On parcourt tous les élements, puis on les draw/affichent
        for (Element e : monde.getElementsDuMonde()) {
            if (e != null) {
                if (e.getBody().getUserData() == EnumTypeBody.PERSONNAGE) //à cause de la forme du body du perso, le perso est décalé de 0.25, il faut donc le recaler. (on fixe la texture sur le body)
                    platVenture.getBatch().draw(e.getTexture(), e.getPosition().x + 0.25f, e.getPosition().y, e.getLargeur(), e.getHauteur());
                else
                    platVenture.getBatch().draw(e.getTexture(), e.getPosition().x, e.getPosition().y, e.getLargeur(), e.getHauteur());
            }
        }

        //Mode debug
        //this.debug.render(this.monde.getMonde(), camera.combined);

        this.platVenture.getBatch().end();
    }

    @Override
    public void resize(int width, int height) { //Fonction appelée lorsque l'utilisateur change la taille de la fenêtre, et ce fait tout au début, lors de la création de la fenêtre.
        vp.update(width, height); //Maj ViewPort
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); //On met la position de la cam
        camera.update(); //Maj Caméra

        platVenture.getBatch().setProjectionMatrix(camera.combined); //Ré-applique la cam au spriteBatch
    }

    public void positionnerCameraPersonnage() {
        float coordPersoX = this.monde.getPersonnage().getPosition().x;
        float coordPersoY = this.monde.getPersonnage().getPosition().y;

        //Si la caméra est collée au mur de gauche, et que le joueur est à gauche du centre de la caméra, alors la caméera ne bouge pas.
        //Si la caméra est collée au mur de gauche, et que le joueur est à droite du centre de la caméra, alors, on fixe la caméra au joueur.
        //Dimension du viewPort de la caméra = dimension de la caméra.
        if (!this.monde.isPersoVientDeSpawn()) {
            //La caméra suit le personnage lorque celui-ci se déplace vers la gauche.
            if (this.camera.position.x - this.camera.viewportWidth / 2 > 0 && coordPersoX < this.camera.position.x) { //Si la caméra n'est pas collée au mur de gauche et si le joueur se déplace vers le mur de gauche, on fixe la caméra sur le joueur.
                //On rapproche la caméra du personnage, si l'on centre la caméra sur le joueur, la caméra sort de la map...
                this.camera.position.set(coordPersoX, this.camera.position.y, 0);
            }
            //La caméra suit le personnage lorque celui-ci se déplace vers la droite.
            if (this.camera.position.x + this.camera.viewportWidth / 2 < this.monde.getNiveau().getLargeur() && coordPersoX > this.camera.position.x) { //Si la caméra n'est pas collée au mur de droite et si le joueur se déplace vers le mur de droite, on fixe la caméra sur le joueur.
                //On rapproche la caméra du personnage, si l'on centre la caméra sur le joueur, la caméra sort de la map...
                this.camera.position.set(coordPersoX, this.camera.position.y, 0);
            }
            //La caméra suit le personnage lorque celui-ci se déplace vers le haut.
            if (this.camera.position.y + this.camera.viewportHeight / 2 < this.monde.getNiveau().getHauteur() && coordPersoY > this.camera.position.y) { //Si la caméra n'est pas collée au mur de gauche et si le joueur se déplace vers le mur de gauche, on fixe la caméra sur le joueur.
                //On rapproche la caméra du personnage, si l'on centre la caméra sur le joueur, la caméra sort de la map...
                this.camera.position.set(this.camera.position.x, coordPersoY, 0);
            }
            //La caméra suit le personnage lorque celui-ci se déplace vers le bas.
            if (this.camera.position.y - this.camera.viewportHeight / 2 > 0 && coordPersoY < this.camera.position.y) { //Si la caméra n'est pas collée au mur de gauche et si le joueur se déplace vers le mur de gauche, on fixe la caméra sur le joueur.
                //On rapproche la caméra du personnage, si l'on centre la caméra sur le joueur, la caméra sort de la map...
                this.camera.position.set(this.camera.position.x, coordPersoY, 0);
            }
            //Maj de la caméra.
            camera.update();
        }
        //Si le personnage vient de spawn, on donne à la caméra les coordonnées de base de celle-ci et on dit que le personnage à spawn.
        else {
            this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
            this.monde.setPersoVientDeSpawn(false);
        }
    }

    private void renderAffichageTexte() {
        GlyphLayout glyphLayout = new GlyphLayout();
        String score = "Score : " + this.monde.getScore();
        String temps = "" + Arrays.toString(this.monde.getTempsRestant());
        this.font = new BitmapFont();

        //On applique la font au temps.
        glyphLayout.setText(font, temps);
        font.draw(this.platVenture.getBatch(), glyphLayout, -glyphLayout.width / 2, this.camera.viewportHeight / 2 - 10);

        //On applique la font au score.
        glyphLayout.setText(font, score);
        font.draw(this.platVenture.getBatch(), glyphLayout, this.camera.viewportWidth / 2 - glyphLayout.width, this.camera.viewportHeight / 2 - 10);


    }

    private void defineTextFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParam = new FreeTypeFontGenerator.FreeTypeFontParameter();

        //On fixe les param de la font
        fontParam.size = (int) (60f / 1024f * Gdx.graphics.getWidth());
        fontParam.color = Color.YELLOW;
        fontParam.borderColor = Color.BLACK;
        fontParam.borderWidth = 3f / 1024f * Gdx.graphics.getWidth();

        font = fontGenerator.generateFont(fontParam);

        fontGenerator.dispose();
    }
}
