package com.mygdx.platventure.ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.platventure.Monde;
import com.mygdx.platventure.PlatVenture;
import com.mygdx.platventure.ecouteurs.EcouteurEcranJeu;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.EnumTypeBody;
import com.mygdx.platventure.elements.gemmes.Gemmes;

public class EcranJeu extends ScreenAdapter {
    private final PlatVenture platVenture;
    private Texture texture;
    private final OrthographicCamera camera; //Caméra qui utilise la taille du monde virtuelle.
    private final FitViewport vp;
    private final Monde monde;
    private final Box2DDebugRenderer debug;
    private final EcouteurEcranJeu mouvementJoueur;
    private BitmapFont font;
    private final OrthographicCamera cameraTexte; //Nouvelle caméra car si on utilise l'autre caméra pour afficher le texte, étant donné la taille de l'autre caméra, le texte est beaucoup trop grand.
    private int cptChangementAnimation; //Compteur incrémenté à chaque render, au bout d'un certain nombre, il change l'animation de la gemme.
    private int numSpriteGemmes;

    public EcranJeu(PlatVenture platVenture) {
        this.platVenture = platVenture;
        this.monde = new Monde();
        this.debug = new Box2DDebugRenderer();
        this.mouvementJoueur = new EcouteurEcranJeu();
        int imL = Gdx.graphics.getWidth();
        int imH = Gdx.graphics.getHeight();

        //Initialisation de la caméra et du ViewPort
        camera = new OrthographicCamera();
        vp = new FitViewport(16f, (16f * imH) / imL, camera);
        //On met ce ViewPort sur la Caméra
        vp.apply();

        //Initialisation de la caméra du texte.
        cameraTexte = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //On lui donne la taille de l'écran

        this.cptChangementAnimation = 0;
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
        if (this.monde.isEstAuNiveau3()) {
            camera.position.set(this.monde.getNiveau().getLargeur() / 2f, this.monde.getNiveau().getHauteur() / 2f, 0);
            camera.update();
        } else {
            this.positionnerCameraPersonnage();
        }
        //camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        this.platVenture.getBatch().setProjectionMatrix(camera.combined);

        //Raffraichissement de l'affichage.
        ScreenUtils.clear(0, 0, 0, 0);
        this.platVenture.getBatch().begin();
        if (!this.mouvementJoueur.isDebugMode()) {
            this.platVenture.getBatch().draw(this.texture, 0, 0, this.monde.getNiveau().getLargeur(), this.monde.getNiveau().getHauteur());

            //On parcourt tous les élements, puis on les draw/affichent
            for (Element e : monde.getElementsDuMonde()) {
                if (e != null) {
                    if (e.getBody().getUserData() == EnumTypeBody.PERSONNAGE) {
                        if (e.getBody().getLinearVelocity().x < 0) { //Le joueur va vers la gauche.
                            platVenture.getBatch().draw(e.getTexture(), e.getPosition().x + 0.25f + e.getLargeur(), e.getPosition().y, -e.getLargeur(), e.getHauteur()); //à cause de la forme du body du perso, le perso est décalé de 0.25, il faut donc le recaler. (on fixe la texture sur le body)
                        } else { //Le joueur va vers la droite.
                            platVenture.getBatch().draw(e.getTexture(), e.getPosition().x + 0.25f, e.getPosition().y, e.getLargeur(), e.getHauteur()); //à cause de la forme du body du perso, le perso est décalé de 0.25, il faut donc le recaler. (on fixe la texture sur le body)
                        }

                    } else if (e.getBody().getUserData() == EnumTypeBody.GEMMES) {
                        TextureRegion textureRegion = new TextureRegion((TextureRegion) (((Gemmes) e).getAnimation().getKeyFrame(this.numSpriteGemmes, true)));
                        platVenture.getBatch().draw(textureRegion, e.getPosition().x + 0.25f, e.getPosition().y + 0.25f, e.getLargeur(), e.getHauteur());

                        if (this.cptChangementAnimation > 100) {
                            this.cptChangementAnimation = 0;

                            if (this.numSpriteGemmes > 5) {
                                this.numSpriteGemmes = 0;
                            } else {
                                this.numSpriteGemmes++;
                            }

                        } else {
                            this.cptChangementAnimation++;
                        }
                    } else if (e.getBody().getUserData() == EnumTypeBody.SORTIE) {
                        //Si la pancarte est sur le mur de gauche, on l'inverse.
                        if (e.getPosition().x < 2) { //Valeur 2 arbitraire, tant que cette valeur est dans la partie gauche de l'écran.
                            //On étire la pancarte au sol (pr la coller), du coup je ré-adapte sa hauteur pour éviter les hitboxs fantômes.
                            platVenture.getBatch().draw(e.getTexture(), e.getPosition().x + e.getLargeur(), e.getPosition().y - 1 / 4f, -e.getLargeur(), e.getHauteur() + 1 / 4f);
                        } else { //Si la pancarte est sur le mur de droite, on l'affiche normalement.
                            platVenture.getBatch().draw(e.getTexture(), e.getPosition().x, e.getPosition().y, e.getLargeur(), e.getHauteur());
                        }
                    } else {
                        platVenture.getBatch().draw(e.getTexture(), e.getPosition().x, e.getPosition().y, e.getLargeur(), e.getHauteur());
                    }
                }
            }
        } else {
            //Mode debug
            this.debug.render(this.monde.getMonde(), camera.combined);
        }

        this.platVenture.getBatch().end(); //On stop de draw sur la caméra normal.

        platVenture.getBatch().setProjectionMatrix(cameraTexte.combined);
        platVenture.getBatch().begin(); //On draw sur la caméra texte.

        if (!this.mouvementJoueur.isDebugMode()) {
            font.draw(platVenture.getBatch(), "Score : " + monde.getScore(), camera.position.x + cameraTexte.viewportWidth / 2 - 7 - (int) (7 * 11 / 10f), camera.position.y + cameraTexte.viewportHeight / 2 - 7, 0, 0, false);
            font.draw(platVenture.getBatch(), "" + monde.getTempsRestant()[0], camera.position.x, camera.position.y + cameraTexte.viewportHeight / 2 - 7, 0, 0, false);
            if (this.monde.isJeuEstEnPause()) {
                if (monde.isaGagne()) {
                    font.draw(platVenture.getBatch(), "Bravo :-)", camera.position.x + (cameraTexte.viewportWidth / 40) * 5, camera.position.y + camera.viewportHeight / 2, 0, 0, false);
                } else {
                    font.draw(platVenture.getBatch(), "Dommage :-/", camera.position.x + (cameraTexte.viewportWidth / 30) * 6, camera.position.y + camera.viewportHeight / 2, 0, 0, false);
                }
            }
        }

        platVenture.getBatch().end();

        //Bonus, on passe au niveau suivant.
        if (this.mouvementJoueur.isSkipNiveau() && !this.monde.isJeuEstEnPause()) {
            this.monde.passerAuNiveauSuivant();
            this.mouvementJoueur.setSkipNiveau(false);
        } else if (this.monde.isJeuEstEnPause()) {
            this.mouvementJoueur.setSkipNiveau(false);
        }

        //Bonus, on augmente le score.
        if (this.mouvementJoueur.isAddScore()) {
            this.monde.ajouterScore();
            this.mouvementJoueur.setAddScore(false);
        }
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

        //Si la caméra est collée au mur de gauche, et que le joueur est à gauche du centre de la caméra, alors la caméra ne bouge pas.
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

    private void defineTextFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParam = new FreeTypeFontGenerator.FreeTypeFontParameter();

        //On fixe les param de la font
        fontParam.size = (int) (58f / 1024f * Gdx.graphics.getWidth()); //60f / 1024f * Gdx.graphics.getWidth() taille du sujet, problème sur android le "-" ne s'affiche pas, je test donc en diminuant la taille.
        fontParam.color = Color.YELLOW;
        fontParam.borderColor = Color.BLACK;
        fontParam.borderWidth = 3f / 1024f * Gdx.graphics.getWidth();

        font = fontGenerator.generateFont(fontParam);

        fontGenerator.dispose();
    }
}
