package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GemmeRouge extends Gemmes {
    public GemmeRouge(Vector2 position) {
        super(position);
        this.texture = new Texture("images/Gem_2.png");
    }

    //Si la gemme est rouge, sa valeur est 2
    public int getValeurGemme() {
        return 2;
    }

    @Override
    public void dispose() {

    }

    public Texture getTexture() {
        return this.texture;
    }
}
