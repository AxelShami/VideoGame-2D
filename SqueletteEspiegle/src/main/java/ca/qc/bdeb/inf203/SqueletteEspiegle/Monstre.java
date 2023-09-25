package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Monstre extends GameObject {

    static boolean droite;
    protected static int numeroNiveau = 1;

    private Image imageActuelle;
    private double rayon;
    Image[] monstres = new Image[]{
            new Image("monstres\\0.png"),
            new Image("monstres\\1.png"),
            new Image("monstres\\2.png"),
            new Image("monstres\\3.png"),
            new Image("monstres\\4.png"),
            new Image("monstres\\5.png"),
            new Image("monstres\\6.png"),
            new Image("monstres\\7.png"),
    };

    public Monstre() {
        getRandomCoteMonstre();
        initializerMonstre();
    }

    private void initializerMonstre() {
        int choix = r.nextInt(monstres.length);
        double valeurRandom = 40 + (100 - 40) * r.nextDouble();
        w = valeurRandom;
        h = valeurRandom;
        rayon = valeurRandom / 2;
        double min = 0.2 * 480;
        double max = 0.8 * 480;
        y = (min + (max - min) * r.nextDouble());
        vy = -1 * (100 + (200 - 100) * r.nextDouble());
        if (droite) {
            x = Main.WIDTH;
            vx = (-100 * (Math.pow(numeroNiveau, 0.33))) - 200;
        } else {
            x = 0;
            vx = (100 * (Math.pow(numeroNiveau, 0.33))) + 200;
        }

        if (droite) {
            imageActuelle = ImageHelpers.flop(monstres[choix]);
        } else imageActuelle = monstres[choix];
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(ImageHelpers.colorize(imageActuelle, color), x, y, w, h);
    }


    public static boolean getRandomCoteMonstre() { //https://stackoverflow.com/questions/11468221/get-random-boolean-in-java
        droite = Math.random() < 0.5;
        return droite;
    }

    public boolean validerSortiMonstre() {
        boolean sortiMonstre = false;
        if (droite) {
            if (x + w < -40 || y + h > Main.HEIGHT) {
                sortiMonstre = true;
            }
        } else {
            if (x + w > Main.WIDTH + 40 || y + h > Main.HEIGHT) {
                sortiMonstre = true;
            }
        }
        return sortiMonstre;
    }

    public boolean intersects(Magie bouleMagique) {
        double dx = x - bouleMagique.x;
        double dy = y - bouleMagique.y;
        double dCarre = dx * dx + dy * dy;
        return dCarre < (rayon + bouleMagique.getRayon()) * (rayon + bouleMagique.getRayon());
    }


}
