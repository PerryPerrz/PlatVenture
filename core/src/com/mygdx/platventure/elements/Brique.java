package com.mygdx.platventure.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.platventure.EnumTypeBody;

public class Brique extends Element {

    private final PolygonShape forme;
    private final float densite;
    private final float restitution;
    private final float friction;

    public Brique(Vector2 position, char lettreDeLaBrique) {
        super(position);
        this.forme = new PolygonShape();
        this.densite = 1f;
        this.restitution = 0.1f;
        this.friction = 0.25f;

        //Création des briques
        Vector2[] vectors = new Vector2[4]; //Forme de l'objet
        vectors[0] = new Vector2(0, 0);
        vectors[1] = new Vector2(0, 1);
        vectors[2] = new Vector2(1, 1);
        vectors[3] = new Vector2(1, 0);

        this.forme.set(vectors); //On met les 4 points dans la forme. On dit que la forme d'une brique correspond à 4 points

        this.texture = new Texture("images/Brick_" + lettreDeLaBrique + ".png");
        this.largeur = 1;
        this.hauteur = 1;
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
            fixture.density = densite;
            fixture.restitution = restitution;
            fixture.friction = friction;

            getBody().createFixture(fixture); //On ajoute la fixture au body des briques.

            this.body.setUserData(EnumTypeBody.BRIQUE);
        }
        this.forme.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public float getLargeur() {
        return this.largeur;
    }

    @Override
    public float getHauteur() {
        return this.hauteur;
    }
}
