package com.example.vue;

import com.example.controller.ChoixParamSoloJeuController;
import com.example.controller.ChoixParamSoloNormalController;
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
    public interface Vue{
        void lancerVue(Stage ancienStage);
    
    }
    


    public static Parametre creationParametreSoloNormal(String langue, int temps){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .langue(langue)
        .texteATaper()
        .mode("Solo")
        .vies(0)
        .niveau(0)
        .limiteDeTemps(temps)
        .build();
    }

    public static Parametre creationParametreSoloJeu(String langue, int vies,int niveau){
        return Parametre.builder()
        .vitesse(4.0)
        .frequenceBonus(0)
        .langue(langue)
        .texteATaper()
        .mode("Solo")
        .vies(vies)
        .niveau(niveau)
        .build();
    }

    public static Vue genererVueParamPartieSoloNormal(){
        return new Vue() {
            @Override
            public void lancerVue(Stage ancienStage) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChoixParamSoloNormal.fxml"));
                    Parent root = loader.load();
                    ChoixParamSoloNormalController controller = loader.getController();
                    controller.setStage(stage);
                    
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));  
                    stage.show();
                    ancienStage.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Vue genererVueParamPartieSoloJeu(){
        return new Vue() {
            @Override
            public void lancerVue(Stage ancienStage) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChoixParamSoloJeu.fxml"));
                    Parent root = loader.load();
                    ChoixParamSoloJeuController controller = loader.getController();
                    controller.setStage(stage);
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));  
                    stage.show();
                    ancienStage.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
    
    public static Vue genererVuePartieSoloNormal(String langue, int temps){
        return new Vue() {
            @Override
            public void lancerVue(Stage ancienStage) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SoloNormalScene.fxml"));
                    Parent root = loader.load();
                    SoloNormalSceneController controller = loader.getController();
                    PartieSoloNormal jeu = new PartieSoloNormal(creationParametreSoloNormal(langue,temps));
                    controller.setJeu(jeu);
                    controller.setStage(stage);
                    jeu.initialize();
                    controller.initializeScene();
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));  
                    stage.show();
                    ancienStage.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Vue genererVuePartieSoloJeu(String langue, int vies,int niveau){
        return new Vue() {
            @Override
            public void lancerVue(Stage ancienStage) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SoloJeuScene.fxml"));
                    Parent root = loader.load();
                    SoloJeuSceneController controller = loader.getController();
                    PartieSoloJeu jeu = new PartieSoloJeu(creationParametreSoloJeu(langue,vies,niveau));
                    controller.setJeu(jeu);
                    controller.setStage(stage);
                    jeu.initialize();
                    controller.initializeScene();
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));  
                    stage.show();
                    ancienStage.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
