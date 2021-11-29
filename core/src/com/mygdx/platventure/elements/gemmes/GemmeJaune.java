package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.math.Vector2;

public class GemmeJaune extends Gemmes {
    public GemmeJaune(Vector2 position) {
        super(position);
    }

    //Si la gemme est jaune, sa valeur est 1
    public int getValeurGemme() {
        return 1;
    }
}
