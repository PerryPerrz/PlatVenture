package com.mygdx.platventure.elements.plateformes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.platventure.EnumTypeBody;
import com.mygdx.platventure.elements.Element;

public class PlateformeDroite extends Element {

    private final PolygonShape forme;
    private final float densite;
    private final float restitution;
    private final float friction;

    public PlateformeDroite(Vector2 position) {
        super(position);
        this.forme = new PolygonShape();
        this.densite = 1f;
        this.restitution = 0.1f;
        this.friction = 0.25f;

        //Création de la plateforme de droite, on donne à l'aide des 5 points d'une plateforme sa forme.
        Vector2[] vectors = new Vector2[5]; //Forme de l'objet
        vectors[0] = new Vector2(0, 0);
        vectors[1] = new Vector2(0, 3 / 4f);
        vectors[2] = new Vector2(1, 3 / 4f);
        vectors[3] = new Vector2(1, (3 / 4f) / 2f);
        vectors[4] = new Vector2(1 / 2f, 0);

        this.forme.set(vectors); //On met les 5 points dans la forme. On dit que la forme d'une plateforme de droite correspond à 5 points.
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

            getBody().createFixture(fixture); //On ajoute la fixture au body des plateformes droites.

            this.body.setUserData(EnumTypeBody.PLATEFORME_DROITE);
        }
        this.forme.dispose();
    }

    @Override
    public void dispose() {

    }
}
