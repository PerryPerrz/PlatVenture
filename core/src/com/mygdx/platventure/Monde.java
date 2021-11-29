package com.mygdx.platventure;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platventure.elements.Brique;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.Sortie;
import com.mygdx.platventure.elements.gemmes.GemmeJaune;
import com.mygdx.platventure.elements.gemmes.GemmeRouge;
import com.mygdx.platventure.elements.plateformes.Eau;
import com.mygdx.platventure.elements.plateformes.PlateformeDroite;
import com.mygdx.platventure.elements.plateformes.PlateformeGauche;
import com.mygdx.platventure.elements.plateformes.PlateformeMilieu;

import java.util.ArrayList;

public class Monde { //Le monde de PlatVenture
    private final World monde; //Le monde du jeu en général, le monde ds n'importe quel jeu
    private final ArrayList<Element> elementsDuMonde;

    public Monde(char[][] tableauNiveau){
        this.monde = new World(new Vector2(0,-10f),true); //-10 pour la gravité
        this.elementsDuMonde = new ArrayList<>();

        for(int i = 0; i < tableauNiveau.length ; ++i){
            for(int j = 0; j < tableauNiveau[i].length ; ++j){
                       creerElementDuMonde(tableauNiveau,i,j,tableauNiveau[i].length);
                }

            }
        }

    public void creerElementDuMonde(char[][]tableauNiveau, int i, int j,int tailleColonneTableau) {
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
                Brique brique = new Brique(new Vector2(i,(tailleColonneTableau-j)-1)); //On créer élement
                brique.setBodyDef();    // On Set le BodyDef de l'élement
                brique.createBody(this.monde);  //On Set le Body de l'élement
                brique.setFixture();    //On Set la Fixture de l'élement
                this.elementsDuMonde.add(brique);   //On ajoutel'élement dans le monde
                System.out.println(brique.getPosition());
                break;
            //Case représentant les platefromes du jeu
            case 'J' :
                PlateformeGauche plateformeGauche = new PlateformeGauche(new Vector2(i,(tailleColonneTableau-j)-1));
                plateformeGauche.setBodyDef();    // On Set le BodyDef de l'élement
                plateformeGauche.createBody(this.monde);  //On Set le Body de l'élement
                plateformeGauche.setFixture();    //On Set la Fixture de l'élement
                this.elementsDuMonde.add(plateformeGauche);   //On ajoute l'élement dans le monde
                System.out.println(plateformeGauche.getPosition());
                break;
            case 'K' :
                PlateformeMilieu plateformeMilieu = new PlateformeMilieu(new Vector2(i,(tailleColonneTableau-j)-1));
                plateformeMilieu.setBodyDef();    // On Set le BodyDef de l'élement
                plateformeMilieu.createBody(this.monde);  //On Set le Body de l'élement
                plateformeMilieu.setFixture();    //On Set la Fixture de l'élement
                this.elementsDuMonde.add(plateformeMilieu);   //On ajoute l'élement dans le monde
                System.out.println(plateformeMilieu.getPosition());
                break;
            case 'L' :
                PlateformeDroite plateformeDroite = new PlateformeDroite(new Vector2(i,(tailleColonneTableau-j)-1));
                plateformeDroite.setBodyDef();    // On Set le BodyDef de l'élement
                plateformeDroite.createBody(this.monde);  //On Set le Body de l'élement
                plateformeDroite.setFixture();    //On Set la Fixture de l'élement
                this.elementsDuMonde.add(plateformeDroite);   //On ajoute l'élement dans le monde
                System.out.println(plateformeDroite.getPosition());
                break;
            case '1' :
                GemmeJaune gemmeJaune = new GemmeJaune(new Vector2(i,(tailleColonneTableau-j)-1));
                gemmeJaune.setBodyDef();    // On Set le BodyDef de l'élement
                gemmeJaune.createBody(this.monde);  //On Set le Body de l'élement
                gemmeJaune.setFixture();    //On Set la Fixture de l'élement
                this.elementsDuMonde.add(gemmeJaune);   //On ajoute l'élement dans le monde
                System.out.println(gemmeJaune.getPosition());
                break;
            case '2' :
                GemmeRouge gemmeRouge = new GemmeRouge(new Vector2(i,(tailleColonneTableau-j)-1));
                gemmeRouge.setBodyDef();    // On Set le BodyDef de l'élement
                gemmeRouge.createBody(this.monde);  //On Set le Body de l'élement
                gemmeRouge.setFixture();    //On Set la Fixture de l'élement
                this.elementsDuMonde.add(gemmeRouge);   //On ajoute l'élement dans le monde
                System.out.println(gemmeRouge.getPosition());
                break;
            case 'W' :
                Eau eau = new Eau(new Vector2(i,(tailleColonneTableau-j)-1));
                eau.setBodyDef();    // On Set le BodyDef de l'élement
                eau.createBody(this.monde);  //On Set le Body de l'élement
                eau.setFixture();    //On Set la Fixture de l'élement
                this.elementsDuMonde.add(eau);   //On ajoute l'élement dans le monde
                System.out.println(eau.getPosition());
                break;
            case 'Z' :
                Sortie sortie = new Sortie(new Vector2(i,(tailleColonneTableau-j)-1));
                sortie.setBodyDef();    // On Set le BodyDef de l'élement
                sortie.createBody(this.monde);  //On Set le Body de l'élement
                sortie.setFixture();    //On Set la Fixture de l'élement
                this.elementsDuMonde.add(sortie);   //On ajoute l'élement dans le monde
                System.out.println(sortie.getPosition());
                break;
        }
    }

    public World getMonde(){
        return this.monde;
    }
}