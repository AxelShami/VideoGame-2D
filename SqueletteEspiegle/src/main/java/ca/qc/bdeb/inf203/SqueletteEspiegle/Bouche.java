package ca.qc.bdeb.inf203.SqueletteEspiegle;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Bouche extends Monstre {
    private Image imageBouche = new Image("bouche.png");
    private double temps;
    private final double yBase;

    public Bouche() {
        yBase = y;
        if (droite)
            imageBouche = ImageHelpers.flop(imageBouche);

    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(ImageHelpers.colorize(imageBouche, color), x, y, w, h);
    }

    @Override
    public void update(double deltaTemps) {
        temps += deltaTemps;
        y = yBase + 5 * Math.sin(10 * temps);
        updatePhysique(deltaTemps);
    }


}
