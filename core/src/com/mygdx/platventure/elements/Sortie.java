package com.mygdx.platventure.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Sortie extends Element {

    private final PolygonShape forme;
    private final float densite;
    private final float restitution;
    private final float friction;

    public Sortie(Vector2 position) {
        super(position);
        this.forme = new PolygonShape();
        this.densite = 1f;
        this.restitution = 0.1f;
        this.friction = 0.25f;

        //Création de la sortie.
        Vector2[] vectors = new Vector2[4]; //Forme de l'objet
        vectors[0] = new Vector2(0, 0);
        vectors[1] = new Vector2(0, 1);
        vectors[2] = new Vector2(1, 1);
        vectors[3] = new Vector2(1, 0);

        this.forme.set(vectors); //On met les 4 points dans la forme. On dit que la forme de la sortie correspond à 4 points.
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

            fixture.isSensor = true;

            getBody().createFixture(fixture); //On ajoute la fixture au body de la sortie.
        }
        this.forme.dispose();
    }
}
