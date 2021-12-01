package com.mygdx.platventure.ecouteurs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.platventure.EnumTypeBody;

import sun.jvm.hotspot.debugger.cdbg.EnumType;

public class EcouteurCollision implements ContactListener {
    private boolean isCollisionEntrePersoEtGemmes; //Booléen qui passe à true lorsuque la collision entre un personnage et une gemme à lieu.
    private boolean isCollisionEntrePersoEtEau;
    private boolean isCollisionEntrePersoEtSortie;

    @Override
    public void beginContact(Contact contact) {
        this.isCollisionEntrePersoEtGemmes = false;
        this.isCollisionEntrePersoEtEau = false;
        this.isCollisionEntrePersoEtSortie = false;

        if (contact.getFixtureA().getBody().getUserData() == EnumTypeBody.PERSONNAGE) {
            if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.GEMMES) { //Collision entre le personnage et une gemme.
                this.isCollisionEntrePersoEtGemmes = true;
            }
            if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.EAU) { //Collision entre le personnage et l'eau.
                this.isCollisionEntrePersoEtEau = true;
            }
            if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.SORTIE) { //Collision entre le personnage et la sortie..
                this.isCollisionEntrePersoEtSortie = true;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isCollisionEntrePersoEtGemmes() {
        return isCollisionEntrePersoEtGemmes;
    }

    public boolean isCollisionEntrePersoEtEau() {
        return isCollisionEntrePersoEtEau;
    }

    public boolean isCollisionEntrePersoEtSortie() {
        return isCollisionEntrePersoEtSortie;
    }
}
