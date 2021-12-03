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
    private boolean collisionRapideEntrePersoEtBriqueEtPlateformes = false;
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
            if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.BRIQUE || contact.getFixtureB().getBody().getUserData() == EnumTypeBody.PLATEFORME_GAUCHE || contact.getFixtureB().getBody().getUserData() == EnumTypeBody.PLATEFORME_MILIEU || contact.getFixtureB().getBody().getUserData() == EnumTypeBody.PLATEFORME_DROITE) { //Collision entre le personnage et une brique ou plateforme gauche ou plateforme milieu ou plateforme droite.
                //On test si la collision entre le personnage et une brique/plateformes est rapide.
                if (contact.getFixtureA().getBody().getLinearVelocity().x > 3 || contact.getFixtureA().getBody().getLinearVelocity().y > 3 || contact.getFixtureA().getBody().getLinearVelocity().x < -3 || contact.getFixtureA().getBody().getLinearVelocity().y < -3) {
                    this.collisionRapideEntrePersoEtBriqueEtPlateformes = true;
                }
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
            if (contact.getFixtureA().getBody().getUserData() == EnumTypeBody.BRIQUE || contact.getFixtureA().getBody().getUserData() == EnumTypeBody.PLATEFORME_GAUCHE || contact.getFixtureA().getBody().getUserData() == EnumTypeBody.PLATEFORME_MILIEU || contact.getFixtureA().getBody().getUserData() == EnumTypeBody.PLATEFORME_DROITE) { //Collision entre le personnage et une brique ou plateforme gauche ou plateforme milieu ou plateforme droite.
                //On test si la collision entre le personnage et une brique/plateformes est rapide.
                if (contact.getFixtureB().getBody().getLinearVelocity().x > 3 || contact.getFixtureB().getBody().getLinearVelocity().y > 3 || contact.getFixtureB().getBody().getLinearVelocity().x < -3 || contact.getFixtureB().getBody().getLinearVelocity().y < -3) {
                    this.collisionRapideEntrePersoEtBriqueEtPlateformes = true;
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        //On gère la collision personnage/sortie à la fin du contact.
        if (contact.getFixtureA().getBody().getUserData() == EnumTypeBody.PERSONNAGE) {
            //Collision entre le personnage et la sortie.
            //à chaque fois qu'on sort du contact avec autre chose que la sortie, on reset la sortie.
            this.collisionEntrePersoEtSortie = contact.getFixtureB().getBody().getUserData() == EnumTypeBody.SORTIE;
        }

        //On gère lorsque le personnage correspond à la fixture B et la sortie à l'élement A
        if (contact.getFixtureB().getBody().getUserData() == EnumTypeBody.PERSONNAGE) {
            //Collision entre le personnage et la sortie.
            //à chaque fois qu'on sort du contact avec autre chose que la sortie, on reset la sortie.
            this.collisionEntrePersoEtSortie = contact.getFixtureA().getBody().getUserData() == EnumTypeBody.SORTIE;
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

    public boolean isCollisionRapideEntrePersoEtBriqueEtPlateformes() {
        return collisionRapideEntrePersoEtBriqueEtPlateformes;
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

    public void setCollisionRapideEntrePersoEtBriqueEtPlateformes(boolean collisionRapideEntrePersoEtBriqueEtPlateformes) {
        this.collisionRapideEntrePersoEtBriqueEtPlateformes = collisionRapideEntrePersoEtBriqueEtPlateformes;
    }

    public void dispose() {

    }
}
