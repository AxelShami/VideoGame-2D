package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public abstract class GameObject {

    protected double x, y;
    protected double vx, vy;

    Color color;

    Random r = new Random();


    public GameObject() {
        color = ImageHelpers.couleurAuHasard();
    }

    protected double ax, ay = 150;
    protected double w, h;


    public abstract void draw(GraphicsContext context);


    protected void updatePhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }

    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    }


}
