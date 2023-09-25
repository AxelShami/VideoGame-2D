package ca.qc.bdeb.inf203.SqueletteEspiegle;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Oeil extends Monstre {
    private final Image imageOeil = new Image("oeil.png");
    protected boolean avance;
    protected double tempsAvancer = 0;

    public Oeil() {
        ay = 0;
        vy = 0;
        vx = super.vx * 1.3;
        avance = true;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(ImageHelpers.colorize(imageOeil, color), x, y, w, h);
    }

    public void update(double deltaTemps) {
        tempsAvancer += deltaTemps;
        updatePhysique(deltaTemps);
    }

}
