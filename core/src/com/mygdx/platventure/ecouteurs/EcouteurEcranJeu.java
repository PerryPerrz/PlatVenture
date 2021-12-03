package com.mygdx.platventure.ecouteurs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class EcouteurEcranJeu implements InputProcessor {
    private final Vector2 force; //Force appliquée au joueur lors de son déplacement/pour le déplacer
    private long temps; //Variable qui stock le temps à laquelle le joueur appuie sur son écran, celle-ci permet de gérer le saut. (si l'utilisateur double tap son écran, le temmp entre son premier touché et son second est faible.)

    public EcouteurEcranJeu() {
        this.force = new Vector2(0, 0);
    }

    @Override
    public boolean keyDown(int keycode) {
        //Lorsque l'utilisateur appuie sur la touche, on donne de la force.
        switch (keycode) {
            //Le saut
            case Keys.W:
            case Keys.Z:
            case Keys.DPAD_UP:
                this.force.set(this.force.x, 40); //On impacte pas l'axe des x lors d'un saut, 40 car la gravité rentre en compte.
                break;

            //Gauche
            case Keys.A:
            case Keys.Q:
            case Keys.DPAD_LEFT:
                this.force.set(-1, this.force.y); //-1 sur l'axe des x, on va à gauche, this.force.y car on conserve/impact pas l'axe des y, soit le saut
                break;

            //Droite
            case Keys.D:
            case Keys.DPAD_RIGHT:
                this.force.set(1, this.force.y); //1 sur l'axe des x, on se déplace vers la droite
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //Lorsque l'utilisateur relache la touche, on arrête de donner de la force, on reset la force.
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
        long tempsActuel = System.currentTimeMillis();
        //Si la différence entre le temps sauvegardé lors du premier touché et le temps actuel est compris entre [-100;100[, alors, le joueur saute. (l'écart entre les 2 touchés est jugé trop faible (jugé par moi))
        if (this.temps - tempsActuel >= -100 && this.temps - tempsActuel < 100) {
            this.force.set(this.force.x, 40);
        } else {
            //Si l'utilisateur appuie sur la partie gauche de son écran.
            if (screenX <= Gdx.graphics.getWidth() / 2) {
                this.force.set(-1, this.force.y);
            }
            //Si l'utilisateur appuie sur la partie droite de son écran.
            else {
                this.force.set(1, this.force.y);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //On reset les forces droites et gauches lorque l'utilisateur enlève son doigt de l'écran.
        this.force.set(0, this.force.y);
        //On reset la force du saut, lors de son relachement
        this.force.set(this.force.x, 0);
        this.temps = System.currentTimeMillis(); //On récupère le temps lorsque l'utilisateur arrête de toucher son écran.
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
