package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Partie {
    private ArrayList<Monstre> monstres = new ArrayList<>();
    private ArrayList<Magie> boulesMagique = new ArrayList<>();

    private ArrayList<Image> imagesVies = new ArrayList<>();

    private ArrayList<Oeil> monstresOeil = new ArrayList<>();
    private ArrayList<Bouche> monstresBouche = new ArrayList<>();

    private double tempsEcouleMonstre = 0;
    private double tempsEcouleMagie = 0;
    private double tempsEcouleDepuisDebut = 0;
    private double tempsEcouleMonstreSpecial = 0;
    private boolean affichageNiveau = true;
    private double tempsAffichageNiveau = 0;

    private int cptScore = 0;

    public int getCptScore() {
        return cptScore;
    }

    public void setCptScore(int cptScore) {
        this.cptScore = cptScore;
    }

    public void setNbMonstreSorti(int nbMonstreSorti) {
        this.nbMonstreSorti = nbMonstreSorti;
    }

    public int getNbMonstreSorti() {
        return nbMonstreSorti;
    }

    private int nbMonstreSorti = 0;

    private Squelette squelette;


    Partie() {
        squelette = new Squelette();
        for (int i = 0; i < 3; i++) {
            imagesVies.add(new Image("squelette.png"));
        }
    }

    public void update(double deltaTemps) {

        tempsEcouleDepuisDebut += deltaTemps;
        /*
        Pendant qu'on affiche un changement de niveau, on arrête l'affichage des monstres en arrétant l'avancement de leur temps.
         */
        if (!affichageNiveau) {
            tempsEcouleMagie += deltaTemps;
            tempsEcouleMonstre += deltaTemps;
            tempsEcouleMonstreSpecial += deltaTemps;
        }

        if (affichageNiveau) {
            tempsAffichageNiveau += deltaTemps;
            if (tempsAffichageNiveau > 3) {
                tempsAffichageNiveau = 0;
                affichageNiveau = false;
            }
        }


        // -- squelette --
        squelette.update(deltaTemps);

        for (var monstre : monstres)
            monstre.update(deltaTemps);

        // -- À chaque 3 sec crée un nouveau monstre
        if (tempsEcouleMonstre > 3) {
            tempsEcouleMonstre = 0;
            creerMonstre();
        }


        for (int i = 0; i < monstres.size(); i++) {
            if (monstres.get(i).validerSortiMonstre()) {
                enleverMonstre(monstres.get(i));
                nbMonstreSorti++;
            }
        }
        if (Monstre.numeroNiveau > 1) {

            for (int i = 0; i < monstresOeil.size(); i++) {
                if (monstresOeil.get(i).validerSortiMonstre()) {
                    enleverMonstreOeil(monstresOeil.get(i));
                    nbMonstreSorti++;
                }

            }
            for (int i = 0; i < monstresBouche.size(); i++) {
                if (monstresBouche.get(i).validerSortiMonstre()) {
                    enleverMonstreBouche(monstresBouche.get(i));
                    nbMonstreSorti++;
                }

            }

            if (!affichageNiveau) {
                if (tempsEcouleMonstreSpecial > 5) {
                    tempsEcouleMonstreSpecial = 0;
                    boolean typeMonstre = Monstre.getRandomCoteMonstre();
                    if (typeMonstre) {
                        creerMonstreBouche();
                    } else creerMonstreOeil();
                }
            }


            for (var monstreOeil : monstresOeil) {
                monstreOeil.update(deltaTemps);
                if (monstreOeil.avance) {
                    if (monstreOeil.tempsAvancer >= 0.5) {
                        monstreOeil.tempsAvancer = 0;
                        monstreOeil.vx = -monstreOeil.vx;
                        monstreOeil.avance = false;
                    }
                } else {
                    if (monstreOeil.tempsAvancer >= 0.25) {
                        monstreOeil.tempsAvancer = 0;
                        monstreOeil.vx = -monstreOeil.vx;
                        monstreOeil.avance = true;
                    }
                }
            }
            for (var monstreBouche : monstresBouche)
                monstreBouche.update(deltaTemps);
        }


        for (var bouleMagique : boulesMagique)
            bouleMagique.update(deltaTemps);
        if (tempsEcouleMagie >= 0.6) {

            if (Input.isKeyPressed(KeyCode.SPACE)) {
                tempsEcouleMagie = 0;
                creerBouleMagique(squelette);

            }
        }

        for (int i = 0; i < boulesMagique.size(); i++) {
            if (boulesMagique.get(i).validerSortiBouleMagique()) {
                enleverBoule(boulesMagique.get(i));
            }


        }


        for (var bouleMagie : boulesMagique) {

            for (int i = 0; i < monstres.size(); i++) {
                if (monstres.get(i).intersects(bouleMagie)) {
                    cptScore++;

                    enleverMonstre(monstres.get(i));
                    augmenterNiveauApresCinqMonstreSortie();

                }
            }
            for (int i = 0; i < monstresOeil.size(); i++) {
                if (monstresOeil.get(i).intersects(bouleMagie)) {
                    cptScore++;
                    enleverMonstreOeil(monstresOeil.get(i));
                    augmenterNiveauApresCinqMonstreSortie();
                }

            }
            for (int i = 0; i < monstresBouche.size(); i++) {
                if (monstresBouche.get(i).intersects(bouleMagie)) {
                    cptScore++;
                    enleverMonstreBouche(monstresBouche.get(i));
                    augmenterNiveauApresCinqMonstreSortie();
                }

            }


        }


    }

    public void draw(GraphicsContext context) {

        context.setFill(Color.WHITE); //https://examples.javacodegeeks.com/desktop-java/javafx/javafx-canvas-example/#:~:text=You%20can%20draw%20text%20using,%2C%20double%20x%2C%20double%20y)
        context.setFont(Font.font(20));
        context.fillText(String.valueOf(cptScore), 320, 30);
        if (affichageNiveau) {
            afficherNiveau(context);
        }
        if (gameOver()) {
            affichageFinPartie(context);
        }


//
        if (nbMonstreSorti < 3) {
            int position1 = 230;
            context.drawImage(ImageHelpers.colorize(imagesVies.get(0), Color.rgb(194, 30, 86)), position1, 50, 40, 40);


        }
        if (nbMonstreSorti < 2) {
            int position2 = 290;
            context.drawImage(ImageHelpers.colorize(imagesVies.get(1), Color.rgb(194, 30, 86)), position2, 50, 40, 40);
        }
        int position3 = 340;
        if (nbMonstreSorti < 1)
            context.drawImage(ImageHelpers.colorize(imagesVies.get(2), Color.rgb(194, 30, 86)), position3, 50, 40, 40);


        squelette.draw(context);
        for (var monstre : monstres)
            monstre.draw(context);
        for (var bouleMagique : boulesMagique)
            for (int i = 0; i < 100; i++) {
                bouleMagique.draw(context);
            }


        for (var oeil : monstresOeil)
            oeil.draw(context);

        for (var bouche : monstresBouche)
            bouche.draw(context);


    }


    public boolean gameOver() {
        return nbMonstreSorti == 3;
    }


    public void afficherNiveau(GraphicsContext context) {
        context.setFill(Color.WHITE); //https://examples.javacodegeeks.com/desktop-java/javafx/javafx-canvas-example/#:~:text=You%20can%20draw%20text%20using,%2C%20double%20x%2C%20double%20y)
        context.setFont(Font.font("Fantasy", 40));
        context.fillText("Niveau  " + (Monstre.numeroNiveau), 230, 240);


    }

    public void enleverBoule(Magie boule) {
        boulesMagique.remove(boule);
    }

    public void creerMonstre() {
        monstres.add(new Monstre());
    }


    public void enleverMonstre(Monstre monstre) {
        monstres.remove(monstre);
    }

    public void creerBouleMagique(Squelette squelette) {
        boulesMagique.add(new Magie(squelette));
    }


    public void affichageFinPartie(GraphicsContext context) {
        context.setFill(Color.RED);
        context.setFont(Font.font("verdana", 40));
        context.fillText("Game Over", 230, 240);
    }

    public void augmenterNiveauApresCinqMonstreSortie() {
        if (cptScore % 5 == 0) {
            Monstre.numeroNiveau++;
            affichageNiveau = true;
        }
    }

    public void creerMonstreOeil() {
        monstresOeil.add(new Oeil());
    }

    public void enleverMonstreOeil(Oeil oiel) {
        monstresOeil.remove(oiel);
    }

    public void enleverMonstreBouche(Bouche bouche) {
        monstresBouche.remove(bouche);
    }

    public void creerMonstreBouche() {
        monstresBouche.add(new Bouche());
    }

    public void setAffichageNiveau(boolean affichageNiveau) {
        this.affichageNiveau = affichageNiveau;
    }


}




