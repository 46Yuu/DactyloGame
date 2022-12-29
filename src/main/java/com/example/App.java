package com.example;
import java.net.URL;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.StyledTextArea;

import com.example.controller.MainSceneController;
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
            
            //URL url = Paths.get("./src/main/resources/MainScene.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
            //Parent root = FXMLLoader.load(url);
            Parent root = loader.load();
            MainSceneController controller = loader.getController();
            Jeu jeu = new Jeu(creationParametreSoloNormal());
            Modele modele = new Modele(jeu);
            controller.setModele(modele);
            modele.initialize();

            
            Scene scene = new Scene(root);
            
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Test Scene");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
