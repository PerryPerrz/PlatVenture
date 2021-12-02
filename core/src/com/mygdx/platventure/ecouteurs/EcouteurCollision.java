package com.mygdx.platventure.ecouteurs;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.platventure.EnumTypeBody;

public class EcouteurCollision implements ContactListener {
    private boolean collisionEntrePersoEtGemmes = false; //Booléen qui passe à true lorsuque la collision entre un personnage et une gemme à lieu.
    private boolean collisionEntrePersoEtEau = false;
    private boolean collisionEntrePersoEtSortie = false;
    private Body gemmes = null; //On récup le body des gemmes pour le détruire. (quand la gemme est recup, elle est détruite)

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getBody().getUserData() == EnumTypeBody.PERSONNAGE) {
            if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.GEMMES) { //Collision entre le personnage et une gemme.
                this.collisionEntrePersoEtGemmes = true;
                this.gemmes = contact.getFixtureB().getBody();
            }
            if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.EAU) { //Collision entre le personnage et l'eau.
                this.collisionEntrePersoEtEau = true;
            }
        }

        //On gère lorsque le personnage correspond à la fixture B et l'autre élement concerné.
        if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.PERSONNAGE) {
            if (contact.getFixtureA().getBody().getUserData() == EnumTypeBody.GEMMES) { //Collision entre le personnage et une gemme.
                this.collisionEntrePersoEtGemmes = true;
                this.gemmes = contact.getFixtureA().getBody();
            }
            if (contact.getFixtureA().getBody().getUserData() == EnumTypeBody.EAU) { //Collision entre le personnage et l'eau.
                this.collisionEntrePersoEtEau = true;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        //On gère la collision personnage/sortie à la fin du contact.
        if (contact.getFixtureA().getBody().getUserData() == EnumTypeBody.PERSONNAGE) {
            if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.SORTIE) { //Collision entre le personnage et la sortie.
                this.collisionEntrePersoEtSortie = true;
            }
        }

        //On gère lorsque le personnage correspond à la fixture B et la sortie à l'élement A
        if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.PERSONNAGE) {
            if (contact.getFixtureA().getBody().getUserData() == EnumTypeBody.SORTIE) { //Collision entre le personnage et la sortie.
                this.collisionEntrePersoEtSortie = true;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isCollisionEntrePersoEtGemmes() {
        return collisionEntrePersoEtGemmes;
    }

    public boolean isCollisionEntrePersoEtEau() {
        return collisionEntrePersoEtEau;
    }

    public boolean isCollisionEntrePersoEtSortie() {
        return collisionEntrePersoEtSortie;
    }

    public Body getGemmes() {
        return this.gemmes;
    }

    public void setCollisionEntrePersoEtGemmes(boolean collisionEntrePersoEtGemmes) {
        this.collisionEntrePersoEtGemmes = collisionEntrePersoEtGemmes;
    }

    public void setCollisionEntrePersoEtEau(boolean collisionEntrePersoEtEau) {
        this.collisionEntrePersoEtEau = collisionEntrePersoEtEau;
    }

    public void setCollisionEntrePersoEtSortie(boolean collisionEntrePersoEtSortie) {
        this.collisionEntrePersoEtSortie = collisionEntrePersoEtSortie;
    }

    public void dispose() {

    }
}
