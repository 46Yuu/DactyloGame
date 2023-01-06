package com.example.vue;

import com.example.controller.SoloJeuSceneController;
import com.example.controller.SoloNormalSceneController;
import com.example.modele.Parametre;
import com.example.modele.PartieSoloJeu;
import com.example.modele.PartieSoloNormal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GenerateurVue {


    public static Parametre creationParametreSoloNormal(){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .texteATaper()
        .mode("Solo")
        .vies(0)
        .niveau(0).build();
    }
    
    public static Vue genererVuePartieSoloNormal(){
        return new Vue() {
            @Override
            public void lancerVue() {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SoloNormalScene.fxml"));
                    Parent root = loader.load();
                    SoloNormalSceneController controller = loader.getController();
                    PartieSoloNormal jeu = new PartieSoloNormal(creationParametreSoloNormal());
                    controller.setJeu(jeu);
                    jeu.initialize();
                    controller.initializeScene();
                    stage.setScene(new Scene(root));  
                    stage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Vue genererVuePartieSoloJeu(){
        return new Vue() {
            @Override
            public void lancerVue() {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SoloJeuScene.fxml"));
                    Parent root = loader.load();
                    SoloJeuSceneController controller = loader.getController();
                    PartieSoloJeu jeu = new PartieSoloJeu(creationParametreSoloNormal());
                    controller.setJeu(jeu);
                    jeu.initialize();
                    controller.initializeScene();
                    stage.setScene(new Scene(root));  
                    stage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
