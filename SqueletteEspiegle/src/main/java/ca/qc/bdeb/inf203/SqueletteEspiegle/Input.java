package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {
    // On **associe** chaque touche à vrai/faux, selon si on a
    // appuyé dessus
    private static HashMap<KeyCode, Boolean> touches = new HashMap<>();

    // Personnage.update() va demander si certaines touches sont
    // appuyées ou non
    public static boolean isKeyPressed(KeyCode code) {
        return touches.getOrDefault(code, false);
    }

    public static void setKeyPressed(KeyCode code, boolean isPressed) {
        touches.put(code, isPressed);
    }
}

