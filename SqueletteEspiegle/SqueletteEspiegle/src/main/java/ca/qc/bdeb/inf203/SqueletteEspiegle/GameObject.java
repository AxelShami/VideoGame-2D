package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {

    double x, y;
    double vx, vy;
    double ax, ay;
    double w, h;

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
    public double getHaut() {
        return y;
    }
    public double getBas() {
        return y + h;
    }
    public double getGauche() {
        return x;
    }
    public double getDroite() {
        return x + w;
    }



}
