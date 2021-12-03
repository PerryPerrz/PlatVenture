package com.mygdx.platventure.elements.plateformes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.EnumTypeBody;

public class PlateformeMilieu extends Element {

    private final PolygonShape forme;
    private final float densite;
    private final float restitution;
    private final float friction;

    public PlateformeMilieu(Vector2 position) {
        super(position);
        this.forme = new PolygonShape();
        this.densite = 1f;
        this.restitution = 0.1f;
        this.friction = 0.25f;

        //Création des plateformes du milieu, à l'aide des 4 points d'une plateforme.
        Vector2[] vectors = new Vector2[4]; //Forme de l'objet
        vectors[0] = new Vector2(0, 0);
        vectors[1] = new Vector2(0, 3 / 4f);
        vectors[2] = new Vector2(1, 3 / 4f);
        vectors[3] = new Vector2(1, 0);

        this.forme.set(vectors); //On met les 4 points dans la forme. On dit que la forme d'une plateforme correspond à 4 points.

        this.texture = new Texture("images/Platform_K.png");
        this.largeur = 1f;
        this.hauteur = 3 / 4f;
    }

    @Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.bodyDef.position.set(this.position);
    }

    @Override
    public void setFixture() {
        if (this.bodyDef != null && this.body != null) {
            FixtureDef fixture = new FixtureDef();
            fixture.shape = forme;
            fixture.density = densite;
            fixture.restitution = restitution;
            fixture.friction = friction;

            getBody().createFixture(fixture); //On ajoute la fixture au body des plateformes du milieu.

            this.body.setUserData(EnumTypeBody.PLATEFORME_MILIEU);
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
