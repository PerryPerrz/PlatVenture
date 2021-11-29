package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.platventure.elements.Element;

public abstract class Gemmes extends Element {

    private final CircleShape forme;

    public Gemmes(Vector2 position) {
        super(position);
        this.forme = new CircleShape();

        this.forme.setPosition(new Vector2(1/2f,1/2f)); //1/2 est non 0 car sinon, la gemme se trouve dans la plateforme, il faut donc la décaller. Sinon, il prend le centre de la gemme et le met en bas à droite de sa case.
        this.forme.setRadius(1/4f);
    }

    @Override
    public void setBodyDef() {
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.bodyDef.position.set(this.position);
    }

    @Override
    public void setFixture() { //Caractéristiques physiques du body
        if(this.bodyDef != null && this.body != null){
            FixtureDef fixture = new FixtureDef();
            fixture.shape = forme;
            fixture.isSensor = true; //La gemme est traversable
            getBody().createFixture(fixture); //On ajoute la fixture au body des briques.
        }
        this.forme.dispose();
    }

    public abstract int getValeurGemme();
}
