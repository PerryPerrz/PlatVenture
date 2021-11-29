package com.mygdx.platventure;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Monde { //Le monde de PlatVenture
    private final World monde; //Le monde du Jeu en général, le monde de n'importe quel jeu
    private final ArrayList<Element> elementsDuMonde;

    public Monde(char[][] tableauNiveau){
        this.monde = new World(new Vector2(0,-10f),true); //-10 pour la gravité
        this.elementsDuMonde = new ArrayList<>();

        for(int i = 0; i < tableauNiveau.length ; ++i){
            for(int j = 0; j < tableauNiveau[i].length ; ++j){
                       creerElementDuMonde(tableauNiveau,i,j);
                }

            }
        }

    public void creerElementDuMonde(char[][]tableauNiveau, int i, int j) {
        switch (tableauNiveau[i][j]) {
            //Case représentant les différentes briques du jeu
            case 'A' :
            case 'B' :
            case 'C' :
            case 'D' :
            case 'E' :
            case 'F' :
            case 'G' :
            case 'H' :
            case 'I' :
                    Brique brique = new Brique(new Vector2(i,j)); //On créer élement
                    brique.setBodyDef();    // On Set le BodyDef de l'élement
                    brique.createBody(this.monde);  //On Set le Body de l'élement
                    brique.setFixture();    //On Set la Fixture de l'élement
                    this.elementsDuMonde.add(brique);   //On ajoutel'élement dans le monde
                System.out.println(brique.getPosition());
                break;
            //Case représentant les platefromes du jeu
            case 'J' | 'K' | 'L':

                break;
        }
    }
}