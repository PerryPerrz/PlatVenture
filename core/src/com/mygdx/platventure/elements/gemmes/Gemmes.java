package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.EnumTypeBody;

public abstract class Gemmes extends Element {

    private final CircleShape forme;
    private Animation animation;

    public Gemmes(Vector2 position) {
        super(position);
        this.forme = new CircleShape();

        this.forme.setPosition(new Vector2(1 / 2f, 1 / 2f)); //1/2 est non 0 car sinon, la gemme se trouve dans la plateforme, il faut donc la décaller. Sinon, il prend le centre de la gemme et le met en bas à droite de sa case.
        this.forme.setRadius(1 / 4f);

        this.largeur = 1 / 2f; //Rayon*2
        this.hauteur = 1 / 2f;
    }

    @Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.bodyDef.position.set(this.position);
    }

    @Override
    public void setFixture() { //Caractéristiques physiques du body
        if (this.bodyDef != null && this.body != null) {
            FixtureDef fixture = new FixtureDef();
            fixture.shape = forme;
            fixture.isSensor = true; //La gemme est traversable
            getBody().createFixture(fixture); //On ajoute la fixture au body des gemmes.

            this.body.setUserData(EnumTypeBody.GEMMES); //On donne un identifiant pour pouvoir identifier plus tard son body, identifier les gemmes lors d'une collision.
        }
        this.forme.dispose();
    }

    //Fonction qui retourne la valeur d'une gemme selon son type (rouge ou jaune)
    public abstract int getValeurGemme();

    public float getLargeur() {
        return this.largeur;
    }

    public float getHauteur() {
        return this.hauteur;
    }

    public Animation getAnimation() {
        return animation;
    }

    protected void animationGemmes() {
        //On s'occupe de l'animation des gemmes
        TextureRegion[][] textureRegions = TextureRegion.split(getTexture(), 56, 56);
        TextureRegion[] sousTextureRegions1 = new TextureRegion[textureRegions.length];
        for (int i = 0; i < textureRegions.length; ++i) {
            sousTextureRegions1[i] = textureRegions[i][0];
        }
        animation = new Animation<>(0.2f, sousTextureRegions1);
    }
}
