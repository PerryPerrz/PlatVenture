package com.mygdx.platventure.ecouteurs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class EcouteurEcranJeu implements InputProcessor {
    private final Vector2 force; //Force appliquée au joueur lors de son déplacement/pour le déplacer
    private boolean skipNiveau;

    public EcouteurEcranJeu() {
        this.force = new Vector2(0, 0);
        this.skipNiveau = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        //Lorsque l'utilisateur appuie sur la touche, on donne de la force.
        switch (keycode) {
            //Bonus : on passe n'importe quel niveau.
            case Keys.TAB:
                this.skipNiveau = true;
                break;

                //Bonus : on enlève du temps au timer.

            //Quitte l'appli
            case Keys.ESCAPE:
                Gdx.app.exit();
                break;

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
        if (pointer == 1) { //pointer = 0, quand c'est la première touche, 1 quand ses 2 doigts sont collés  {
            this.force.set(this.force.x, 40);
        } else if (pointer == 0) { //Pas de 3éme/4éme doigts
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
        //On reset les forces droites et gauches lorque l'utilisateur enlève son doigt de l'écran si c'est le doigt 1 qui s'enlève.
        if (pointer == 0) { //0 = déplacement
            this.force.set(0, this.force.y);
        }
        //On reset la force du saut, lors de son relachement, si c'est le doigt 0 qui s'enlève
        if (pointer == 1) { //1 = jump
            this.force.set(this.force.x, 0);
        }
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
        this.force.y = 0; //On reset la force verticale. (éviter le spam de jump(s))
        return vectorTemp;
    }

    public boolean isSkipNiveau() {
        return skipNiveau;
    }

    public void setSkipNiveau(boolean skipNiveau) {
        this.skipNiveau = skipNiveau;
    }
}
