package com.example.vue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


import com.example.modele.*;

/**
 * Hello world!
 *
 */
public class App extends Application
{


    public static void main( String[] args )
    {
        launch(args);
    }

    public Parametre creationParametreSoloNormal(){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .texteATaper()
        .mode("Solo Normal")
        .vies(0)
        .niveau(0).build();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.print("");
            
            // //URL url = Paths.get("./src/main/resources/MainScene.fxml").toUri().toURL();
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SoloNormalScene.fxml"));
            // //Parent root = FXMLLoader.load(url);
            // Parent root = loader.load();
            // SoloNormalController controller = loader.getController();
            // Jeu jeu = new Jeu(creationParametreSoloNormal());
            // Modele modele = new Modele(jeu);
            // controller.setModele(modele);
            // modele.initialize();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChoixScene.fxml"));
            Parent root = loader.load();


            
            Scene scene = new Scene(root);
            
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Choix");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
