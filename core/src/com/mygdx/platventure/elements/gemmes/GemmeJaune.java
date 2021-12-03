package com.mygdx.platventure.elements.gemmes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GemmeJaune extends Gemmes {
    public GemmeJaune(Vector2 position) {
        super(position);
        this.texture = new Texture("images/Gem_1.png");
        this.animationGemmes();
    }

    //Si la gemme est jaune, sa valeur est 1
    public int getValeurGemme() {
        return 1;
    }

    @Override
    public void dispose() {

    }

    public Texture getTexture() {
        return this.texture;
    }
}
