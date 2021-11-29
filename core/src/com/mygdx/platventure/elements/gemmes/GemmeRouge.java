package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.math.Vector2;

public class GemmeRouge extends Gemmes {
    public GemmeRouge(Vector2 position) {
        super(position);
    }

    //Si la gemme est rouge, sa valeur est 2
    public int getValeurGemme() {
        return 2;
    }
}
