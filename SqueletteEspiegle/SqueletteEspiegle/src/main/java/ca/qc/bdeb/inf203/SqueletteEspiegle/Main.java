package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.Position;
import java.util.logging.Handler;


public class Main extends Application {
    public static final int WIDTH = 640, HEIGHT = 480;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }
//var canvas = new Canvas(WIDTH, HEIGHT);
    //root.getChildren().add(canvas);
    // var context = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // la scene d'accueil
        this.stage=primaryStage;
        var root = new VBox();
        var sceneAccueil = new Scene(root, WIDTH, HEIGHT);
        var pane = new Pane();
        var sceneJeu = new Scene(pane, WIDTH, HEIGHT);
        VBox info = new VBox();
        var sceneInfo = new Scene(info, WIDTH, HEIGHT);

        Image imageSceneAccueil = new Image("logo.png");
        ImageView logo = new ImageView(imageSceneAccueil);
        logo.setFitWidth(WIDTH);
        logo.setFitHeight(HEIGHT);
        root.getChildren().addAll(logo);
        var buttons = new VBox();
        Button buttonJouer = new Button("Jouer");
        Button buttonInfo = new Button("Info");
        buttons.getChildren().addAll(buttonJouer,buttonInfo);
        root.setSpacing(-180);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        root.getChildren().addAll(buttons);

        buttonJouer.setOnMouseClicked((event1) -> {
          stage.setScene(sceneJeu);
        });
        buttonInfo.setOnMouseClicked((event2) -> {
            stage.setScene(sceneInfo);
        });

        // La scene de jeu

        var canvas = new Canvas(WIDTH, HEIGHT);
        pane.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();





































        stage.setScene(sceneAccueil);
        stage.setTitle("Squelette Espiegle");
        Image icon = new Image("squelette.png");
        //https://stackoverflow.com/questions/42233740/javafx-set-not-resizable-for-only-one-stage
        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.show();








    }

}