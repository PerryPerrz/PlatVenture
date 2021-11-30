package com.mygdx.platventure;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class EcouteurEcranJeu implements InputProcessor {
    private Vector2 force; //Force aplliquée au joueur lors de son déplacement/pour le déplacer

    public EcouteurEcranJeu() {
        this.force = new Vector2(0, 0);
    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            //Le saut
            case Keys.W:
            case Keys.Z:
            case Keys.DPAD_UP:
                this.force.set(this.force.x, 40); //On impacte pas l'axe des x lors d'un saut, 40 car la gravité rentre en compte.
                System.out.println(this.force);
                break;

            //Gauche
            case Keys.A:
            case Keys.Q:
            case Keys.DPAD_LEFT:
                this.force.set(-1, this.force.y); //-1 sur l'axe des x, on va à gauche, this.force.y car on conserve/impact pas l'axe des y, soit le saut
                System.out.println(this.force);
                break;

            //Droite
            case Keys.D:
            case Keys.DPAD_RIGHT:
                this.force.set(1, this.force.y); //1 sur l'axe des x, on se déplace vers la droite
                System.out.println(this.force);
                break;

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //Lorsque l'utilisateur relache la touche, on arrête de donner de force, on reset la force.
        switch (keycode) {
            //Le saut
            case Keys.W:
            case Keys.Z:
            case Keys.DPAD_UP:
                this.force.set(this.force.x, 0); //On impacte pas l'axe des x lors d'un saut, 40 car la gravité rentre en compte.
                break;

            //Gauche
            case Keys.A:
            case Keys.Q:
            case Keys.DPAD_LEFT:

            //Droite
            case Keys.D:
            case Keys.DPAD_RIGHT:
                this.force.set(0, this.force.y);
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public Vector2 getForce() {
        Vector2 vectorTemp = new Vector2(this.force);
        this.force.y = 0; //On reset la force verticale. (éviter ld spam des jumps)
        return vectorTemp;
    }
}
