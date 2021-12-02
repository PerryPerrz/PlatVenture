package com.mygdx.platventure;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.platventure.ecouteurs.EcouteurCollision;
import com.mygdx.platventure.elements.Brique;
import com.mygdx.platventure.elements.Element;
import com.mygdx.platventure.elements.Personnage;
import com.mygdx.platventure.elements.Sortie;
import com.mygdx.platventure.elements.gemmes.GemmeJaune;
import com.mygdx.platventure.elements.gemmes.GemmeRouge;
import com.mygdx.platventure.elements.gemmes.Gemmes;
import com.mygdx.platventure.elements.plateformes.Eau;
import com.mygdx.platventure.elements.plateformes.PlateformeDroite;
import com.mygdx.platventure.elements.plateformes.PlateformeGauche;
import com.mygdx.platventure.elements.plateformes.PlateformeMilieu;

import java.util.ArrayList;

public class Monde { //Le monde de PlatVenture
    private World monde; //Le monde du jeu en général, le monde ds n'importe quel jeu
    private ArrayList<Element> elementsDuMonde;
    private Personnage personnage;
    private EcouteurCollision collisionJoueur;
    private Niveau niveau;
    private Timer timer; //Timer de chaque niveau.
    public int score; //Score du joueur.
    private int numeroNiveauActuel;

    public Monde() {
        this.score = 0;
        this.numeroNiveauActuel = 1;
        creerMonde(this.numeroNiveauActuel);
    }

    public void creerMonde(int numeroNiveau) {
        this.monde = new World(new Vector2(0, -10f), true); //-10 pour la gravité
        this.elementsDuMonde = new ArrayList<>();
        this.niveau = new Niveau("levels/level_00" + numeroNiveau + ".txt");

        final int[] cpt = {this.niveau.getTemps()}; //On créer le compteur du niveau qui se décremente chaque seconde.
        this.timer = new Timer();
        this.timer.scheduleTask(new Timer.Task() { //On est en train de faire une tâche chronométrée. (met en place une tache durant un certian temps qui se répete)
            @Override
            public void run() {
                cpt[0]--; //On décremente le compteur, on passe par un tableau car run est la fonction de la classe timer task, on à donc pas accés à la variable local cpt, donc il faut passer par un pointeur et donc un tableau.
                System.out.println(cpt[0]);
            }
        }, 0, 1); //0 : délai avant que le timer commence, donc on veut ici que le timer commence direct, 1 : la frequence de répétion, soit ici toutes les 1 secondes.


        for (int i = 0; i < this.niveau.getTabNiveau().length; ++i) {
            for (int j = 0; j < this.niveau.getTabNiveau()[i].length; ++j) {
                creerElementDuMonde(this.niveau.getTabNiveau(), i, j, this.niveau.getTabNiveau()[i].length);
            }
        }

        //Initialisation de l'écouteur collision.
        this.collisionJoueur = new EcouteurCollision();
        this.monde.setContactListener(this.collisionJoueur); //L'écouteur de collision écoute le monde
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
            //Case représentant le perosnnage du jeu.
            case 'P':
                this.personnage = new Personnage(new Vector2(i, (tailleColonneTableau - j) - 1));
                elementTemporaire = this.personnage;
                break;
        }

        //Si c'est autre-chose que du vide (V).
        if (elementTemporaire != null) {
            elementTemporaire.setBodyDef();    // On Set le BodyDef de l'élement
            elementTemporaire.createBody(this.monde);  //On Set le Body de l'élement
            elementTemporaire.setFixture();    //On Set la Fixture de l'élement
            this.elementsDuMonde.add(elementTemporaire);   //On ajoute l'élement dans le monde
        }

        //Si c'est du vide, on ne fait rien.
    }

    public World getMonde() {
        return this.monde;
    }

    public Personnage getPersonnage() {
        return this.personnage;
    }

    //Fonction qui met à jour le monde graphique à partir des données (maj l'affichage)
    public void update() {
        for (Element e : this.elementsDuMonde) {
            if (e != null) { //On vérifie que l'élement n'est pas vide (le vide du niveau)
                e.setPosition(e.getBody().getPosition()); //On prend la position dans son body (dans les données) et on met cette position dans l'affichage.
            }
        }
        //On check les collisions entre le personnage et les gemmes.
        if (this.collisionJoueur.isCollisionEntrePersoEtGemmes()) {
            Element elementTemp = null; //On ne peut pas supprimer un éleent de ce que je parcours dans un foreach. Je passe par une variable intermédiaire?
            //On détruit la gemme.
            for (Element e : this.elementsDuMonde) { //On parcourt les élements pour détruire la bonne gemme, la gemme qui ç bien été récup.
                if (e.getBody() == this.collisionJoueur.getGemmes()) { //On vérifie que l'élement n'est pas vide (le vide du niveau)
                    elementTemp = e;
                }
            }
            //On incrémente le score.
            this.score += ((Gemmes) elementTemp).getValeurGemme();
            System.out.println("score :" + score);

            this.elementsDuMonde.remove(elementTemp); //Une fois que l'on à trouvé la gemme correspondant à celle récupérée, on remove la gemme des élements du monde.
            this.monde.destroyBody(this.collisionJoueur.getGemmes()); //On détruit la gemme du monde qui à été touchée.

            //On remet le booléen de collision à faux, la collision est finie.
            this.collisionJoueur.setCollisionEntrePersoEtGemmes(false);
        }

        //On check les collisions entre le personnage et l'eau.
        if (this.collisionJoueur.isCollisionEntrePersoEtEau()) {
            //On restart le niveau en re-initialisant le score.
            this.score = 0;
            this.dispose(); //On détruit le monde.
            creerMonde(this.numeroNiveauActuel); //On doit recréer un monde.
        }

        //On check les collisions entre le personnage et la sortie.
        if (this.personnage.getPosition().x >= this.niveau.getLargeur() || this.personnage.getPosition().x < -1 || this.personnage.getPosition().y < 0) { //Si le joueur sort de la droite de l'écran //Si le joueur sort de la gauche de l'écran //Si le joueur sort du bas de l'écran //-1 car sinn il ne dépasse pas la brique
            if (this.collisionJoueur.isCollisionEntrePersoEtSortie()) { //Si il sort de l'écran en touchant la sortie, le joueur gagne.
                //On passe au niveau suivant (lorsque l'on touche la sortie et que l'on sort de l'écran) en gardant le score.
                this.dispose(); //On détruit le monde.
                //On passe au niveau suivant
                if (this.numeroNiveauActuel == 3) {
                    this.numeroNiveauActuel = 1;
                } else {
                    this.numeroNiveauActuel++;
                }
                creerMonde(this.numeroNiveauActuel); //On doit recréer un monde en passant au niveau suivant
            } else { //Si il sort de l'écran sans toucher la sortie, le joueur perd.
                this.score = 0;
                this.dispose(); //On détruit le monde.
                creerMonde(this.numeroNiveauActuel); //On doit recréer un monde en rejouant sur le même niveau.
            }
        }
    }

    //Fonction qui détruit tous les élements utilisée avant de recréer le monde/créer un nouveau monde.
    public void dispose() {
        //On dispose tous les élements sauf les élements null (le vide)
        for (Element e : this.elementsDuMonde) {
            if (e != null) {
                e.dispose();
            }
        }
        //On vide l'ArrayList
        this.elementsDuMonde.clear();
        this.elementsDuMonde = null; //Il n'y à plus de références à l'ArrayList vidée, elle devient un objet mort et se fait consommer par le garbage collector.

        //On clean up les autres attributs
        this.monde.dispose();
        this.personnage.dispose();
        this.collisionJoueur.dispose();
        this.niveau.dispose();
        this.timer.clear(); //On stop le timer en cours, on peut en recréer un nouveau par la suite. (éviter les timers qui se font en parallèle lors que la relance d'un niveau)
    }
}