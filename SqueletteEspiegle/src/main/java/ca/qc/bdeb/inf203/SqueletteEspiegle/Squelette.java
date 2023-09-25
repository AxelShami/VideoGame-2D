package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Squelette extends GameObject {
    private boolean droiteIsPressed;
    private double tempsEcoule;

    public Squelette() {
        w = 48;
        h = 96;
        x = Main.WIDTH / 2;
        y = Main.HEIGHT / 2;
    }

    private final Image[] imagesSquelettes = new Image[]{new Image("squelette\\marche1.png"), new Image("squelette\\marche2.png")};
    private final Image[] imagesSquelettesInverse = new Image[]{ImageHelpers.flop(imagesSquelettes[0]), ImageHelpers.flop(imagesSquelettes[1]),};

    private Image imageActuelle = new Image("squelette\\stable.png");
    private final Image squeletteStable = new Image("squelette\\stable.png");

    @Override
    public void update(double deltaTemps) {
        tempsEcoule += deltaTemps;
        int FRAME_RATE = 10;
        int frame = (int) Math.floor(tempsEcoule * FRAME_RATE);

        boolean left = Input.isKeyPressed(KeyCode.LEFT);
        boolean right = Input.isKeyPressed(KeyCode.RIGHT);
        boolean jump = Input.isKeyPressed(KeyCode.UP);


        if (left) {
            ax = -1000;
            imageActuelle = imagesSquelettesInverse[frame % imagesSquelettesInverse.length];

            if (vx < -300) {
                vx = -300;
            }
            droiteIsPressed = false;

        } else if (right) {

            imageActuelle = imagesSquelettes[frame % imagesSquelettes.length];
            ax = 1000;
            if (vx > 300)
                vx = 300;
            droiteIsPressed = true;

        } else {
            ax = 0;
            if (droiteIsPressed) {
                imageActuelle = squeletteStable;
            } else imageActuelle = ImageHelpers.flop(squeletteStable);

            // Quand on relâche : on RALENTI au lieu de stopper
            int signeVitesse = vx > 0 ? 1 : -1;
            double vitesseAmortissementX = -signeVitesse * 500;
            vx += deltaTemps * vitesseAmortissementX;
            int nouveauSigneVitesse = vx > 0 ? 1 : -1;

            // si le signe change, la vitesse tombe à zéro
            if (nouveauSigneVitesse != signeVitesse) {
                vx = 0;
            }
            ay = 1200;

        }

        if (x + w + 5 > Main.WIDTH || x - 5 < 0) {
            vx *= -0.9;

        }
        updatePhysique(deltaTemps);

        if (jump) {
            if (Main.HEIGHT - h < y) {
                vy = -600;
            }
        }

        x = Math.min(x, Main.WIDTH - w);
        x = Math.max(x, 0);
        y = Math.min(y, Main.HEIGHT - h);
        y = Math.max(y, 0);
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(imageActuelle, x, y, w, h);
    }

}






