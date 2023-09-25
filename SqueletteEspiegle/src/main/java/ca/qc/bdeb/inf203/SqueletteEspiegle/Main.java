package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    protected static final int WIDTH = 640, HEIGHT = 480;

    private Stage stage;


    private double tempsAvantDeRoutournerAuSceneAccueil;
    private Scene sceneInfo;
    private Partie partie;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        // la scene d'accueil
        this.stage = primaryStage;
        var root = new VBox();
        var sceneAccueil = new Scene(root, WIDTH, HEIGHT);
        var pane = new Pane();
        var sceneJeu = new Scene(pane, WIDTH, HEIGHT);
        VBox info = new VBox();
        sceneInfo = new Scene(info, WIDTH, HEIGHT);

        partie = new Partie();


        Image imageSceneAccueil = new Image("logo.png");
        ImageView logo = new ImageView(imageSceneAccueil);
        logo.setFitWidth(WIDTH);
        logo.setFitHeight(HEIGHT);
        root.getChildren().addAll(logo);
        var buttons = new VBox();
        Button buttonJouer = new Button("Jouer");
        Button buttonInfo = new Button("Info");
        buttons.getChildren().addAll(buttonJouer, buttonInfo);
        root.setSpacing(-180);

        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        root.getChildren().addAll(buttons);
        Text score = new Text("1");

        sceneJeu.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            } else if (e.getCode() == KeyCode.H) {
                Monstre.numeroNiveau++;
                partie.setAffichageNiveau(true);
            } else if (e.getCode() == KeyCode.J) {
                partie.setCptScore(partie.getCptScore() + 1);
                partie.augmenterNiveauApresCinqMonstreSortie();
            } else if (e.getCode() == KeyCode.K) {
                partie.setNbMonstreSorti(partie.getNbMonstreSorti() - 1);
            } else if (e.getCode() == KeyCode.L) {
                partie.setNbMonstreSorti(3);
                Monstre.numeroNiveau = 1;

            } else {

                Input.setKeyPressed(e.getCode(), true);


            }


        });
        buttonJouer.setOnAction((event1) -> {
            stage.setScene(sceneJeu);
            partie.setNbMonstreSorti(0);
            partie = new Partie();
            Monstre.numeroNiveau = 1;


        });


        buttonInfo.setOnAction((event2) -> {
            sceneInfo = sceneInfo(sceneAccueil);
            stage.setScene(sceneInfo);
        });


        // La scene de jeu

        var canvas = new Canvas(WIDTH, HEIGHT);
        pane.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();


        pane.getChildren().add(score);


        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();


            @Override
            public void handle(long now) {

                double deltaTemps = (now - lastTime) * 1e-9;


                context.setFill(Color.BLACK);
                context.fillRect(0, 0, WIDTH, HEIGHT);


                partie.update(deltaTemps);
                partie.draw(context);

                if (partie.gameOver()) {
                    tempsAvantDeRoutournerAuSceneAccueil += deltaTemps;
                    if (tempsAvantDeRoutournerAuSceneAccueil > 0.75) {
                        stage.setScene(sceneAccueil);


                        tempsAvantDeRoutournerAuSceneAccueil = 0;

                    }
                }


                lastTime = now;
            }
        };
        timer.start();

        sceneJeu.setOnKeyReleased((e) -> Input.setKeyPressed(e.getCode(), false));


        stage.setScene(sceneAccueil);
        stage.setTitle("Squelette Espiegle");
        Image icon = new Image("squelette.png");
        stage.setResizable(false);  //https://stackoverflow.com/questions/42233740/javafx-set-not-resizable-for-only-one-stage
        stage.getIcons().add(icon);
        stage.show();

    }


    public Scene sceneInfo(Scene sceneAccueil) {

        VBox root = new VBox();
        root.setSpacing(20);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Text titreJeu = new Text("Squelette Espiègle");
        titreJeu.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, 40));
        root.setPadding(new Insets(150, 0, 0, 0));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(titreJeu);
        HBox createur1 = new HBox();
        createur1.setSpacing(30);
        Text motPar = new Text("par");
        motPar.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, 20));
        Text nomCreateur1 = new Text("Ammar Ranko");
        stylingText(root, createur1, motPar, nomCreateur1);

        HBox createur2 = new HBox();
        createur2.setSpacing(30);
        Text motEt = new Text("Et");
        motEt.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, 20));
        Text nomCreateur2 = new Text("Axel Shami");
        stylingText(root, createur2, motEt, nomCreateur2);


        VBox phraseExplication = new VBox();
        phraseExplication.setSpacing(10);
        Text textExplicPartie1 = new Text("Travail remis à Nicolas Hurtubise. Graphismes adaptés de http://game-iconic.net/.\nDéveloppé dans le cadre du cours 420-203-RE- Développement de programmes dans\n  un environnemnet graphiquesDéveloppé dans le cadre du cours 420-203-RE-\nDéveloppement de programmes dans un environnemnet graphiques, au collège de Bois- de- Boulogne");
        textExplicPartie1.setFont(Font.font("arial", FontPosture.ITALIC, 12));
        phraseExplication.getChildren().add(textExplicPartie1);

        phraseExplication.setAlignment(Pos.CENTER);
        nomCreateur2.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, 20));

        root.getChildren().add(phraseExplication);

        Button retour = new Button("retour");
        root.getChildren().add(retour);
        retour.setOnAction((e) -> {
            stage.setScene(sceneAccueil);
            nomCreateur1.setFill(ImageHelpers.couleurAuHasard());
            nomCreateur2.setFill(ImageHelpers.couleurAuHasard());

        });
        scene.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(sceneAccueil);

            }
            stage.setScene(sceneAccueil);
            nomCreateur1.setFill(ImageHelpers.couleurAuHasard());
            nomCreateur2.setFill(ImageHelpers.couleurAuHasard());

        });


        return scene;


    }

    private void stylingText(VBox root, HBox createur1, Text motPar, Text nomCreateur1) {
        nomCreateur1.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, 20));
        nomCreateur1.setFill(ImageHelpers.couleurAuHasard());
        createur1.setAlignment(Pos.CENTER);
        createur1.getChildren().addAll(motPar, nomCreateur1);
        root.getChildren().add(createur1);
    }


}