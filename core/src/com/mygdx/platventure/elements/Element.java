package com.mygdx.platventure.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Element {
    protected Vector2 position; //(x,y) donc la position d'un élement
    protected Body body; //Caractéristiques physiques de l'élement
    protected BodyDef bodyDef; //Caractéristiques non physique de l'élement
    protected Texture texture;
    protected float largeur;
    protected float hauteur;

    public Element(Vector2 position) {
        this.position = position;
    }

    public abstract void setBodyDef();

    public abstract void setFixture();

    public void createBody(World monde) {
        this.body = monde.createBody(this.bodyDef);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Body getBody() {
        return body;
    }

    public abstract void dispose();

    public abstract Texture getTexture();

    public abstract float getLargeur();

    public abstract float getHauteur();
}
