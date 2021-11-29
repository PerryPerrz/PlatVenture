package com.mygdx.platventure.elements;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Personnage extends Element {

    private final PolygonShape forme;
    private final CircleShape cercle;
    private final float densite;
    private final float restitution;
    private final float friction;

    public Personnage(Vector2 position) {
        super(position);
        this.forme = new PolygonShape();
        this.cercle = new CircleShape();
        this.densite = 0.5f;
        this.restitution = 0.1f;
        this.friction = 0.5f;

        //Création du personnage
        Vector2[] vectors = new Vector2[4]; //Forme de l'objet
        vectors[0] = new Vector2(0.25f, 0.431034483f); //(500*0.5)/290 = 0.86206897 ici, on divise cette valeur par 2 selon l'énoncé : on obtient : 0.431034483 //O.25 et non 0 car il n'est pas centré par rapport à une brique.
        vectors[1] = new Vector2(0.5f, 0.86206897f); //0.5, 0.25 du 0.5/2 + le 0,25 pr centrer le personnage par rapport à la brique. //La hauteur = 0.86206897
        vectors[2] = new Vector2(0.75f, 0.431034483f); //0.5 + 0.25 pour centrer le personnage par rapport à la brique
        vectors[3] = new Vector2(0.50f, 0.215517242f); //O.50 /2 + 0.25 pr centrer le personnage par rapport à la brique //y = 0.86206897/4 = 0.215517242

        this.forme.set(vectors);

        this.cercle.setPosition(new Vector2(0.5f, 0.107758621f)); //x = 0.5/2 + 0.25 pr centrer le personnage par rapport à la brique. y = h/8 donc 0.862068966/8 = 0.107758621
        this.cercle.setRadius(0.107758621f); //Un rayon = distance qui par du centre et qui arrive sur un coté du cercle, il peut donc très bien partir du centre et arriver sur le coté bas du cercle.
    }

    @Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.bodyDef.position.set(this.position);
    }

    @Override
    public void setFixture() { //Caractéristiques physiques du body
        if (this.bodyDef != null && this.body != null) {
            //On s'occupe de la tête du personnage.
            FixtureDef fixtureTete = new FixtureDef();
            fixtureTete.shape = this.forme;
            fixtureTete.density = this.densite;
            fixtureTete.restitution = this.restitution;
            fixtureTete.friction = this.friction;

            getBody().createFixture(fixtureTete); //On ajoute la fixture au body du personnage.

            //On s'occupe du corps du personnage.
            FixtureDef fixtureCorps = new FixtureDef();
            fixtureCorps.shape = this.cercle;
            fixtureCorps.density = this.densite;
            fixtureCorps.restitution = this.restitution;
            fixtureCorps.friction = this.friction;

            getBody().createFixture(fixtureCorps); //On ajoute la fixture au body du personnage.
        }
        this.forme.dispose();
    }
}
