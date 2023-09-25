package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;


public class Squelette extends GameObject{

    @Override
    public void draw(GraphicsContext context) {

    }
    public void essaie(GraphicsContext context){

        double frameRate = 16 * 1e-9;
        Image[] frames = new Image[] {
                new Image("squelette\\stable"),
                new Image("squelette\\marche1"),
                new Image("squelette\\marche2")
        };

         context.drawImage(frameRate);

        /*
         * 16 fps = 16 changements d'image par seconde
         * (ou 16*10ˆ-9 images par nanoseconde)
         */

// Charge les images de l’animation dans un tableau





        AnimationTimer timer = new AnimationTimer() {
            //GraphicsContext context =
            private long lastTime = System.nanoTime();
            private double x = 0, y = 150;
            @Override
            public void handle(long now) {
                /* Calcul du deltaTime par rapport au
début de l'animation */
                double deltaTime = now - startTime;
                int frame = (int) Math.floor(deltaTime * frameRate);
                Image img = frames[frame % frames.length];
                context.clearRect(50, 50, img.getWidth(), img.getHeight());
                context.drawImage(img, 50, 50);

// Temps en sec = 10ˆ(-9) * temps en nanosec
                double deltaTime = (now - lastTime) * 1e-9;
                x += deltaTime * 90; // 90 pixels/s
                context.clearRect(0, 0, w, h);
                context.fillRect(x, y, 40, 40);
                lastTime = now;
            }
        };


    }
}
