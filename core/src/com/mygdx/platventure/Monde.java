package com.mygdx.platventure;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.platventure.elements.Brique;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.Personnage;
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

    public Monde(char[][] tableauNiveau) {
        this.monde = new World(new Vector2(0, -10f), true); //-10 pour la gravité
        this.elementsDuMonde = new ArrayList<>();

        for (int i = 0; i < tableauNiveau.length; ++i) {
            for (int j = 0; j < tableauNiveau[i].length; ++j) {
                creerElementDuMonde(tableauNiveau, i, j, tableauNiveau[i].length);
            }

        }
    }

    public void creerElementDuMonde(char[][] tableauNiveau, int i, int j, int tailleColonneTableau) {
        Element elementTemporaire = null;

        switch (tableauNiveau[i][j]) {
            //Case représentant les différentes briques du jeu
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
                //La lecture se faisant dans le sens inverse, il faut faire une symétrie par rapport à l'axe des abscisses : si y est en bas à droite, on le met en haut à droite et de ce fait, l'élement est bien placé.
                elementTemporaire = new Brique(new Vector2(i, (tailleColonneTableau - j) - 1)); //(tailleColonneTableau - j) - 1 pour remettre l'image à l'endroit. (le - 1, la lecture était décallée d'une case, une ligne non existante était affichée, tous les élements étaient décalés) le - 1 enlève ce décallage.
                break;
            //Case représentant la plateforme gauche du jeu.
            case 'J':
                elementTemporaire = new PlateformeGauche(new Vector2(i, (tailleColonneTableau - j) - 1));
                break;
            //Case représentant la plateforme du milieu du jeu.
            case 'K':
                elementTemporaire = new PlateformeMilieu(new Vector2(i, (tailleColonneTableau - j) - 1));
                break;
            //Case représentant la plateforme droite du jeu.
            case 'L':
                elementTemporaire = new PlateformeDroite(new Vector2(i, (tailleColonneTableau - j) - 1));
                break;
            //Case représentant les gemmes jaunes du jeu.
            case '1':
                elementTemporaire = new GemmeJaune(new Vector2(i, (tailleColonneTableau - j) - 1));
                break;
            //Case représentant les gemmes rouge du jeu.
            case '2':
                elementTemporaire = new GemmeRouge(new Vector2(i, (tailleColonneTableau - j) - 1));
                break;
            //Case représentant l'eau du jeu.
            case 'W':
                elementTemporaire = new Eau(new Vector2(i, (tailleColonneTableau - j) - 1));
                break;
            //Case représentant la sortie du jeu.
            case 'Z':
                elementTemporaire = new Sortie(new Vector2(i, (tailleColonneTableau - j) - 1));
                break;
            case 'P':
                elementTemporaire = new Personnage(new Vector2(i, (tailleColonneTableau - j) - 1));
                break;
        }

        //Si c'est autre-chose que du vide (V).
        if (elementTemporaire != null) {
            elementTemporaire.setBodyDef();    // On Set le BodyDef de l'élement
            elementTemporaire.createBody(this.monde);  //On Set le Body de l'élement
            elementTemporaire.setFixture();    //On Set la Fixture de l'élement
            this.elementsDuMonde.add(elementTemporaire);   //On ajoute l'élement dans le monde
            System.out.println(elementTemporaire.getPosition());
        }

        //Si c'est du vide, on ne fait rien.
    }

    public World getMonde() {
        return this.monde;
    }
}